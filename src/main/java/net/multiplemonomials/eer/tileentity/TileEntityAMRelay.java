package net.multiplemonomials.eer.tileentity;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.interfaces.ITileEntityAcceptsEMC;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileEntityAMRelay;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.Coordinate;
import net.multiplemonomials.eer.util.LogHelper;

public class TileEntityAMRelay extends TileEntityEE implements ITileEntityAcceptsEMC
{
	public static final int ENERGY_SLOT_INVENTORY_INDEX = 0;
	
	/**
	 * 1-indexed upgrade level 
	 */
	private byte upgradeLevel = 1;
	
	/**
	 * The amount of EMC currently stored in the condenser
	 * Should always be less that the EMC value of inventory[INPUT_SLOT_INVENTORY_INDEX]
	 */
	private double leftoverEMC = 0;
	
	/**
	 * The current light level of the collector.  Cached so it isn't checked every gui render cycle
	 */
	private int lightLevel;
	
	//TODO: config option for drain rate
	private static final int[] ANTI_MATTER_RELAY_DRAIN_RATE = {320, 960, 3200};
	
	Set<ForgeDirection> _sidesToOutputTo;
	
	
    public void setLightLevel(int lightLevel) {
		this.lightLevel = lightLevel;
	}

	public int getLightLevel() {
		return lightLevel;
	}
	
	//constructor for loading from NBT
	public TileEntityAMRelay()
    {
    	super(1);
    	_sidesToOutputTo = EnumSet.noneOf(ForgeDirection.class);
    }

	//constructor for when first placed in world
	public TileEntityAMRelay(byte upgradeLevel)
    {
    	super(1);
    	this.upgradeLevel = upgradeLevel;
    	
    	_sidesToOutputTo = EnumSet.noneOf(ForgeDirection.class);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
       super.readFromNBT(nbtTagCompound);

       leftoverEMC = nbtTagCompound.getDouble("leftoverEMC");
       
       if(nbtTagCompound.hasKey("level"))
       {
    	   upgradeLevel = nbtTagCompound.getByte("level");
       }
       
       // Read in the ItemStacks in the inventory from NBT
       NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
       for (int i = 0; i < tagList.tagCount(); ++i)
       {
           NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
           byte slotIndex = tagCompound.getByte("Slot");
           if (slotIndex >= 0 && slotIndex < inventory.length)
           {
               inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
           }
       }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
       super.writeToNBT(nbtTagCompound);

       nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);
       
       nbtTagCompound.setByte("level", upgradeLevel);
       
       // Write the ItemStacks in the inventory to NBT
       NBTTagList tagList = new NBTTagList();
       for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
       {
           if (inventory[currentIndex] != null)
           {
               NBTTagCompound tagCompound = new NBTTagCompound();
               tagCompound.setByte("Slot", (byte) currentIndex);
               inventory[currentIndex].writeToNBT(tagCompound);
               tagList.appendTag(tagCompound);
           }
       }
       
       nbtTagCompound.setTag("Items", tagList);
    }
    
    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
    {
    	if(slotIndex == ENERGY_SLOT_INVENTORY_INDEX && (!EnergyRegistry.getInstance().hasEnergyValue(itemStack)))
    	{
    		return false;
    	}
    	
        return true;
    }
    
    //used so that we only check the light level once every few seconds
    int tickCounter = 1;
    @Override
    public void updateEntity()
    {
    	drainEMCToKleinStar();
    	
    	if(_sidesToOutputTo.isEmpty())
    	{
    	       scanForAndAddValidEMCReceivers();
    	}
    		
    	
    	//output to other machines
    	//for now, no speed limit
    	for(ForgeDirection direction : _sidesToOutputTo)
    	{
    		Coordinate machineCoordinates = Coordinate.offsetByOne(new Coordinate(xCoord, yCoord, zCoord, worldObj), direction);
    		TileEntity entity = machineCoordinates.getTileEntity();
    		if(entity == null)
    		{
    			LogHelper.error("Somehow there wasn't an EMC-recieving tile entity at " + machineCoordinates + " where there was supposed to be");
    			_sidesToOutputTo.remove(direction);
    			continue;
    		}
    		
    		ITileEntityAcceptsEMC machine;
    		try
    		{
    			machine = ((ITileEntityAcceptsEMC) entity);
    		}
    		catch(ClassCastException error)
    		{
    			LogHelper.error("Somehow there wasn't an EMC-recieving tile entity at " + machineCoordinates + " where there was supposed to be");
    			error.printStackTrace();
    			continue;
    		}
    		
    		leftoverEMC -= machine.tryAddEMC(leftoverEMC / _sidesToOutputTo.size());
    	}
    }
    
    private void drainEMCToKleinStar()
    {
    	if(leftoverEMC != 0 && (inventory[ENERGY_SLOT_INVENTORY_INDEX] != null && inventory[ENERGY_SLOT_INVENTORY_INDEX].getItem() instanceof IStoresEMC))
    	{
    		IStoresEMC emcStoringItem = ((IStoresEMC)inventory[ENERGY_SLOT_INVENTORY_INDEX].getItem());
    		if(emcStoringItem.isEMCBattery())
    		{
    			if(leftoverEMC > 0)
    			{
    				leftoverEMC -= emcStoringItem.tryAddEMC(inventory[ENERGY_SLOT_INVENTORY_INDEX], MathHelper.clamp_double(leftoverEMC, 0, ANTI_MATTER_RELAY_DRAIN_RATE[upgradeLevel - 1]));
    			}
    		}
    	}
    }
    
	public double getLeftoverEMC() 
	{
		return leftoverEMC;
	}

	public void setLeftoverEMC(double leftoverEMC)
	{
		this.leftoverEMC = leftoverEMC;
	}
	
	public void upgradeLevel()
	{
		if(upgradeLevel < 3)
		{
			++upgradeLevel;
		}
	}
	
	public double getMaxStorableEMC()
	{
		return (double)(100000 * (upgradeLevel - 1));
	}
	
    @Override
    public String getInventoryName()
    {
        if(this.hasCustomName())
        {
        	return this.getCustomName();
        }
        else
        {
        //TODO: Unlocalized name
        	 return "antiMatterRelay" + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[upgradeLevel - 1];
        }
    }
	
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityAMRelay(this));
    }
    
    /**
     * Scans the blocks around and looks for ones that 
     */
    public void scanForAndAddValidEMCReceivers()
    {
    	for(ForgeDirection direction : ForgeDirection.values())
    	{
    		Coordinate testingCoordinates = Coordinate.offsetByOne(new Coordinate(xCoord, yCoord, zCoord, worldObj), direction);
    		TileEntity entity = testingCoordinates.getTileEntity();
    		//Don't output to energy collectors, or else that'd be infinite EMC!
    		if(entity != null && entity instanceof ITileEntityAcceptsEMC && !(entity instanceof TileEntityEnergyCollector))
    		{
    			_sidesToOutputTo.add(direction);
    		}
    	}
    }
    
    public double getStoredEMC() {
      return leftoverEMC;
    }
    
    public double getMaxEMC() {
      return CommonConfiguration.ENERGY_COLLECTOR_EMC_STORAGE[upgradeLevel - 1];
    }
    
    //The point of returning "amountToAdd" if the value doesn't change?
    /** Adds EMC from energy-collectors. Bonus based on how many there are
      *
      */
	@Override
	public double tryAddEMC(double amountToAdd)
	{
		leftoverEMC += amountToAdd;
		return amountToAdd;
	}
}
