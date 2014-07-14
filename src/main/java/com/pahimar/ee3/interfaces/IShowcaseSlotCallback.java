package com.pahimar.ee3.interfaces;

import com.pahimar.ee3.inventory.slot.ShowcaseSlot;

import net.minecraft.item.ItemStack;

public interface IShowcaseSlotCallback
{
	/**
	 * Called when the slot is clicked on.  Returns what the slot should give to the player (can be null).
	 * @param currentStackInSlot stack currently in the slot.  This HAS been copied, so it's ok to do whatever with it
	 * @param isShiftClicking true if the item is being shift-clicked out, however this only works with things that use eer's ItemHelper.transferStackInSlot 
	 * @return
	 */
	public ItemStack onSlotClick(ShowcaseSlot slot, ItemStack currentStackInSlot, boolean isShiftClicking);
}
