package net.multiplemonomials.eer.handler;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.oredict.OreDictionary;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.item.crafting.RecipesAlchemicalBagDyes;
import net.multiplemonomials.eer.registry.AludelRecipeRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler
{
    @SuppressWarnings("unchecked")
	public static void init()
    {
        // Add in the ability to dye Alchemical Bags
        CraftingManager.getInstance().getRecipeList().add(new RecipesAlchemicalBagDyes());
        
        //add Dark Matter crafting recipe
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.darkMatter), new Object[] {"AAA", "ADA", "AAA", 'A', new ItemStack(ModItems.alchemicalFuel, 1, 2), 'D', Blocks.diamond_block});
        
        //add Dark Matter tools' crafting recipies
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.pickaxeDarkMatter), new Object[] {"AAA", " D ", " D ", 'A', ModItems.darkMatter, 'D', Items.diamond});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.axeDarkMatter), new Object[] {"AA ", "AD ", " D ", 'A', ModItems.darkMatter, 'D', Items.diamond});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.swordDarkMatter), new Object[] {" A ", " A ", " D ", 'A', ModItems.darkMatter, 'D', Items.diamond});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.shovelDarkMatter), new Object[] {" A ", " D ", " D ", 'A', ModItems.darkMatter, 'D', Items.diamond});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.hoeDarkMatter), new Object[] {"AA ", " D ", " D ", 'A', ModItems.darkMatter, 'D', Items.diamond});
        
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.flintDarkMatter), Items.iron_ingot, ModItems.darkMatter);
       
        //alchemical fuel
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 2, 0), new Object[] {"GC ", "CC ", "C  ", 'G', Items.glowstone_dust, 'C', new ItemStack(Items.coal, 1, 0)});
        
        //add machines' crafting recipes
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.calcinator), new Object[] {"S S", "SCS", "SIS", 'S', Blocks.stone, 'C', Blocks.cobblestone, 'I', Items.iron_ingot});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.aludel), new Object[] {"S S", "ISI", "S S", 'S', Blocks.stone, 'I', Items.iron_ingot});


        //minium stone recipe
        AludelRecipeRegistry.instance().registerRecipe(new ItemStack(ModItems.stoneMinium), new String[]{"i", "d", "mm", "g"},
        		'i', new ItemStack(ModItems.stoneInert), 'd', new ItemStack(Items.diamond), 'm', new ItemStack(ModItems.alchemicalDust, 1, 3), 'g', new ItemStack(Items.glowstone_dust));
        
        //alchemical fuel recipes       
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 1, 0), new Object[] {" C ", "CMC", " C ",
        	'C', Items.coal, 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 1, 1), new Object[] {" C ", "CMC", " C ",
        	'C', new ItemStack(ModItems.alchemicalFuel, 1, 0), 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 1, 2), new Object[] {" C ", "CMC", " C ",
        	'C', new ItemStack(ModItems.alchemicalFuel, 1, 1), 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        //alchemical chest
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.alchemicalChest, 1, 0), new Object[] {"IDI", "VCV", "IMI", 'I', Items.iron_ingot, 'D', Items.diamond, 
        	'V', new ItemStack(ModItems.alchemicalDust, 1, 1), 'C', Blocks.chest, 'M', new ItemStack(ModItems.alchemicalDust, 1, 3)});
        
        //energy condenser
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.condenser, 1, 0), new Object[] {"OVO", "AaA", "OVO", 'O', new ItemStack(Blocks.obsidian),
        	'a', new ItemStack(ModBlocks.alchemicalChest, 1, 2), 'A', new ItemStack(ModItems.alchemicalDust, 1, 1), 'V', new ItemStack(ModItems.alchemicalDust, 1, 2), 'A', new ItemStack(ModItems.alchemicalDust, 1, 2)});
        
        //glass bell
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.glassBell, 1, 0), new Object[] {" G ", "G G", " S ", 'G', Blocks.glass,
        	'S', Blocks.stone});
        //alchemical upgrades
        //not exactly sure what the verdant upgrade does, since there are only three types of chest
        //GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalUpgrade, 1, 0), new Object[] {" V ", "VMV", " V ", 'V', 
        //	new ItemStack(ModItems.alchemicalDust, 1, 1), 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalUpgrade, 1, 1), new Object[] {" A ", "AMA", " A ", 'A', 
        	new ItemStack(ModItems.alchemicalDust, 1, 2), 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalUpgrade, 1, 2), new Object[] {" V ", "VMV", " V ", 'V', 
        	new ItemStack(ModItems.alchemicalDust, 1, 3), 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        //alchemical bag upgrades
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalBag, 1, 1), new Object[] { 
        	new ItemStack(ModItems.alchemicalBag, 1, 0), new ItemStack(ModItems.alchemicalUpgrade,1, 1) });
        
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalBag, 1, 2), new Object[] { 
        	new ItemStack(ModItems.alchemicalBag, 1, 1), new ItemStack(ModItems.alchemicalUpgrade,1, 2) });
        
        
        //alchemical chest upgrades
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.alchemicalChest, 1, 1), new ItemStack(ModItems.alchemicalUpgrade, 1, 1), new ItemStack(ModBlocks.alchemicalChest, 1, 0));

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.alchemicalChest, 1, 2), new ItemStack(ModItems.alchemicalUpgrade, 1, 2), new ItemStack(ModBlocks.alchemicalChest, 1, 1));
        
        //inert stone
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.stoneInert), new Object[] {"IGI", "GSG", "IGI", 'G', 
        	Items.gold_ingot, 'I', Items.iron_ingot, 'S', Blocks.stone});
        
        //alchemical bag
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalBag, 1, 0), new Object[] {"WGW", "ACA", "WGW", 'W', 
        	new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'G', Items.gold_ingot, 'C', new ItemStack(ModBlocks.alchemicalChest, 1, 0), 'A', new ItemStack(ModItems.alchemicalDust, 1, 2)});
        
        //Talisman of Repair
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.talismanRepair), new Object[] {"VAW", "SPM", "VAW", 'W', new ItemStack(Blocks.wool, 1, -1),
        	'V', new ItemStack(ModItems.alchemicalDust, 1, 1), 'A', new ItemStack(ModItems.alchemicalDust, 1, 2), 'M', new ItemStack(ModItems.alchemicalDust, 1, 3),
        	'P', Items.paper, 'S', Items.string});
        
        //mundane band
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.bandIron, 1, 0), new Object[] {" I ", "ILI", " I ", 'I', 
        	Items.iron_ingot, 'L', Items.lava_bucket});

        //imbued band
        AludelRecipeRegistry.instance().registerRecipe(new ItemStack(ModItems.bandIron, 1, 1), new String[]{"b", "g", "\n\n", "g"},
        		'g', new ItemStack(Items.glowstone_dust), 'b', new ItemStack(ModItems.bandIron, 1, 0));
        
        //ring of flight
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.ringFlight, 1, 0), new Object[] {" F ", "DBD", " F ", 'F', Items.feather, 
        	'D', ModItems.darkMatter, 'B', new ItemStack(ModItems.bandIron, 1, 1)});
        
        //transmutation tablet
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.transmutationTablet, 1, 0), new Object[] {"   ", "OMO", "DGD", 'O', new ItemStack(Blocks.obsidian),
        	'D', Items.diamond, 'M', Items.glowstone_dust, 'M', new ItemStack(ModItems.stoneMinium, 1, OreDictionary.WILDCARD_VALUE)});
        
        //alchemical fuel blocks
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 0), new Object[] {"FFF", "FFF", "FFF", 'F', new ItemStack(ModItems.alchemicalFuel, 1, 0)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 1), new Object[] {"FFF", "FFF", "FFF", 'F', new ItemStack(ModItems.alchemicalFuel, 1, 1)});

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 2), new Object[] {"FFF", "FFF", "FFF", 'F', new ItemStack(ModItems.alchemicalFuel, 1, 2)});
        
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalFuel, 9, 0), new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 0));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalFuel, 9, 1), new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 1));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalFuel, 9, 2), new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 2));
        
        //Klein Stars
        ItemStack kleinStarIchi = new ItemStack(ModItems.kleinStar, 1, 0);
        ItemStack kleinStarNi = new ItemStack(ModItems.kleinStar, 1, 1);
        ItemStack kleinStarSan = new ItemStack(ModItems.kleinStar, 1, 2);
        ItemStack kleinStarYon = new ItemStack(ModItems.kleinStar, 1, 3);
        ItemStack kleinStarGo = new ItemStack(ModItems.kleinStar, 1, 4);
        ItemStack kleinStarZen = new ItemStack(ModItems.kleinStar, 1, 5);
        
        GameRegistry.addShapedRecipe(kleinStarIchi, new Object[] {"MMM", "MDM", "MMM", 'D', Items.diamond,
        	'M', new ItemStack(ModItems.alchemicalFuel, 1, 0)});
        
        GameRegistry.addShapelessRecipe(kleinStarNi, kleinStarIchi, kleinStarIchi, kleinStarIchi, kleinStarIchi);
        
        GameRegistry.addShapelessRecipe(kleinStarSan, kleinStarNi, kleinStarNi, kleinStarNi, kleinStarNi);

        GameRegistry.addShapelessRecipe(kleinStarYon, kleinStarSan, kleinStarSan, kleinStarSan, kleinStarSan);

        GameRegistry.addShapelessRecipe(kleinStarGo, kleinStarYon, kleinStarYon, kleinStarYon, kleinStarYon);

        GameRegistry.addShapelessRecipe(kleinStarZen, kleinStarIchi, kleinStarNi, kleinStarSan, kleinStarYon, kleinStarGo);

    }

    @SubscribeEvent
    public void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event)
    {
    	//take durability damage from minium stone
    	//TEMPORARILY DISABLED DUE TO AE2 bug
//    	for(int counter = event.craftMatrix.getSizeInventory(); counter > 0; --counter)
//    	{
//    		ItemStack itemStack = event.craftMatrix.getStackInSlot(counter);
//    		
//    		if(itemStack != null && itemStack.getItem() == ModItems.stoneMinium)
//    		{
//    			String name = event.crafting.getUnlocalizedName();
//    			if(name.equals("tile.eer:transmutationTablet"))
//    			{
//	    			itemStack.damageItem(100, event.player);
//    			}
//    			else
//    			{
//    				itemStack.damageItem(1, event.player);
//    			}
//    			
//    			//if the stone has broken, return it as shards
//    			if(itemStack.stackSize == 0)
//    			{
//    				itemStack = new ItemStack(ModItems.shardMinium, new Random().nextInt(9), 0);
//    				if(itemStack.stackSize == 0)
//    				{
//    					itemStack = null;
//    				}
//    			}
//    			
//    			//and then return it
//    		}
//    		
//    	}
    }
    
}
