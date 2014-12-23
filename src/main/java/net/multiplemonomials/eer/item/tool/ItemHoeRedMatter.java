package net.multiplemonomials.eer.item.tool;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.BlockHelper;
import net.multiplemonomials.eer.util.EMCHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemHoeRedMatter extends ItemHoeDarkMatter implements IStoresEMC {

	public ItemHoeRedMatter(Matter matterType) {
		super(matterType);
	}
	
	@Override
	public double getAvailableEMC(ItemStack itemStack)
	{
    	verifyItemStackHasNBTTag(itemStack);
    	
    	return itemStack.stackTagCompound.getDouble(Names.NBT.EMC_STORED);
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
		
		double currentEMC = itemStack.stackTagCompound.getDouble(Names.NBT.EMC_STORED);
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
		
		itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, newEMC);
		
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
		double currentEMC = itemStack.stackTagCompound.getDouble(Names.NBT.EMC_STORED);
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
		
		itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, currentEMC);
		
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
        if (!player.canPlayerEdit(x, y, z, side, itemStack))
        {
            return false;
        }
        else
        {
        	//take some EMC
        	double emcLeft = getAvailableEMC(itemStack);
        	int blocksToSet = getDamage(itemStack) == CommonConfiguration.MAX_ITEM_CHARGES ? 1 : MathHelper.floor_double(Math.pow((CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack)) + 1, 2));
        	double neededEMC = CommonConfiguration.RM_HOE_REQUIRED_EMC_PER_BLOCK * blocksToSet;
        	
        	
        	if(emcLeft < neededEMC)
        	{
        		emcLeft += EMCHelper.consumeEMCFromPlayerInventory(player, neededEMC - emcLeft);
        		itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, emcLeft);
        	}
        	if(emcLeft >= neededEMC)
        	{
        		
	            UseHoeEvent event = new UseHoeEvent(player, itemStack, world, x, y, z);
	            if(MinecraftForge.EVENT_BUS.post(event))
	            {
	                return false;
	            }
	
	            if (event.getResult() == Result.ALLOW)
	            {
	                return true;
	            }
	            
	            emcLeft -= neededEMC;
	            itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, emcLeft);
	            
	            Block farmland = Blocks.farmland;
	
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), farmland.stepSound.getStepResourcePath(), (farmland.stepSound.getVolume() + 1.0F) / 2.0F, farmland.stepSound.getPitch() * 0.8F);

                if (world.isRemote)
                {
                    return true;
                }
                else
                {
                	//set farmland blocks to irrigated farmland
                    BlockHelper.setBlocksOfTypeAround(x, y, z, farmland, ModBlocks.irrigatedFarmland, 7, (CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack)), world);
                	
                	//set dirt blocks to farmland
                    BlockHelper.setBlocksOfTypeAround(x, y, z, Blocks.dirt, farmland, 7, (CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack)), world);
                    BlockHelper.setBlocksOfTypeAround(x, y, z, Blocks.grass, farmland, 7, (CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack)), world);
                    
                    return true;
                }
        	}
        	return false;
        }
    }

}
