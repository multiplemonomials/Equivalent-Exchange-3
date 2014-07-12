package com.pahimar.ee3.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.pahimar.ee3.item.ItemEE;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIronBand extends ItemEE 
{
	private IIcon[] icons;
	
	public ItemIronBand()
	{
		super();
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(Names.Items.IRON_BAND);
        setMaxDamage(0);
	}
	
    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.IRON_BAND);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s%s", Textures.RESOURCE_PREFIX, Names.Items.IRON_BAND, Names.Items.IRON_BAND_SUBTYPES[
           MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.IRON_BAND_SUBTYPES.length - 1)]);
       
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.IRON_BAND_SUBTYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[Names.Items.IRON_BAND_SUBTYPES.length];

        for (int i = 0; i < Names.Items.IRON_BAND_SUBTYPES.length; i++)
        {
            icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.IRON_BAND + Names.Items.IRON_BAND_SUBTYPES[i]);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, Names.Items.IRON_BAND_SUBTYPES.length - 1)];
    }
}
