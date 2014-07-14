package com.pahimar.ee3.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * A Slot that cannot be added to and can take items from the bound inventory when something is removed 
 */
public class OutputSlot extends Slot 
{
	
	int[] _stacksToSubtractFrom;

	/**
	 * Constructs OutputSlot
	 * @param par1iInventory
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param stacksToSubtractFrom  indexes of stacks, if any, to take one from when an item is removed from the slot
	 */
	public OutputSlot(IInventory par1iInventory, int par2, int par3, int par4, int[] stacksToSubtractFrom)
	{
		super(par1iInventory, par2, par3, par4);
		
		_stacksToSubtractFrom = stacksToSubtractFrom;
	}
	
	@Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
        
        if(_stacksToSubtractFrom != null && _stacksToSubtractFrom.length != 0)
        {
        	for(int index : _stacksToSubtractFrom)
        	{
        		ItemStack stackInCurrentSlot = inventory.getStackInSlot(index);
        		if(stackInCurrentSlot != null)
        		{
        			if(stackInCurrentSlot.stackSize == 1)
        			{
        				inventory.setInventorySlotContents(index, null);
        			}
        			else
        			{
        				stackInCurrentSlot.stackSize -= 1;
        				inventory.setInventorySlotContents(index, stackInCurrentSlot);
        				
        			}
        		}
        	}
        }
    }
    
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

}
