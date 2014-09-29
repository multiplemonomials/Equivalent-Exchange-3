package net.multiplemonomials.eer.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.EMCHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;

public class ItemRingBlackHole extends ItemStoresEMC implements IBauble
{	
	IIcon icon;
	
    public ItemRingBlackHole()
    {
        super();
        this.setUnlocalizedName(Names.Items.RING_MAGNET);
        
        this.setMaxStackSize(1);
        
    }
    
    /** Gets the items around the player and collects them (like a magnet!)
     * 
     * Returns true if item(S) are collected, false if there's nothing to collect
     * 		use so EMC isn't used up for nothing
     */
	@SuppressWarnings("unchecked")
	private double collectItems(EntityPlayer player) 
	{
		//TODO: range of ring
		
		//use a small amount of EMC even if no items were pushed
		double itemsPushed = .01;
		
		//this code credit ChickenBones' NEI
		
		float distancexz = 16;
		double maxspeedxz = 0.5;
		double maxspeedy = 0.5;
		double speedxz = 0.05;
		double speedy = 0.07;
		List<EntityItem> items;
		if (player.worldObj.isRemote) 
		{
			return 0;
		} 
		else
		{
			items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(30, 30, 10));
		}
		for (Iterator<EntityItem> iterator = items.iterator(); iterator.hasNext(); )
		{
			EntityItem item = iterator.next();
			if (item.delayBeforeCanPickup > 0) 
			{
				continue;
			}
			
			if (item.isDead && player.worldObj.isRemote) 
			{
				iterator.remove();
			}
			
			itemsPushed += 1;
			
			double dx = player.posX - item.posX;
			double dy = player.posY + player.getEyeHeight() - item.posY;
			double dz = player.posZ - item.posZ;
			double absxz = Math.sqrt(dx * dx + dz * dz);
			double absy = Math.abs(dy);
			if (absxz > distancexz) 
			{
				continue;
			}
			if (absxz > 1)
			{
				dx /= absxz;
				dz /= absxz;
			}
			if (absy > 1)
			{
				dy /= absy;
			}
			double vx = item.motionX + speedxz * dx;
			double vy = item.motionY + speedy * dy;
			double vz = item.motionZ + speedxz * dz;
			double absvxz = Math.sqrt(vx * vx + vz * vz);
			double absvy = Math.abs(vy);
			double rationspeedxz = absvxz / maxspeedxz;
			if (rationspeedxz > 1)
			{
				vx /= rationspeedxz;
				vz /= rationspeedxz;
			}
			double rationspeedy = absvy / maxspeedy;
			if (rationspeedy > 1)
			{
				vy /= rationspeedy;
			}
			if (absvxz < 0.2 && absxz < 0.2 && player.worldObj.isRemote)
			{
				item.setDead();
			}
			item.setVelocity(vx, vy, vz);
		}
		return itemsPushed;
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
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.RING;
	}

	//TODO: config option for EMC-Usage
	int drainPerTick = 50;

	@Override
	public void onWornTick(ItemStack itemStack, EntityLivingBase entity)
	{		
		//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
			EntityPlayer player = (EntityPlayer)entity;
			
			if(!player.capabilities.isCreativeMode)
			{
    			double fuelEMCLeft = getAvailableEMC(itemStack);
    		
    			if(fuelEMCLeft <= 0)
    			{
    				fuelEMCLeft += EMCHelper.consumeEMCFromPlayerInventory(player, 10 * drainPerTick);
    			}
    			if(fuelEMCLeft > 0)
    			{
        			fuelEMCLeft -= (drainPerTick * collectItems(player));
    			}

    			itemStack.stackTagCompound.setDouble("storedEMC", fuelEMCLeft);
			}
			else
			{
				collectItems(player);
			}
    		
    	}
		
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase entity)
	{
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase entity)
	{
		
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
    
    
}
