package net.multiplemonomials.eer.item.tool;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.EMCHelper;

public class ItemSwordRedMatter extends ItemSwordDarkMatter implements IStoresEMC {

	public ItemSwordRedMatter(Matter matterType) {
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
	
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	verifyItemStackHasNBTTag(itemStack);
    	
    	
    	int damageToDo = getDamage(itemStack) == CommonConfiguration.MAX_ITEM_CHARGES ? 2 : ((CommonConfiguration.MAX_ITEM_CHARGES - getDamage(itemStack) + 1) * 2);
		itemStack.stackTagCompound.setInteger("damageToDo", damageToDo);

		if(!player.capabilities.isCreativeMode)
		{
			//ensure enough EMC is available
	    	double emcLeft = getAvailableEMC(itemStack);
	    	double neededEMC = CommonConfiguration.RM_HOE_REQUIRED_EMC_PER_BLOCK * damageToDo;

	    	if(emcLeft < neededEMC)
	    	{
	    		emcLeft += EMCHelper.consumeEMCFromPlayerInventory(player, neededEMC - emcLeft);
	    	}
	    	
    		itemStack.stackTagCompound.setDouble(Names.NBT.EMC_STORED, emcLeft);
	    	
	    	if(emcLeft < neededEMC)
	    	{
	    		return itemStack;
	    	}
		}
    	
    	
    	player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
    	

        return itemStack;
    }
	
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.bow;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 5000;
    }
    
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int count)
    {
        if(count > 4500)
        {
        	int damageToDo = itemStack.stackTagCompound.getInteger("damageToDo");
	    	double neededEMC = CommonConfiguration.RM_SWORD_REQUIRED_EMC_PER_DAMAGE_POINT * damageToDo;
	    	
	    	tryTakeEMC(itemStack, neededEMC); //we checked this earlier, so there should be EMC
	    	
	    	//now, actually DO something
	    	AxisAlignedBB pushBox = AxisAlignedBB.getBoundingBox(player.posX - 5, player.posY - 2, player.posZ - 5, player.posX + 5, player.posY + 1, player.posZ + 5);
			for(Object object : player.worldObj.getEntitiesWithinAABB(EntityMob.class, pushBox))
			{
				EntityMob mob = (EntityMob)object;
				
				mob.attackEntityFrom(DamageSource.causePlayerDamage(player), damageToDo);
			}
        }
        
        
    }

}
