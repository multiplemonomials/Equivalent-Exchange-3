package net.multiplemonomials.eer.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.interfaces.ITileEntityAcceptsEMC;
import net.multiplemonomials.eer.inventory.ContainerCondenser;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageCondenserEMCUpdateToClient;
import net.multiplemonomials.eer.network.message.MessageTileCondenser;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.ItemHelper;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;

public class TileEntityCondenser extends TileEntityAlchemicalChest implements ITileEntityAcceptsEMC
{
	public static final int INPUT_SLOT_INVENTORY_INDEX = ContainerCondenser.CONDENSER_INVENTORY_SIZE - 1;
	
	//slots which it is not OK to put condensed items into
	static Set<Integer> _invalidSlots;
	
	double _targetItemEMC;
	
	public double getTargetItemEMC()
	{
		//the actual value cannot be trusted on the client, at least for now
		if(inventory[INPUT_SLOT_INVENTORY_INDEX] == null)
		{
			return 0;
		}
		
		EnergyValue value = EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INVENTORY_INDEX]);
		
		if(value == null)
		{
			return 0;
		}
		
		return value.getValue();
	}

	int _freeSpace;
	
	/**
	 * The amount of EMC currently stored in the condenser
	 * Should always be less that the EMC value of inventory[INPUT_SLOT_INVENTORY_INDEX]
	 */
	private double leftoverEMC = 0;
	
	
    public TileEntityCondenser()
    {
        super(ContainerCondenser.CONDENSER_INVENTORY_SIZE, 3);
        
        //I realize this isn't very thread-safe, but if you can think of a better way let me know
        if(_invalidSlots == null)
        {
        	_invalidSlots = new HashSet<Integer>();
        	_invalidSlots.add(INPUT_SLOT_INVENTORY_INDEX);
        }
    }
    
    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.CONDENSER;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
       super.readFromNBT(nbtTagCompound);

       leftoverEMC = nbtTagCompound.getDouble("leftoverEMC");
       _targetItemEMC = nbtTagCompound.getDouble("targetItemEMC");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
       super.writeToNBT(nbtTagCompound);

       nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);
       nbtTagCompound.setDouble("targetItemEMC", _targetItemEMC);
    }
    
    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
    {
    	if(slotIndex == INPUT_SLOT_INVENTORY_INDEX && (!EnergyRegistry.getInstance().hasEnergyValue(itemStack)))
    	{
    		return false;
    	}
    	
        return true;
    }
    
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	
    	//only do things serverside
    	if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
    	{
    		boolean changedLeftoverEMC = false;
        	
    		//check for things to condense
    		ArrayList<Integer> condensableItems = getCondensableItems();
    		
    		if(!condensableItems.isEmpty() || leftoverEMC > _targetItemEMC)
    		{
    			changedLeftoverEMC = true;
    			
    		    _freeSpace = getFreeSpace();

    			
    		    if(_freeSpace != 0)
    		    {
    		    	condenseSomeItems(condensableItems);
    		    }
    		    
    		    
    			
    		}
        	
        	if(inventory[INPUT_SLOT_INVENTORY_INDEX] != null && (_targetItemEMC != 0 && leftoverEMC > _targetItemEMC))
        	{
    			changedLeftoverEMC = true;

        	    _freeSpace = getFreeSpace();

        		ItemStack itemsToAdd = inventory[INPUT_SLOT_INVENTORY_INDEX].copy();
        		itemsToAdd.stackSize = drainLeftoverEMC(_targetItemEMC);
        		ItemHelper.addItemsToInventory(itemsToAdd, this, _invalidSlots);
        		
        	}
        	
        	//while the inventory syncing is handled automatically, syncing leftoverEMC is not.
        	if(changedLeftoverEMC)
        	{
        		syncEMC();
        	}
    	}

    }
    
    /**
     * sends the proper packet to update the EMC on the other side
     */
    private void syncEMC()
    {
    	if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT))
    	{
	    //	MessageCondenserEMCUpdateToServer message = new MessageCondenserEMCUpdateToServer(this);
	    //	PacketHandler.INSTANCE.sendToServer(message);
    	}
    	else
    	{
    		MessageCondenserEMCUpdateToClient message = new MessageCondenserEMCUpdateToClient(this);
    		PacketHandler.INSTANCE.sendToAllAround(message, new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 128));
    	}
    }
    
    private int drainLeftoverEMC(double itemToProduceEMCValue)
    {
    	int itemToProduceLimit = _freeSpace;
    	int itemsToProduce = 0;
    	
    	while(itemsToProduce < itemToProduceLimit)
    	{
    		if(getStoredEMC() < itemToProduceEMCValue)
    		{
    			return itemsToProduce;
    		}
    		else
    		{
    			leftoverEMC -= itemToProduceEMCValue;
    			++itemsToProduce;
    		}
    	}
    	
    	return itemsToProduce;
    }

	/**
     * Condenses as many of the supplied items as is allowed by the config rate and current inventory space.
     * @param condensableItems an ArrayList of inventory indexes to items to condense. The function will modify these or 
     * set them to null
     * 
     */
    private void condenseSomeItems(ArrayList<Integer> condensableItems) 
    {	
    	
    	_targetItemEMC = MathHelper.floor_float(EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INVENTORY_INDEX]).getValue());
    	
    	int numberOfSourceItemsToCondense = CommonConfiguration.CONDENSER_INPUT_ITEMS_PER_TICK;
    	
    	int numberOfTargetItemsToProduce = 0;
		
    	numberOfTargetItemsToProduce += drainLeftoverEMC(_targetItemEMC);
    	
		for(Integer index : condensableItems)
    	{
    		int singleItemEmcValue = MathHelper.floor_float(EnergyRegistry.getInstance().getEnergyValue(inventory[index]).getValue());
    		for(int stackCounter = inventory[index].stackSize; stackCounter >= 1; --stackCounter)
    		{
    			if(inventory[index].stackSize == 1)
    			{
    				inventory[index] = null;
    			}
    			else
    			{
    				--inventory[index].stackSize;
    			}
    			
    			--numberOfSourceItemsToCondense;
    			
    			leftoverEMC += singleItemEmcValue;
    			
    			
    			numberOfTargetItemsToProduce += drainLeftoverEMC(_targetItemEMC);
    			
    			
    			if(numberOfSourceItemsToCondense <= 0)
    			{
    				break;
    			}
    		}
			
			if(numberOfSourceItemsToCondense <= 0)
			{
				break;
			}
    	}
	    	
    	if(numberOfTargetItemsToProduce != 0)
    	{
    		ItemStack itemsToProduce = new ItemStack(inventory[INPUT_SLOT_INVENTORY_INDEX].getItem(), numberOfTargetItemsToProduce, inventory[INPUT_SLOT_INVENTORY_INDEX].getItemDamage());
    		int itemsToReturn = ItemHelper.addItemsToInventory(itemsToProduce, this, _invalidSlots);
    		
    		if(itemsToReturn != 0)
    		{
    			LogHelper.warn("Condenser logic issue: failed to add all produced items to the condenser");
    			LogHelper.warn("Don't freak out server op, your EMC will be returned.");
    			leftoverEMC += (itemsToReturn * _targetItemEMC);
    		}
    			
    	}	
	}
    
    private int getFreeSpace()
    {
    	int freeSlots = 0;
    	
    	if(inventory[INPUT_SLOT_INVENTORY_INDEX] != null)
		{
			for(int counter = 0; counter < inventory.length - 1; ++counter)
			{
				ItemStack itemStack = inventory[counter];
				if(itemStack != null)
				{
					if(EnergyRegistry.getInstance().hasEnergyValue(itemStack))
					{
						int freeSlotsInStack = inventory[INPUT_SLOT_INVENTORY_INDEX].getItem().getItemStackLimit(inventory[INPUT_SLOT_INVENTORY_INDEX]) - inventory[counter].stackSize;
						if(itemStack.getItem() == inventory[INPUT_SLOT_INVENTORY_INDEX].getItem() && freeSlotsInStack > 0)
						{
							freeSlots += freeSlotsInStack;
						}
					}
				}
				else
				{
					 freeSlots += inventory[INPUT_SLOT_INVENTORY_INDEX].getItem().getItemStackLimit(inventory[INPUT_SLOT_INVENTORY_INDEX]);
				}
			}
		}
    	
    	return freeSlots;
    }

	/**
     * Returns an ArrayList of inventory indexes to the items in the condenser that can be condensed
     * , or an empty list if nothing can be condensed.
     * 
     * Also checks if there is any space to put the condensed items.
     */
	private ArrayList<Integer> getCondensableItems() 
	{
		ArrayList<Integer> condensableItems = new ArrayList<Integer>();
		
    	if(!EnergyRegistry.getInstance().hasEnergyValue(inventory[INPUT_SLOT_INVENTORY_INDEX]))
		{
    		return condensableItems;
		}
		
		boolean hasAnySpace = false;
		
		if(inventory[INPUT_SLOT_INVENTORY_INDEX] != null)
		{
			for(int counter = 0; counter < inventory.length; ++counter)
			{
				ItemStack itemStack = inventory[counter];
				if(itemStack != null)
				{
					if(EnergyRegistry.getInstance().hasEnergyValue(itemStack))
					{
						if(itemStack.getItem() != inventory[INPUT_SLOT_INVENTORY_INDEX].getItem() || itemStack.getItemDamage() != inventory[INPUT_SLOT_INVENTORY_INDEX].getItemDamage())
						{
							condensableItems.add(counter);
						}
						else
						{
							if(itemStack.stackSize < itemStack.getItem().getItemStackLimit(itemStack))
							{
								hasAnySpace = true;
							}
						}
					}
				}
				else
				{
					hasAnySpace = true;
				}
			}
		}
	
		if(hasAnySpace)
		{
			return condensableItems;
		}
		else
		{
			return new ArrayList<Integer>();
		}
	}

	public double getStoredEMC() 
	{
		return leftoverEMC;
	}

	public void setStoredEMC(double leftoverEMC)
	{
		this.leftoverEMC = leftoverEMC;
	}
	
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileCondenser(this));
    }


	@Override
	public double getMaxEMC() 
	{
		//something tells me that this is a bad idea
		return Double.MAX_VALUE;
	}

	@Override
	public double tryAddEMC(double amountToAdd)
	{
		leftoverEMC += amountToAdd;
		
		syncEMC();
		
		return amountToAdd;
	}
}
