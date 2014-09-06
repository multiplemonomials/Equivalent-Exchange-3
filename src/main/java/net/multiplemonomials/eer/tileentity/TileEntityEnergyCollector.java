package net.multiplemonomials.eer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileEnergyCollector;

public class TileEntityEnergyCollector extends TileEntityEE
{
	public static final int OUTPUT_SLOT_INVENTORY_INDEX = 0;
	
	/**
	 * 1-indexed upgrade level 
	 */
	private byte upgradeLevel = 1;
	
	/**
	 * The amount of EMC currently stored in the condenser
	 * Should always be less that the EMC value of inventory[INPUT_SLOT_INVENTORY_INDEX]
	 */
	private double leftoverEMC = 0;
	
	
    public TileEntityEnergyCollector()
    {
    	super(1);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
       super.readFromNBT(nbtTagCompound);

       leftoverEMC = nbtTagCompound.getLong("leftoverEMC");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
       super.writeToNBT(nbtTagCompound);

       nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);
    }
    
    
    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
    {
    	if(slotIndex == OUTPUT_SLOT_INVENTORY_INDEX && (!EnergyRegistry.getInstance().hasEnergyValue(itemStack)))
    	{
    		return false;
    	}
    	
        return true;
    }
    
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	//TBD

    }
    
	public double getLeftoverEMC() 
	{
		return leftoverEMC;
	}

	public void setLeftoverEMC(double leftoverEMC)
	{
		this.leftoverEMC = leftoverEMC;
	}
	
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEnergyCollector(this));
    }
}
