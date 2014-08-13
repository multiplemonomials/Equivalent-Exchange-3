package net.multiplemonomials.eer.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.multiplemonomials.eer.item.*;
import net.multiplemonomials.eer.item.tool.*;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemEE alchemicalBag = new ItemAlchemicalBag();
    public static final ItemEE alchemicalDust = new ItemAlchemicalDust();
    public static final ItemEE alchemicalFuel = new ItemAlchemicalFuel();
    public static final ItemEE stoneInert = new ItemInertStone();
    public static final ItemEE shardMinium = new ItemMiniumShard();
    public static final ItemEE stoneMinium = new ItemMiniumStone();
    public static final ItemEE stonePhilosophers = new ItemPhilosophersStone();
    public static final ItemEE alchemicalUpgrade = new ItemAlchemicalInventoryUpgrade();
    public static final ItemEE chalk = new ItemChalk();
    public static final ItemEE diviningRod = new ItemDiviningRod();
    public static final ItemEE darkMatter = new ItemDarkMatter();
    public static final ItemEE talismanRepair = new ItemTalismanRepair();
    public static final ItemEE bandIron = new ItemBandIron();
    public static final ItemEE ringFlight = new ItemRingFlight();
    public static final ItemEE kleinStar = new ItemKleinStar();
    
    public static final ItemTool axeDarkMatter = new ItemAxeDarkMatter();
    public static final ItemTool pickaxeDarkMatter = new ItemPickaxeDarkMatter();
    public static final ItemTool shovelDarkMatter = new ItemShovelDarkMatter();
    
    public static final ItemHoe hoeDarkMatter = new ItemHoeDarkMatter();
    public static final ItemSword swordDarkMatter = new ItemSwordDarkMatter();
    public static final Item flintDarkMatter = new ItemFlintDarkMatter();
    
    public static void init()
    {
        GameRegistry.registerItem(alchemicalBag, Names.Items.ALCHEMICAL_BAG);
        GameRegistry.registerItem(alchemicalDust, Names.Items.ALCHEMICAL_DUST);
        GameRegistry.registerItem(alchemicalFuel, Names.Items.ALCHEMICAL_FUEL);
        GameRegistry.registerItem(stoneInert, Names.Items.INERT_STONE);
        GameRegistry.registerItem(shardMinium, Names.Items.MINIUM_SHARD);
        GameRegistry.registerItem(stoneMinium, Names.Items.MINIUM_STONE);
        GameRegistry.registerItem(stonePhilosophers, Names.Items.PHILOSOPHERS_STONE);
        GameRegistry.registerItem(chalk, Names.Items.CHALK);
        GameRegistry.registerItem(alchemicalUpgrade, Names.Items.ALCHEMICAL_UPGRADE);
        GameRegistry.registerItem(diviningRod, Names.Items.DIVINING_ROD);
        GameRegistry.registerItem(darkMatter, Names.Items.DARK_MATTER);
        GameRegistry.registerItem(talismanRepair, Names.Items.TALISMAN_REPAIR);
        GameRegistry.registerItem(bandIron, Names.Items.BAND_IRON);
        GameRegistry.registerItem(ringFlight, Names.Items.RING_FLIGHT);
        GameRegistry.registerItem(kleinStar, Names.Items.KLEIN_STAR);
        

        GameRegistry.registerItem(axeDarkMatter, Names.Tools.AXE_DARK_MATTER);
        GameRegistry.registerItem(hoeDarkMatter, Names.Tools.HOE_DARK_MATTER);
        GameRegistry.registerItem(pickaxeDarkMatter, Names.Tools.PICKAXE_DARK_MATTER);
        GameRegistry.registerItem(shovelDarkMatter, Names.Tools.SHOVEL_DARK_MATTER);
        GameRegistry.registerItem(swordDarkMatter, Names.Tools.SWORD_DARK_MATTER);
        GameRegistry.registerItem(flintDarkMatter, Names.Tools.FLINT_DARK_MATTER);

        
    }
}
