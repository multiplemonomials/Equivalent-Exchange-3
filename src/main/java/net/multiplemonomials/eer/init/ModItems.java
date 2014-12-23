package net.multiplemonomials.eer.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.multiplemonomials.eer.item.ItemAlchemicalBag;
import net.multiplemonomials.eer.item.ItemAlchemicalDust;
import net.multiplemonomials.eer.item.ItemAlchemicalFuel;
import net.multiplemonomials.eer.item.ItemAlchemicalInventoryUpgrade;
import net.multiplemonomials.eer.item.ItemAssignmentGUIActivator;
import net.multiplemonomials.eer.item.ItemBandIron;
import net.multiplemonomials.eer.item.ItemChalk;
import net.multiplemonomials.eer.item.ItemDiviningRod;
import net.multiplemonomials.eer.item.ItemEE;
import net.multiplemonomials.eer.item.ItemInertStone;
import net.multiplemonomials.eer.item.ItemKleinStar;
import net.multiplemonomials.eer.item.ItemMatter;
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
import net.multiplemonomials.eer.item.tool.ItemHoeRedMatter;
import net.multiplemonomials.eer.item.tool.ItemPickaxeDarkMatter;
import net.multiplemonomials.eer.item.tool.ItemPickaxeRedMatter;
import net.multiplemonomials.eer.item.tool.ItemShovelDarkMatter;
import net.multiplemonomials.eer.item.tool.ItemSwordDarkMatter;
import net.multiplemonomials.eer.item.tool.Matter;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static ItemEE alchemicalBag;
    public static ItemEE alchemicalDust;
    public static ItemEE alchemicalFuel;
    public static ItemEE stoneInert;
    public static ItemEE shardMinium;
    public static ItemEE stoneMinium;
    public static ItemEE stonePhilosophers;
    public static ItemEE alchemicalUpgrade;
    public static ItemEE matterUpgrade;
    public static ItemEE chalk;
    public static ItemEE diviningRod;
    public static ItemEE matter;
    public static ItemEE talismanRepair;
    public static ItemEE bandIron;
    public static ItemEE ringFlight;
    //Black hole band
    public static ItemEE ringMagnet;
    public static ItemEE kleinStar;
    public static ItemEE assignmentGUIActivator;
    
    public static ItemTool axeDarkMatter;
    public static ItemTool pickaxeDarkMatter;
    public static ItemTool pickaxeRedMatter;
    public static ItemTool shovelDarkMatter;
    public static ItemHoe hoeDarkMatter;
    public static ItemHoe hoeRedMatter;
    public static ItemSword swordDarkMatter;
    public static Item flintDarkMatter;
    
    public static void init()
    {
    	
        alchemicalBag = new ItemAlchemicalBag();
        alchemicalDust = new ItemAlchemicalDust();
        alchemicalFuel = new ItemAlchemicalFuel();
        stoneInert = new ItemInertStone();
        shardMinium = new ItemMiniumShard();
        stoneMinium = new ItemMiniumStone();
        stonePhilosophers = new ItemPhilosophersStone();
        alchemicalUpgrade = new ItemAlchemicalInventoryUpgrade();
        matterUpgrade = new ItemMatterUpgrade();
        chalk = new ItemChalk();
        diviningRod = new ItemDiviningRod();
        matter = new ItemMatter();
        talismanRepair = new ItemTalismanRepair();
        bandIron = new ItemBandIron();
        ringFlight = new ItemRingFlight();
        ringMagnet = new ItemRingBlackHole();
        kleinStar = new ItemKleinStar();
        assignmentGUIActivator = new ItemAssignmentGUIActivator();
        
        axeDarkMatter = new ItemAxeDarkMatter();
        pickaxeDarkMatter = new ItemPickaxeDarkMatter(Matter.DarkMatter);
        pickaxeRedMatter = new ItemPickaxeRedMatter(Matter.RedMatter);
        shovelDarkMatter = new ItemShovelDarkMatter();
        hoeDarkMatter = new ItemHoeDarkMatter(Matter.DarkMatter);
        hoeRedMatter = new ItemHoeRedMatter(Matter.RedMatter);
        swordDarkMatter = new ItemSwordDarkMatter();
        flintDarkMatter = new ItemFlintDarkMatter();
    	
    	
    	
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
        GameRegistry.registerItem(assignmentGUIActivator, Names.Items.ASSIGNMENT_GUI_ACTIVATOR);        

        GameRegistry.registerItem(axeDarkMatter, Names.Tools.AXE_DARK_MATTER);
        GameRegistry.registerItem(hoeDarkMatter, Names.Tools.HOE_DARK_MATTER);
        GameRegistry.registerItem(hoeRedMatter, Names.Tools.HOE_RED_MATTER);
        GameRegistry.registerItem(pickaxeDarkMatter, Names.Tools.PICKAXE_DARK_MATTER);
        GameRegistry.registerItem(pickaxeRedMatter, Names.Tools.PICKAXE_RED_MATTER);
        GameRegistry.registerItem(shovelDarkMatter, Names.Tools.SHOVEL_DARK_MATTER);
        GameRegistry.registerItem(swordDarkMatter, Names.Tools.SWORD_DARK_MATTER);
        GameRegistry.registerItem(flintDarkMatter, Names.Tools.FLINT_DARK_MATTER);

        
    }
}
