package com.pahimar.ee3.tileentity;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.pahimar.ee3.exchange.EnergyRegistry;
import com.pahimar.ee3.inventory.ContainerCondenser;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.Reference;
import com.pahimar.ee3.util.ItemHelper;

public class TileEntityCondenser extends TileEntityAlchemicalChest
{
	public static final int INPUT_SLOT_INVENTORY_INDEX = ContainerCondenser.CONDENSER_INVENTORY_SIZE - 1;

	private ItemStack[] previousInventory;
	
	/**
	 * The amount of EMC currently stored in the condenser
	 * Should always be less that the EMC value of inventory[INPUT_SLOT_INVENTORY_INDEX]
	 */
	private long leftoverEMC = 0;
	
	
    public TileEntityCondenser()
    {
        super(3);
        
        inventory = new ItemStack[ContainerCondenser.CONDENSER_INVENTORY_SIZE];
        
        previousInventory = new ItemStack[ContainerCondenser.CONDENSER_INVENTORY_SIZE];

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
    	return !ArrayUtils.isEquals(inventory, previousInventory);
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
    	
    	if(getHasChanged())
    	{
    		ArrayList<Integer> condensableItems = getCondensableItems();
    		
    		if(!condensableItems.isEmpty())
    		{
    			//commit the last update method's changes and them make our own
    			//so that the getHasChanged will return true next update
    			
    			updateHasChanged();
    			
    			condenseSomeItems(condensableItems);
    			
    		}
    	}

    }

    /**
     * Adds the ItemStack to the first available slot in the inventory
     * 
     * Splits the items if the stack is more than 64.
     * 
     * Assumes it is not called with a null or zero-size ItemStack.
     * 
     * @param itemsToProduce
     * 
     * @return the number of items that could not fit, if any
     */
    private int addItemsToInventory(ItemStack itemsToProduce)
    {
		for(int counter = 0; counter < inventory.length; ++counter)
		{
			if(itemsToProduce.stackSize == 0)
			{
				break;
			}
			
			if(inventory[counter] == null)
			{
				inventory[counter] = itemsToProduce.copy();
				
				inventory[counter].stackSize = itemsToProduce.stackSize;
				
				if(inventory[counter].stackSize > inventory[counter].getItem().getItemStackLimit(inventory[counter]))
				{
					inventory[counter].stackSize = inventory[counter].getItem().getItemStackLimit(inventory[counter]);
				}
				
				itemsToProduce.stackSize -= inventory[counter].stackSize;
			}
			else if(inventory[counter].stackSize < getInventoryStackLimit() && ItemHelper.equalsIgnoreStackSize(itemsToProduce, inventory[counter]))
			{
				int itemsToAddToStack = itemsToProduce.stackSize;
				
				if(itemsToAddToStack + inventory[counter].stackSize > inventory[counter].getItem().getItemStackLimit(inventory[counter]))
				{
					itemsToAddToStack = inventory[counter].getItem().getItemStackLimit(inventory[counter]) - inventory[counter].stackSize;
				}
				
				inventory[counter].stackSize += itemsToAddToStack;
				itemsToProduce.stackSize -= itemsToAddToStack;
			}
		}
		
		return itemsToProduce.stackSize;
	}
    
    private int drainLeftoverEMC(int itemToProduceLimit, int itemToProduceEMCValue)
    {
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
     * Condenses as many of the supplied items as is allowed by the config rate and current invenory space.
     * @param condensableItems an ArrayList of inventory indexes to items to condense. The function will modify these or 
     * set them to null
     * 
     */
    private void condenseSomeItems(ArrayList<Integer> condensableItems) 
    {	
    	int targetItemEMC = MathHelper.floor_float(EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INVENTORY_INDEX]).getValue());
    	
    	int numberOfTargetItemsToProduce = 0;
		
    	numberOfTargetItemsToProduce += drainLeftoverEMC(Reference.CONDENSER_OUTPUT_ITEMS_PER_TICK, targetItemEMC);
    	
    	//TODO: don't produce more items than can fit.  Currently they're barfed back out as leftover EMC
    	
    	if(numberOfTargetItemsToProduce <= Reference.CONDENSER_OUTPUT_ITEMS_PER_TICK)
	    {
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
	    			leftoverEMC += singleItemEmcValue;
	    			
	    			
	    			numberOfTargetItemsToProduce += drainLeftoverEMC(Reference.CONDENSER_OUTPUT_ITEMS_PER_TICK - numberOfTargetItemsToProduce, targetItemEMC);
	    			
	    			
	    			if(numberOfTargetItemsToProduce >= Reference.CONDENSER_OUTPUT_ITEMS_PER_TICK)
	    			{
	    				break;
	    			}
	    		}
				
				if(numberOfTargetItemsToProduce >= Reference.CONDENSER_OUTPUT_ITEMS_PER_TICK)
				{
					break;
				}
	    	}
	    }
	    	
    	if(numberOfTargetItemsToProduce != 0)
    	{
    		ItemStack itemsToProduce = new ItemStack(inventory[INPUT_SLOT_INVENTORY_INDEX].getItem(), numberOfTargetItemsToProduce, inventory[INPUT_SLOT_INVENTORY_INDEX].getItemDamage());
    		int itemsToReturn = addItemsToInventory(itemsToProduce);
    		
    		if(itemsToReturn != 0)
    		{
    			//TODO: put these back where they go, may have to rethink things
    		}
    			
    	}	
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

	public long getLeftoverEMC() 
	{
		return leftoverEMC;
	}
}
