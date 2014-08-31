package net.multiplemonomials.eer.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemKleinStar extends ItemEE
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemKleinStar()
    {
        super();
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(Names.Items.KLEIN_STAR);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, Names.Items.KLEIN_STAR);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, Names.Items.KLEIN_STAR, Names.Items.KLEIN_STAR_SUBTYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.KLEIN_STAR_SUBTYPES.length - 1)]);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.KLEIN_STAR_SUBTYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, Names.Items.KLEIN_STAR_SUBTYPES.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[Names.Items.KLEIN_STAR_SUBTYPES.length];

        for (int i = 0; i < Names.Items.KLEIN_STAR_SUBTYPES.length; i++)
        {
            icons[i] = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + Names.Items.KLEIN_STAR  + Names.Items.KLEIN_STAR_SUBTYPES[i]);
        }
    }

    /**
     * Attempts to take the provided amount of EMC from the Klein Star.
     * It might return less than that or 0, though.
     * @param itemStack
     * @param idealEMC
     * @return
     */
	public static double takeEMC(ItemStack itemStack, double idealEMC)
	{
		verifyItemStackHasNBTTag(itemStack);
		
		double currentEMC = itemStack.stackTagCompound.getDouble("EMCValue");
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
		
		itemStack.stackTagCompound.setDouble("EMCValue", newEMC);
		
		return EMCGotten;
				
		
	}
	
	/**
	 * 
	 * @param itemStack
	 * @return The max amount of EMC that the provided klein star can store
	 */
	public static double getMaxStorableEMC(ItemStack itemStack)
	{
		return CommonConfiguration.HALF_KLEIN_STAR_ICHI_EMC * Math.pow(2, itemStack.getItemDamage() + 1);
	}
	
	public static double getAvailableEMC(ItemStack itemStack)
	{
		verifyItemStackHasNBTTag(itemStack);
		
		return itemStack.stackTagCompound.getDouble("EMCValue");
	}

	/**
	 * Makes sure the klein star itemstack supplied has its proper NBT tagging
	 * @param itemStack
	 */
	private static void verifyItemStackHasNBTTag(ItemStack itemStack)
	{
		if(itemStack.getTagCompound() == null)
		{
			itemStack.stackTagCompound = new NBTTagCompound();
		}
		
	}

	/**
	 * Tries to add the given EMC to the Klein Star
	 * @param itemStack
	 * @param EMCToAdd
	 * 
	 * @return The EMC it didn't add because it hit the limit
	 */
	public static double addEMC(ItemStack itemStack, double EMCToAdd)
	{
		verifyItemStackHasNBTTag(itemStack);
		double currentEMC = itemStack.stackTagCompound.getDouble("EMCValue");
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
		
		itemStack.stackTagCompound.setDouble("EMCValue", currentEMC);
		
		return failedToAddEMC;

	}
}
