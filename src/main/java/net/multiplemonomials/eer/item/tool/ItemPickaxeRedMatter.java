package net.multiplemonomials.eer.item.tool;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.util.BlockHelper;
import net.multiplemonomials.eer.util.EMCHelper;
import net.multiplemonomials.eer.util.ItemHelper;

public class ItemPickaxeRedMatter extends ItemPickaxeDarkMatter implements IStoresEMC {

	public ItemPickaxeRedMatter(Matter matterType) {
		super(matterType);
	}
	
	@Override
	public double getAvailableEMC(ItemStack itemStack)
	{
    	verifyItemStackHasNBTTag(itemStack);
    	
    	return itemStack.stackTagCompound.getDouble("storedEMC");
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
		
		return failedToAddEMC;

	}

	@Override
	public double getMaxStorableEMC(ItemStack itemStack)
	{
		return 0;
	}

	@Override
	public boolean isEMCBattery() 
	{
		return false;
	}
	
	@Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
    {	

        if (CommonConfiguration.RM_PICK_ABILITY_ENABLED && _matterType == Matter.RedMatter)
        {
            if(!world.isAirBlock(x, y, z) && player.canPlayerEdit(x, y, z, side, itemStack))
            {                
            	//TODO: fun, magical sound!!!!!! wheeeeeeee!!!!!!!
            	
            	//take some EMC
            	double emcLeft = getAvailableEMC(itemStack);
            	int blocksToSet = getDamage(itemStack) == CommonConfiguration.MAX_ITEM_CHARGES ? 1 : MathHelper.floor_double(Math.pow((CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack)) + 1, 3));
            	double neededEMC = CommonConfiguration.RM_PICK_REQUIRED_EMC_PER_BLOCK * blocksToSet;
            	
            	
            	if(emcLeft < neededEMC)
            	{
            		emcLeft += EMCHelper.consumeEMCFromPlayerInventory(player, neededEMC - emcLeft);
            	}
            	if(emcLeft >= neededEMC)
            	{
            		 emcLeft -= neededEMC;
            		 itemStack.stackTagCompound.setDouble("emcLeft", emcLeft);
            		 
            		 //construct a cube AABB with the clicked block as its center
            		 int halfHeight = (CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack));
            		 AxisAlignedBB areaToDig = AxisAlignedBB.getBoundingBox(x - halfHeight, y - halfHeight, z - halfHeight, x + halfHeight, y + halfHeight, z + halfHeight);
            		 
            		 //mine the blocks
            		 ArrayList<ItemStack> drops = BlockHelper.instaMineBlocks(areaToDig, world, player, itemStack);
            		 ItemHelper.depositItemsToPlayer(player, drops);
            	}
            	else //oops, we took all the EMC you had and it wasn't enough
            	{
            		itemStack.stackTagCompound.setDouble("emcLeft", emcLeft);
            		return false;
            	}
            	
            	return true;
            }

        }
        
        return false;
    }

}
