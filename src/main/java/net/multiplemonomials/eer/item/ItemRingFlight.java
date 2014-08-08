package net.multiplemonomials.eer.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.reference.Key;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.EMCHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRingFlight extends ItemEE implements IKeyBound
{	
	IIcon [] icons;
	
	//metadatas: 
	//0 - out of fuel
	//1 - has fuel, but not pushing mobs
	//2 - has fuel and is pushing mobs
	
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
    		ItemStack heldItem = entityPlayer.getHeldItem();
    		
    		if(heldItem.getItem() instanceof ItemRingFlight)
    		{
    			if(heldItem.getItemDamage() == 1)
    			{
    				heldItem.setItemDamage(2);
    			}
    			else if(heldItem.getItemDamage() == 2)
    			{
    				heldItem.setItemDamage(1);
    			}
    				
    		}
    	}
    	else if(key == Key.JUMP)
    	{
    		//TODO: play sound
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
       return icons[MathHelper.clamp_int(damageValue - 1, 0, icons.length - 1)];
    }
    
    public boolean isPushingMobsAway(ItemStack itemStack)
    {
    	return itemStack.getItemDamage() > 1;
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isHeldItem) 
    {
    	//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		//use some fuel
    		NBTTagCompound stackTag = itemStack.stackTagCompound;
    		
    		if(stackTag == null)
    		{
    			stackTag = new NBTTagCompound();
    			stackTag.setFloat("fuelEMCLeft", 0F);
    		}
    		
    		float fuelEMCLeft = stackTag.getFloat("fuelEMCLeft");
    		
    		
    		//use some fuel
    		if(player.capabilities.isFlying && (!player.capabilities.isCreativeMode))
    		{
    			fuelEMCLeft -= Reference.FLYING_RING_EMC_DRAIN_PER_TICK;
    		}
    		
    		if(isPushingMobsAway(itemStack))
    		{
    			pushMobsAway(player);
    			if((!player.capabilities.isCreativeMode))
    			{
    				fuelEMCLeft -= Reference.FLYING_RING_EMC_DRAIN_PER_TICK_MOB_PUSH;
    			}
    		}
    		
    		if(fuelEMCLeft < 0)
    		{
    			fuelEMCLeft += EMCHelper.consumeEMCFromPlayerInventory(player, 10 * Reference.FLYING_RING_EMC_DRAIN_PER_TICK);
    		}
    		
    		if(fuelEMCLeft < 0)
    		{
    			itemStack.setItemDamage(0);
    		}
    		else if(itemStack.getItemDamage() == 0)
    		{
    			itemStack.setItemDamage(1);
    		}
    		
    		stackTag.setFloat("fuelEMCLeft", fuelEMCLeft);
    		
    		itemStack.stackTagCompound = stackTag;
    		
    		
    	}
    }
    
	private void pushMobsAway(EntityPlayer player) 
	{
		AxisAlignedBB pushBox = AxisAlignedBB.getBoundingBox(player.posX - 5, player.posY - 2, player.posZ - 5, player.posX + 5, player.posY + 1, player.posZ + 5);
		for(Object object : player.worldObj.getEntitiesWithinAABB(EntityMob.class, pushBox))
		{
			assert(object instanceof EntityMob);
			EntityMob mob = (EntityMob)object;
			//mob.motionX = 1/(mob.posX - player.posX);
			//mob.motionZ = 1/(mob.posZ - player.posZ);
			mob.motionX = (mob.posX - player.posX) / 5;
			mob.motionZ = (mob.posZ - player.posZ) / 5;
		}
	}
    
    
}
