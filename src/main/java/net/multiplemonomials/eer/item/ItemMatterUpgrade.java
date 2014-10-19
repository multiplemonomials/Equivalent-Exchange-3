package net.multiplemonomials.eer.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.multiplemonomials.eer.reference.Messages;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMatterUpgrade extends ItemEE
{
    public ItemMatterUpgrade()
    {
        super();
        this.setMaxStackSize(64);
        this.setUnlocalizedName(Names.Items.MATTER_UPGRADE);
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, Names.Items.MATTER_UPGRADE);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, Names.Items.MATTER_UPGRADE, Names.Items.MATTER_UPGRADE_SUBTYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.MATTER_UPGRADE_SUBTYPES.length - 1)]);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.MATTER_UPGRADE_SUBTYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean flag)
    {
        list.add(StatCollector.translateToLocal(Messages.UPGRADES_MACHINES));
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        switch (MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.MATTER_UPGRADE_SUBTYPES.length - 1))
        {
            case 0:
            {
                return EnumChatFormatting.WHITE + super.getItemStackDisplayName(itemStack);
            }
            case 1:
            {
                return EnumChatFormatting.RED + super.getItemStackDisplayName(itemStack);
            }
            default:
            {
                return EnumChatFormatting.WHITE + super.getItemStackDisplayName(itemStack);
            }
        }
    }
}
