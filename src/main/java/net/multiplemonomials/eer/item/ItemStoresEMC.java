package net.multiplemonomials.eer.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.multiplemonomials.eer.interfaces.IStoresEMC;

public abstract class ItemStoresEMC extends ItemEE implements IStoresEMC
{
	@Override
	public double getAvailableEMC(ItemStack itemStack)
	{
    	verifyItemStackHasNBTTag(itemStack);
    	
    	return itemStack.stackTagCompound.getDouble("storedEMC");
	}
	
	@Override
	public double tryTakeEMC(ItemStack itemStack, double idealEMC)
	{
		verifyItemStackHasNBTTag(itemStack);
		
		double currentEMC = itemStack.stackTagCompound.getDouble("storedEMC");
		double newEMC = 0.0;
		double EMCGotten = 0.0;
		if(currentEMC < idealEMC)
		{
			EMCGotten = currentEMC;
		}
		else
		{
			newEMC = currentEMC - idealEMC;
			EMCGotten = idealEMC;
		}
		
		itemStack.stackTagCompound.setDouble("storedEMC", newEMC);
		
		return EMCGotten;
				
		
	}
	
	/**
	 * Makes sure the itemstack supplied has its proper NBT tagging
	 * @param itemStack
	 */
	protected static void verifyItemStackHasNBTTag(ItemStack itemStack)
	{
		if(itemStack.getTagCompound() == null)
		{
			itemStack.stackTagCompound = new NBTTagCompound();
		}
		
	}
	
	/**
	 * Tries to add the given EMC to the item
	 * @param itemStack
	 * @param EMCToAdd
	 * 
	 * @return The EMC it didn't add because it hit the limit
	 */
	@Override
	public double tryAddEMC(ItemStack itemStack, double EMCToAdd)
	{
		verifyItemStackHasNBTTag(itemStack);
		double currentEMC = itemStack.stackTagCompound.getDouble("storedEMC");
		double maxEMC = getMaxStorableEMC(itemStack);
		double failedToAddEMC = 0;
		if(currentEMC + EMCToAdd > maxEMC)
		{
			failedToAddEMC = (currentEMC + EMCToAdd) - maxEMC; 
			currentEMC = maxEMC;
		}
		else
		{
			currentEMC += EMCToAdd;
		}
		
		itemStack.stackTagCompound.setDouble("storedEMC", currentEMC);
		
		return EMCToAdd - failedToAddEMC;

	}
}
