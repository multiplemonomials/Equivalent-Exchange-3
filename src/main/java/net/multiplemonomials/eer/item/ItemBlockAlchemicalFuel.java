package net.multiplemonomials.eer.item;

import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockAlchemicalFuel extends ItemMultiTexture
{
    public ItemBlockAlchemicalFuel(Block block)
    {
        super(ModBlocks.alchemicalFuelBlock, ModBlocks.alchemicalFuelBlock, Names.Items.ALCHEMICAL_FUEL_SUBTYPES);
    }
}
