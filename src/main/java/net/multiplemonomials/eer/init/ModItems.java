package net.multiplemonomials.eer.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.multiplemonomials.eer.item.ItemAlchemicalBag;
import net.multiplemonomials.eer.item.ItemAlchemicalDust;
import net.multiplemonomials.eer.item.ItemAlchemicalFuel;
import net.multiplemonomials.eer.item.ItemAlchemicalInventoryUpgrade;
import net.multiplemonomials.eer.item.ItemBandIron;
import net.multiplemonomials.eer.item.ItemChalk;
import net.multiplemonomials.eer.item.ItemMatter;
import net.multiplemonomials.eer.item.ItemDiviningRod;
import net.multiplemonomials.eer.item.ItemEE;
import net.multiplemonomials.eer.item.ItemInertStone;
import net.multiplemonomials.eer.item.ItemKleinStar;
import net.multiplemonomials.eer.item.ItemMatterUpgrade;
import net.multiplemonomials.eer.item.ItemMiniumShard;
import net.multiplemonomials.eer.item.ItemMiniumStone;
import net.multiplemonomials.eer.item.ItemPhilosophersStone;
import net.multiplemonomials.eer.item.ItemRingBlackHole;
import net.multiplemonomials.eer.item.ItemRingFlight;
import net.multiplemonomials.eer.item.ItemTalismanRepair;
import net.multiplemonomials.eer.item.tool.ItemAxeDarkMatter;
import net.multiplemonomials.eer.item.tool.ItemFlintDarkMatter;
import net.multiplemonomials.eer.item.tool.ItemHoeDarkMatter;
import net.multiplemonomials.eer.item.tool.ItemPickaxeMatter;
import net.multiplemonomials.eer.item.tool.ItemShovelDarkMatter;
import net.multiplemonomials.eer.item.tool.ItemSwordDarkMatter;
import net.multiplemonomials.eer.item.tool.Matter;
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
    public static final ItemEE matterUpgrade = new ItemMatterUpgrade();
    public static final ItemEE chalk = new ItemChalk();
    public static final ItemEE diviningRod = new ItemDiviningRod();
    public static final ItemEE matter = new ItemMatter();
    public static final ItemEE talismanRepair = new ItemTalismanRepair();
    public static final ItemEE bandIron = new ItemBandIron();
    public static final ItemEE ringFlight = new ItemRingFlight();
    //Black hole band
    public static final ItemEE ringMagnet = new ItemRingBlackHole();
    public static final ItemEE kleinStar = new ItemKleinStar();
    
    public static final ItemTool axeDarkMatter = new ItemAxeDarkMatter();
    public static final ItemTool pickaxeDarkMatter = new ItemPickaxeMatter(Matter.DarkMatter);
    public static final ItemTool pickaxeRedMatter = new ItemPickaxeMatter(Matter.RedMatter);
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
        GameRegistry.registerItem(matterUpgrade, Names.Items.MATTER_UPGRADE);
        GameRegistry.registerItem(diviningRod, Names.Items.DIVINING_ROD);
        GameRegistry.registerItem(matter, Names.Items.DARK_MATTER);
        GameRegistry.registerItem(talismanRepair, Names.Items.TALISMAN_REPAIR);
        GameRegistry.registerItem(bandIron, Names.Items.BAND_IRON);
        GameRegistry.registerItem(ringFlight, Names.Items.RING_FLIGHT);
        GameRegistry.registerItem(ringMagnet, Names.Items.RING_MAGNET);
        GameRegistry.registerItem(kleinStar, Names.Items.KLEIN_STAR);
        

        GameRegistry.registerItem(axeDarkMatter, Names.Tools.AXE_DARK_MATTER);
        GameRegistry.registerItem(hoeDarkMatter, Names.Tools.HOE_DARK_MATTER);
        GameRegistry.registerItem(pickaxeDarkMatter, Names.Tools.PICKAXE_DARK_MATTER);
        GameRegistry.registerItem(pickaxeRedMatter, Names.Tools.PICKAXE_RED_MATTER);
        GameRegistry.registerItem(shovelDarkMatter, Names.Tools.SHOVEL_DARK_MATTER);
        GameRegistry.registerItem(swordDarkMatter, Names.Tools.SWORD_DARK_MATTER);
        GameRegistry.registerItem(flintDarkMatter, Names.Tools.FLINT_DARK_MATTER);

        
    }
}
