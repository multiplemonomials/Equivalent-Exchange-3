package net.multiplemonomials.eer.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.reference.Key;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.EMCHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRingFlight extends ItemStoresEMC implements IKeyBound, IBauble
{	
	IIcon [] icons;
	
	//metadatas: 
	//0 - not pushing mobs
	//1 - is pushing mobs 
	
    public ItemRingFlight()
    {
        super();
        this.setUnlocalizedName(Names.Items.RING_FLIGHT);
        
        this.setMaxStackSize(1);
        
        icons = new IIcon[2];
    }

    @SideOnly(Side.CLIENT)
	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key) 
	{
    	if(key == Key.TOGGLE)
    	{    		

    		if(itemStack != null && itemStack.getItem() instanceof ItemRingFlight)
    		{
    			if(itemStack.getItemDamage() == 0)
    			{
    				itemStack.setItemDamage(1);
    			}
    			else if(itemStack.getItemDamage() == 1)
    			{
    				itemStack.setItemDamage(0);
    			}
    			
    			return;
    		}
    	}
		
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "Inactive");
        
        icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "Active");

    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damageValue)
    {
       return icons[MathHelper.clamp_int(damageValue, 0, icons.length - 1)];
    }
    
    public boolean isPushingMobsAway(ItemStack itemStack)
    {
    	return itemStack.getItemDamage() > 0;
    }
    
	private void pushMobsAway(EntityPlayer player) 
	{
		AxisAlignedBB pushBox = AxisAlignedBB.getBoundingBox(player.posX - 5, player.posY - 2, player.posZ - 5, player.posX + 5, player.posY + 1, player.posZ + 5);
		for(Object object : player.worldObj.getEntitiesWithinAABB(EntityMob.class, pushBox))
		{
			assert(object instanceof EntityMob);
			EntityMob mob = (EntityMob)object;
			
			//what units are the EntityMob.motion{X, Y, Z} variables in?  I sure don't know.
			mob.motionX = (mob.posX - player.posX) / 5;
			mob.motionZ = (mob.posZ - player.posZ) / 5;
		}
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

	@Override
	public void onWornTick(ItemStack itemStack, EntityLivingBase entity)
	{
		
		//Baubles ticks REALLY fast compared to inventory ticks
		
		//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		double fuelEMCLeft = getAvailableEMC(itemStack);
	
    		//use some fuel
    		if(player.capabilities.isFlying && (!player.capabilities.isCreativeMode))
    		{
    			fuelEMCLeft -= CommonConfiguration.FLYING_RING_EMC_DRAIN_PER_TICK;
    		}
    		
    		if(isPushingMobsAway(itemStack))
    		{
    			pushMobsAway(player);
    			if((!player.capabilities.isCreativeMode))
    			{
    				fuelEMCLeft -= CommonConfiguration.FLYING_RING_EMC_DRAIN_PER_TICK_MOB_PUSH;
    			}
    		}
    		
    		if(fuelEMCLeft < 0)
    		{
    			fuelEMCLeft += EMCHelper.consumeEMCFromPlayerInventory(player, 10 * CommonConfiguration.FLYING_RING_EMC_DRAIN_PER_TICK);
    		}
    		
    		if(fuelEMCLeft < 0)
    		{
    			player.capabilities.allowFlying = false;
    			player.capabilities.isFlying = false;
    		}
    		else
    		{
    			player.capabilities.allowFlying = true;
    		}
    		
    		itemStack.stackTagCompound.setDouble("fuelEMCLeft", fuelEMCLeft); 		
    		
    	}
		
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase entity)
	{
	   	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		
    		if(player.capabilities.isCreativeMode)
    		{
    			return;
    		}
    		
			player.capabilities.allowFlying = true;

    	}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase entity)
	{
	   	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		
    		if(player.capabilities.isCreativeMode)
    		{
    			return;
    		}
    		
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;

    	}
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
