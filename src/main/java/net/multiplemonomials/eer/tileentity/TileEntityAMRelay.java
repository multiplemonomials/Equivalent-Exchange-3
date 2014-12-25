package net.multiplemonomials.eer.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.ITileEntityAcceptsEMC;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileEntityAMRelay;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.Coordinate;

public class TileEntityAMRelay extends TileEntityEE implements ITileEntityAcceptsEMC
{
	/**
	 * 1-indexed upgrade level 
	 */
	private byte upgradeLevel = 1;
	
	/**
	 * The amount of EMC currently stored in the relay
	 */
	private double leftoverEMC = 0;
	
	//used by the networking code
	public double getLeftoverEMC() 
	{
		return leftoverEMC;
	}

	public void setLeftoverEMC(double leftoverEMC) 
	{
		this.leftoverEMC = leftoverEMC;
	}

	ITileEntityAcceptsEMC _outputEntity;
	
	//constructor for loading from NBT
	public TileEntityAMRelay()
    {
    	super(0);
    }

	//constructor for when first placed in world
	public TileEntityAMRelay(byte upgradeLevel)
    {
    	super(0);
    	this.upgradeLevel = upgradeLevel;
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
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
       super.writeToNBT(nbtTagCompound);

       nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);
       
       nbtTagCompound.setByte("level", upgradeLevel);
    }
    
    @Override
    public void updateEntity()
    {
		if(_outputEntity == null)
		{
		    onNeighborUpdate();
			return;
		}
		
		double emcToGiveWithoutLoss = MathHelper.clamp_double(leftoverEMC, 0, CommonConfiguration.ANTIMATTER_RELAY_EMC_PER_TICK[upgradeLevel - 1]);
		double emcToGiveWithLoss = emcToGiveWithoutLoss * CommonConfiguration.ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[upgradeLevel - 1];
		
		//if it doesn't fit
		if(_outputEntity.getStoredEMC() + emcToGiveWithLoss > _outputEntity.getMaxEMC())
		{
			if(_outputEntity.getStoredEMC() == _outputEntity.getMaxEMC())
			{
				return;
			}
			emcToGiveWithLoss = _outputEntity.getMaxEMC() + _outputEntity.getStoredEMC();
			emcToGiveWithoutLoss = emcToGiveWithLoss / CommonConfiguration.ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[upgradeLevel - 1];
		}
		_outputEntity.tryAddEMC(emcToGiveWithLoss);
		leftoverEMC -= emcToGiveWithoutLoss;
	
    }
	
	public void upgradeLevel()
	{
		if(upgradeLevel < 3)
		{
			++upgradeLevel;
		}
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
        	 return Names.Containers.ANTIMATTER_RELAY;
        }
    }
	
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityAMRelay(this));
    }
    
    /**
     * Updates the output TileEntity
     */

	public void onNeighborUpdate()
	{
		Coordinate testingCoordinates = Coordinate.offsetByOne(new Coordinate(xCoord, yCoord, zCoord, worldObj), orientation);
		TileEntity entity = testingCoordinates.getTileEntity();
		if(entity instanceof ITileEntityAcceptsEMC)
		{
			_outputEntity = ((ITileEntityAcceptsEMC)entity);
		}
		else
		{
			_outputEntity = null;
		}
		
	}

	@Override
	public double getStoredEMC() 
	{
		return leftoverEMC;
	}

	@Override
	public double getMaxEMC() 
	{
		//same as energy collector
		return CommonConfiguration.ENERGY_COLLECTOR_EMC_STORAGE[upgradeLevel - 1];
	}

	@Override
	public double tryAddEMC(double amountToAdd) 
	{
		amountToAdd = MathHelper.clamp_double(amountToAdd, 0, getMaxEMC());
		leftoverEMC += amountToAdd;
		return amountToAdd;
	}
}
