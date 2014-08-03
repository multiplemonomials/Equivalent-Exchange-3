package net.multiplemonomials.eer.inventory.slot;

import net.multiplemonomials.eer.exchange.EnergyRegistry;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * A Slot where only items with EMC values can be put
 */
public class EMCValuesOnlySlot extends Slot 
{  
	public EMCValuesOnlySlot(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return EnergyRegistry.getInstance().hasEnergyValue(par1ItemStack);
    }

}
