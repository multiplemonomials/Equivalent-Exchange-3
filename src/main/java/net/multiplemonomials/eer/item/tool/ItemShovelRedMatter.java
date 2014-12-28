package net.multiplemonomials.eer.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.BlockHelper;
import net.multiplemonomials.eer.util.EMCHelper;

public class ItemShovelRedMatter extends ItemShovelDarkMatter implements IStoresEMC {

	public ItemShovelRedMatter(Matter matterType) {
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
		//encode variables used in tick handler
		verifyItemStackHasNBTTag(itemStack);
		itemStack.stackTagCompound.setBoolean("inUse", true);
		itemStack.stackTagCompound.setInteger("currentX", x);
		itemStack.stackTagCompound.setInteger("currentY", y);
		itemStack.stackTagCompound.setInteger("currentZ", z);
		itemStack.stackTagCompound.setInteger("direction", side);
		itemStack.stackTagCompound.setInteger("useCount", 0);
		return true;
    }
	
	@Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.bow;
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isHeldItem) 
    {
    	//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		verifyItemStackHasNBTTag(itemStack);
    		if(itemStack.stackTagCompound.getBoolean("inUse"))
    		{
        		int x = itemStack.stackTagCompound.getInteger("currentX");
        		int y = itemStack.stackTagCompound.getInteger("currentY");
        		int z = itemStack.stackTagCompound.getInteger("currentZ");
        		ForgeDirection offset = ForgeDirection.values()[itemStack.stackTagCompound.getInteger("direction")];
        		
        		byte useCount = itemStack.stackTagCompound.getByte("useCount");

        		if(useCount != 0)
        		{
	        		x += offset.offsetX;
	        		y += offset.offsetY;
	        		z += offset.offsetZ;
        		}        		
    			if(useCount < 64 && (getDigSpeed(itemStack, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z)) > 1.0F))
    			{
	    			//take some EMC
	    	    	double emcLeft = getAvailableEMC(itemStack);
	    	    	double neededEMC = CommonConfiguration.RM_SHOVEL_REQUIRED_EMC_PER_BLOCK;
	
	    	    	if(emcLeft < neededEMC)
	    	    	{
	    	    		emcLeft += EMCHelper.consumeEMCFromPlayerInventory(player, neededEMC - emcLeft);
	    	    	}
	    	    	
	    	    	if(emcLeft >= neededEMC)
	    	    	{
	    	    		emcLeft -= neededEMC;
	            		itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, emcLeft);
	            		
	            		//now, DO something
	            		
	            		AxisAlignedBB boxToDig = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z);
	            		BlockHelper.instaMineBlocks(boxToDig, world, player, itemStack);
	            		
	            		itemStack.stackTagCompound.setInteger("currentX", x);
	            		itemStack.stackTagCompound.setInteger("currentY", y);
	            		itemStack.stackTagCompound.setInteger("currentZ", z);
	            		itemStack.stackTagCompound.setInteger("useCount", useCount);
	    	    	}
	    	    	else
	    	    	{
	    	    		//out of EMC
	    	    		itemStack.stackTagCompound.setBoolean("inUse", false);
	            		itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, emcLeft);
	
	    	    	}
    			}
    			else //dug 64 blocks
    			{
    	    		itemStack.stackTagCompound.setBoolean("inUse", false);
    			}
    	    	    	    	
    		}
    	}
    	
    }
    
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int count)
    {
        itemStack.stackTagCompound.setBoolean("inUse", false);
    }

}
