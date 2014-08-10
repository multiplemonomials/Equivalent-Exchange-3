package net.multiplemonomials.eer.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBandIron extends ItemEE 
{
	private IIcon[] icons;
	
	public ItemBandIron()
	{
		super();
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(Names.Items.BAND_IRON);
        setMaxDamage(0);
	}
	
    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, Names.Items.BAND_IRON);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s%s", Reference.RESOURCE_PREFIX, Names.Items.BAND_IRON, Names.Items.IRON_BAND_SUBTYPES[
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
            icons[i] = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + Names.Items.BAND_IRON + Names.Items.IRON_BAND_SUBTYPES[i]);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, Names.Items.IRON_BAND_SUBTYPES.length - 1)];
    }
}
