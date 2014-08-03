package net.multiplemonomials.eer.init;

import net.multiplemonomials.eer.block.*;

import net.multiplemonomials.eer.item.ItemBlockAlchemicalChest;
import net.multiplemonomials.eer.item.ItemBlockAlchemicalFuel;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    public static final BlockEE chalkBlock = new BlockChalk();
    public static final BlockEE alchemicalFuelBlock = new BlockAlchemicalFuel();
    public static final BlockEE alchemicalChest = new BlockAlchemicalChest();
    public static final BlockEE aludel = new BlockAludel();
    public static final BlockEE calcinator = new BlockCalcinator();
    public static final BlockEE glassBell = new BlockGlassBell();
    public static final BlockEE researchStation = new BlockResearchStation();
	public static final BlockEE condenser = new BlockCondenser();
	public static final BlockEE transmutationTablet = new BlockTransmutationTablet();

    public static void init()
    {
        GameRegistry.registerBlock(chalkBlock, Names.Blocks.CHALK);
        GameRegistry.registerBlock(alchemicalFuelBlock, ItemBlockAlchemicalFuel.class, Names.Blocks.ALCHEMICAL_FUEL);
        GameRegistry.registerBlock(alchemicalChest, ItemBlockAlchemicalChest.class, Names.Blocks.ALCHEMICAL_CHEST);
        GameRegistry.registerBlock(aludel, Names.Blocks.ALUDEL);
        GameRegistry.registerBlock(calcinator, Names.Blocks.CALCINATOR);
        GameRegistry.registerBlock(glassBell, Names.Blocks.GLASS_BELL);
        GameRegistry.registerBlock(researchStation, Names.Blocks.RESEARCH_STATION);
        GameRegistry.registerBlock(condenser, Names.Blocks.CONDENSER);
        GameRegistry.registerBlock(transmutationTablet, Names.Blocks.TRANSMUTATION_TABLET);

    }
}
