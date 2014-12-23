package net.multiplemonomials.eer.init;

import net.minecraft.block.Block;
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
	public static final BlockEE condenser = new BlockCondenser();
	public static final BlockEE transmutationTablet = new BlockTransmutationTablet();
	public static final Block   irrigatedFarmland = new BlockIrrigatedFarmland();
	public static final BlockEE energyCollectorAntimatter = new BlockEnergyCollector((byte) 1);
	public static final BlockEE energyCollectorDarkMatter = new BlockEnergyCollector((byte) 2);
	public static final BlockEE energyCollectorRedMatter = new BlockEnergyCollector((byte) 3);
	
	public static final BlockEE antiMatterRelayAntimatter = new BlockAMRelay((byte) 1);
	public static final BlockEE antiMatterRelayDarkMatter = new BlockAMRelay((byte) 2);
	public static final BlockEE antiMatterRelayRedMatter = new BlockAMRelay((byte) 3);

    public static void init()
    {
        GameRegistry.registerBlock(chalkBlock, Names.Blocks.CHALK);
        GameRegistry.registerBlock(alchemicalFuelBlock, ItemBlockAlchemicalFuel.class, Names.Blocks.ALCHEMICAL_FUEL);
        GameRegistry.registerBlock(alchemicalChest, ItemBlockAlchemicalChest.class, Names.Blocks.ALCHEMICAL_CHEST);
        GameRegistry.registerBlock(aludel, Names.Blocks.ALUDEL);
        GameRegistry.registerBlock(calcinator, Names.Blocks.CALCINATOR);
        GameRegistry.registerBlock(glassBell, Names.Blocks.GLASS_BELL);
        GameRegistry.registerBlock(condenser, Names.Blocks.CONDENSER);
        GameRegistry.registerBlock(transmutationTablet, Names.Blocks.TRANSMUTATION_TABLET);
        GameRegistry.registerBlock(irrigatedFarmland, Names.Blocks.IRRIGATED_FARMLAND);
        
        GameRegistry.registerBlock(energyCollectorAntimatter, Names.Blocks.ENERGY_COLLECTOR + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[0]);
        GameRegistry.registerBlock(energyCollectorDarkMatter, Names.Blocks.ENERGY_COLLECTOR + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[1]);
        GameRegistry.registerBlock(energyCollectorRedMatter, Names.Blocks.ENERGY_COLLECTOR + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[2]);
    
    	GameRegistry.registerBlock(antiMatterRelayAntimatter, "antiMatterRelay" + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[0]);
    	GameRegistry.registerBlock(antiMatterRelayDarkMatter, "antiMatterRelay" + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[1]);
    	GameRegistry.registerBlock(antiMatterRelayRedMatter, "antiMatterRelay" + Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[2]);
    }
}
