package net.multiplemonomials.eer.tileentity;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.interfaces.ITileEntityAcceptsEMC;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageEnergyCollectorUpdate;
import net.multiplemonomials.eer.network.message.MessageTileEnergyCollector;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.Coordinate;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityEnergyCollector extends TileEntityEE
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
	
	Set<ForgeDirection> _sidesToOutputTo;
	
	
    public void setLightLevel(int lightLevel) {
		this.lightLevel = lightLevel;
	}

	public int getLightLevel() {
		return lightLevel;
	}

	public TileEntityEnergyCollector()
    {
    	super(1);
    	
    	_sidesToOutputTo = EnumSet.noneOf(ForgeDirection.class);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
       super.readFromNBT(nbtTagCompound);

       leftoverEMC = nbtTagCompound.getDouble("leftoverEMC");
       
       upgradeLevel = nbtTagCompound.getByte("level");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
       super.writeToNBT(nbtTagCompound);

       nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);
       
       nbtTagCompound.setByte("level", upgradeLevel);
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
    	if(!worldObj.isRemote)
    	{
	    	if(--tickCounter == 0)
	    	{
	    		tickCounter = 14;
	    		lightLevel = worldObj.getBlockLightValue(xCoord, yCoord, zCoord);	
	    		
	    		PacketHandler.INSTANCE.sendToAllAround(new MessageEnergyCollectorUpdate(this), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, yCoord, zCoord, 64));
	    	}
    	}
    	
    	if(leftoverEMC < getMaxStorableEMC())
    	{
    		leftoverEMC += (CommonConfiguration.ENERGY_COLLECTOR_EMC_PER_TICK[upgradeLevel - 1] * (lightLevel / 15.0));
    	}
    	
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
    				leftoverEMC -= emcStoringItem.tryAddEMC(inventory[ENERGY_SLOT_INVENTORY_INDEX], MathHelper.clamp_double(leftoverEMC, 0, CommonConfiguration.ENERGY_COLLECTOR_DRAIN_RATE[upgradeLevel - 1]));
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
		return CommonConfiguration.ENERGY_COLLECTOR_EMC_STORAGE[upgradeLevel - 1];
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
        	 return Names.Containers.ENERGY_COLLECTOR + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[upgradeLevel - 1];
        }
    }
	
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEnergyCollector(this));
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
    		if(entity != null && entity instanceof ITileEntityAcceptsEMC)
    		{
    			_sidesToOutputTo.add(direction);
    		}
    	}
    }
}
