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

public class ItemRingBlackHole extends ItemStoresEMC implements IKeyBound, IBauble
{	
	IIcon [] icons;
	
	//metadatas: 
	//0 - not collecting
	//1 - is collecting 
	
    public ItemRingBlackHole()
    {
        super();
        this.setUnlocalizedName(Names.Items.RING_MAGNET);
        
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
    
    public boolean isCollectingItems(ItemStack itemStack)
    {
    	return itemStack.getItemDamage() > 0;
    }
    
    /** Gets the items around the player and collects them (like a magnet!)
     * 
     * Returns true if item(S) are collected, false if there's nothing to collect
     * 		use so EMC isn't used up for nothing
     */
	private boolean collectItems(EntityPlayer player) 
	{
		//TODO: range of ring
		AxisAlignedBB pushBox = AxisAlignedBB.getBoundingBox(player.posX - 5, player.posY - 2, player.posZ - 5, player.posX + 5, player.posY + 1, player.posZ + 5);
		for(Object object : player.worldObj.getEntitiesWithinAABB(EntityItem.class, pushBox))
		{
			assert(object instanceof EntityItem);
			EntityItem item = (EntityItem)object;
			
			item.onCollideWithPlayer(player);
		}
		if(item != null) return true;
		return false;
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
		
		//Baubles ticks REALLY fast compared to inventory ticks
		
		//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
    		
    		if(isCollectingItems(itemStack)) //Only do stuff it it's on
    		{
    			EntityPlayer player = (EntityPlayer)entity;
    			double fuelEMCLeft = getAvailableEMC(itemStack);
    		
    			if((!player.capabilities.isCreativeMode) && collectItems())
    			{
    				fuelEMCLeft -= drainPerTick;
    			}
    			
    			if(fuelEMCLeft < 0)
    			{
    				fuelEMCLeft += EMCHelper.consumeEMCFromPlayerInventory(player, 10 * drainPerTick);
    			}
    			itemStack.stackTagCompound.setDouble("fuelEMCLeft", fuelEMCLeft); 		
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
