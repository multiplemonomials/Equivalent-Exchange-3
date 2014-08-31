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
	 * Returns the max storeable EMC if the itemStack has a max amount of storable EMC, like the Klein star,
	 * 0 if not, like the Ring of Flight.
	 * @param itemStack
	 * @return
	 */
	public double getMaxStorableEMC(ItemStack itemStack);
}
