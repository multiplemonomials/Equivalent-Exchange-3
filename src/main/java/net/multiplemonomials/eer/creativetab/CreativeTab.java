package net.multiplemonomials.eer.creativetab;

import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class CreativeTab
{
    public static final CreativeTabs EER_TAB = new CreativeTabs(Reference.MOD_ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.matter;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel()
        {
            return StatCollector.translateToLocal("key.categories.eer");
        }
    };
}
