package net.multiplemonomials.eer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileEnergyCollector;
import net.multiplemonomials.eer.reference.Names;

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
	
	
    public int getLightLevel() {
		return lightLevel;
	}

	public TileEntityEnergyCollector()
    {
    	super(1);
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
    
    //used so that we only check the light level once a second
    int tickCounter = 1;
    @Override
    public void updateEntity()
    {
    	if(--tickCounter == 0)
    	{
    		lightLevel = worldObj.getBlockLightValue(xCoord, yCoord, zCoord);
    		tickCounter = 9;
    	}
    	
    	if(leftoverEMC <= getMaxStorableEMC())
    	{
    		leftoverEMC += ((CommonConfiguration.ENERGY_COLLECTOR_EMC_PER_TICK[upgradeLevel - 1] * 100) * (lightLevel / 15.0));
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
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.ENERGY_COLLECTOR;
    }
	
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEnergyCollector(this));
    }
}
