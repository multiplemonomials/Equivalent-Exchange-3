package com.pahimar.ee3.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.pahimar.ee3.interfaces.IKeyBound;
import com.pahimar.ee3.reference.Key;
import com.pahimar.ee3.reference.Names;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRingFlight extends ItemEE implements IKeyBound
{	
	IIcon [] icons;
	
    public ItemRingFlight()
    {
        super();
        this.setUnlocalizedName(Names.Items.RING_FLIGHT);
        
        this.setMaxStackSize(1);
        
        icons = new IIcon[2];
    }

	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key) 
	{
    	if(key == Key.TOGGLE)
    	{
    		ItemStack heldItem = entityPlayer.getHeldItem();
    		
    		if(heldItem.getItem() instanceof ItemRingFlight)
    		{
    			setDamage(heldItem, heldItem.getItemDamage() == 1 ? 0 : 1);
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
    
    
}
