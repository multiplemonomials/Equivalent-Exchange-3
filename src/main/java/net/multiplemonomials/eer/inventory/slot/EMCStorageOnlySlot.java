package net.multiplemonomials.eer.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.multiplemonomials.eer.interfaces.IStoresEMC;

/**
 * A Slot where only items that store EMC can be put
 */
public class EMCStorageOnlySlot extends Slot 
{  
	public EMCStorageOnlySlot(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof IStoresEMC;
    }

}
