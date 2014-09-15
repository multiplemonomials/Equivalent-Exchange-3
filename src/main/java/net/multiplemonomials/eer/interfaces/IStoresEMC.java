package net.multiplemonomials.eer.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Interface used to mark and access power items that can store EMC.
 * @author Jamie
 *
 */

public interface IStoresEMC 
{
	/**
	 * Returns the stored EMC in the ItemStack provided
	 */
	public double getAvailableEMC(ItemStack itemStack);
	
	/**
	 * Attempts to take the provided amount of EMC, and returns what it took (could be 0) 
	 * @return
	 */
	public double tryTakeEMC(ItemStack itemStack, double wantedAmount);
	
	/**
	 * Attempts to add the provided EMC.  Returns how much was added
	 */
	public double tryAddEMC(ItemStack itemStack, double toAdd);
	
	/**
	 * Returns the max storeable EMC if the itemStack has a max amount of storable EMC, like the Klein star,
	 * 0 if not, like the Ring of Flight.
	 * @param itemStack
	 * @return
	 */
	public double getMaxStorableEMC(ItemStack itemStack);
	
	/**
	 * 
	 * @return If the item's EMC is OK to be taken by other power items, i.e. it's only purpose is to store EMC
	 */
	public boolean isEMCBattery();
}
