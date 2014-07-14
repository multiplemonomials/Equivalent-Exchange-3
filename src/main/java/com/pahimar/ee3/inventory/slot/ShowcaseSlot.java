package com.pahimar.ee3.inventory.slot;

import com.pahimar.ee3.interfaces.IShowcaseSlotCallback;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * A Slot which shows an item and cannot be added to.
 * 
 * It calls a function when clicked, and will give back an arbitrary item based on said function.
 */
public class ShowcaseSlot extends Slot 
{
	/**
	 * Constructs OutputSlot
	 * @param par1iInventory
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param stacksToSubtractFrom  indexes of stacks, if any, to take one from when an item is removed from the slot
	 */
	
	IShowcaseSlotCallback _clickCallback;
	
	//used only to store the old contents of a slot while 
	ItemStack oldContents;
	boolean hasOldContents;
	
	public ShowcaseSlot(IInventory par1iInventory, int par2, int par3, int par4, IShowcaseSlotCallback clickCallback)
	{
		super(par1iInventory, par2, par3, par4);
		
		_clickCallback = clickCallback;
	}
	
	@Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
        
        if(hasOldContents)
        {
        	this.inventory.setInventorySlotContents(getSlotIndex(), oldContents);
        }
        
    }
    
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }
    
    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    public boolean canTakeStack(EntityPlayer p_82869_1_)
    {
    	if(getStack() == null)
    	{
    		return false;
    	}
    	
    	ItemStack itemToSet = _clickCallback.onSlotClick(this, getStack().copy(), false);
    	
    	if(itemToSet == null)
    	{
    		return false;
    	}
    	
    	//replace the shown stack with the one provided
    	hasOldContents = true;
    	oldContents = getStack();
    	this.inventory.setInventorySlotContents(getSlotIndex(), itemToSet);
    	
    	return true;
    }
    
    /**
     * Return whether this slot's stack can be taken from this slot.
     * 
     * Called by ItemHelper.transferStackInSlot to indicate shift clicking
     */
    public boolean canTakeStackShiftClicking(EntityPlayer p_82869_1_)
    {
    	ItemStack itemToSet = _clickCallback.onSlotClick(this, getStack().copy(), true);
    	
    	if(itemToSet == null)
    	{
    		return false;
    	}
    	
    	//replace the shown stack with the one provided
    	hasOldContents = true;
    	oldContents = getStack();
    	this.inventory.setInventorySlotContents(getSlotIndex(), itemToSet);
    	
        return true;
    }

}
