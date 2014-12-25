package net.multiplemonomials.eer.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;

public class ItemMatter extends ItemEE 
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
	
	public ItemMatter()
	{
		  super();
	      this.setUnlocalizedName(Names.Items.MATTER);
	      
	      //override stack size setting
	      this.maxStackSize = 64;
	      
	      this.setHasSubtypes(true);

	}
	
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s%s", Reference.RESOURCE_PREFIX, Names.Items.MATTER, Names.Items.MATTER_SUBTYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.ALCHEMICAL_FUEL_SUBTYPES.length - 1)]);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.MATTER_SUBTYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, Names.Items.MATTER_SUBTYPES.length - 1)];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[Names.Items.MATTER_SUBTYPES.length];

        for (int i = 0; i < Names.Items.MATTER_SUBTYPES.length; i++)
        {
            icons[i] = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + Names.Items.MATTER + Names.Items.MATTER_SUBTYPES[i]);
        }
    }
}
