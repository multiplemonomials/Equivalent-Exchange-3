package net.multiplemonomials.eer.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.inventory.ContainerCondenser;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileCondenser;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.ItemHelper;
import net.multiplemonomials.eer.util.LogHelper;

public class TileEntityCondenser extends TileEntityAlchemicalChest
{
	public static final int INPUT_SLOT_INVENTORY_INDEX = ContainerCondenser.CONDENSER_INVENTORY_SIZE - 1;

	private ItemStack[] previousInventory;
	
	//slots which it is not OK to put condensed items into
	static Set<Integer> _invalidSlots;
	
	double _targetItemEMC;
	
	int _freeSpace;
	
	/**
	 * The amount of EMC currently stored in the condenser
	 * Should always be less that the EMC value of inventory[INPUT_SLOT_INVENTORY_INDEX]
	 */
	private double leftoverEMC = 0;
	
	
    public TileEntityCondenser()
    {
        super(3);
        
        //I realize this isn't very thread-safe, but if you can think of a better way let me know
        if(_invalidSlots == null)
        {
        	_invalidSlots = new HashSet<Integer>();
        	_invalidSlots.add(INPUT_SLOT_INVENTORY_INDEX);
        }
        
        inventory = new ItemStack[ContainerCondenser.CONDENSER_INVENTORY_SIZE];
        
        previousInventory = new ItemStack[ContainerCondenser.CONDENSER_INVENTORY_SIZE];

    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
       super.readFromNBT(nbtTagCompound);

       leftoverEMC = nbtTagCompound.getLong("leftoverEMC");
       _targetItemEMC = nbtTagCompound.getInteger("targetItemEMC");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
       super.writeToNBT(nbtTagCompound);

       nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);
       nbtTagCompound.setDouble("targetItemEMC", _targetItemEMC);
    }
    
    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.CONDENSER;
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
    
    /**
     * Compares the previous inventory with the current one and returns if it has changed.
     * @return
     */
	public boolean getHasChanged()
    {
    	return !Objects.deepEquals(inventory, previousInventory);
    }
    
    /**
     * Updates the "previous" inventory to the current inventory
     * @return
     */
    public void updateHasChanged()
    {
    	System.arraycopy(inventory, 0, previousInventory, 0, inventory.length);
    }
    
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	
    	
    	//if(getHasChanged())
    	//{
    		ArrayList<Integer> condensableItems = getCondensableItems();
    		
    		if(!condensableItems.isEmpty())
    		{
    		    _freeSpace = getFreeSpace();

    			//commit the last update method's changes and them make our own
    			//so that the getHasChanged will return true next update
    			
    			//updateHasChanged();
    			
    		    if(_freeSpace != 0)
    		    {
    		    	condenseSomeItems(condensableItems);
    		    }
    			
    		}
    	//}
    	
    	if(inventory[INPUT_SLOT_INVENTORY_INDEX] != null && (_targetItemEMC != 0 && leftoverEMC > _targetItemEMC))
    	{
    		
    	    _freeSpace = getFreeSpace();

    		ItemStack itemsToAdd = inventory[INPUT_SLOT_INVENTORY_INDEX].copy();
    		itemsToAdd.stackSize = drainLeftoverEMC(_targetItemEMC);
    		ItemHelper.addItemsToInventory(itemsToAdd, this, _invalidSlots);
    		
    	}

    }
    
    private int drainLeftoverEMC(double itemToProduceEMCValue)
    {
    	int itemToProduceLimit = _freeSpace;
    	int itemsToProduce = 0;
    	
    	while(itemsToProduce < itemToProduceLimit)
    	{
    		if(getLeftoverEMC() < itemToProduceEMCValue)
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
    	
    	int numberOfSourceItemsToCondense = Reference.CONDENSER_INPUT_ITEMS_PER_TICK;
    	
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
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileCondenser(this));
    }
}
