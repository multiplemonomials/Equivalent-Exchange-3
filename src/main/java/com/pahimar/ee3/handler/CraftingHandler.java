package com.pahimar.ee3.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import com.pahimar.ee3.init.ModBlocks;
import com.pahimar.ee3.init.ModItems;
import com.pahimar.ee3.item.crafting.RecipesAlchemicalBagDyes;
import com.pahimar.ee3.registry.AludelRecipeRegistry;

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
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 2, 0), new Object[] {"GC ", "CC ", "C  ", 'G', Items.glowstone_dust, 'C', new ItemStack(Items.coal, 1, 0)});
        
        //add machines' crafting recipes
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.calcinator), new Object[] {"S S", "SCS", "SIS", 'S', Blocks.stone, 'C', Blocks.cobblestone, 'I', Items.iron_ingot});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.aludel), new Object[] {"S S", "ISI", "S S", 'S', Blocks.stone, 'I', Items.iron_ingot});


        //minium stone recipe
        AludelRecipeRegistry.instance().registerRecipe(new ItemStack(ModItems.stoneMinium), new String[]{"i", "d", "mm", "g"},
        		'i', new ItemStack(ModItems.stoneInert), 'd', new ItemStack(Items.diamond), 'm', new ItemStack(ModItems.alchemicalDust, 1, 3), 'g', new ItemStack(Items.glowstone_dust));
        
        //alchemical fuel recipes       
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 1, 0), new Object[] {" C ", "CMC", " C ",
        	'C', Items.coal, 'M', new ItemStack(ModItems.stoneMinium)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 1, 1), new Object[] {" C ", "CMC", " C ",
        	'C', new ItemStack(ModItems.alchemicalFuel, 1, 0), 'M', new ItemStack(ModItems.stoneMinium)});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalFuel, 1, 2), new Object[] {" C ", "CMC", " C ",
        	'C', new ItemStack(ModItems.alchemicalFuel, 1, 1), 'M', new ItemStack(ModItems.stoneMinium)});
        
        //alchemical chest
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.alchemicalChest, 1, 0), new Object[] {"IDI", "VCV", "IMI", 'I', Items.iron_ingot, 'D', Items.diamond, 
        	'V', new ItemStack(ModItems.alchemicalDust, 1, 1), 'C', Blocks.chest, 'M', new ItemStack(ModItems.alchemicalDust, 1, 3)});
        
        //energy condenser
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.condenser, 1, 0), new Object[] {"OVO", "AaA", "OVO", 'O', new ItemStack(Blocks.obsidian),
        	'a', new ItemStack(ModBlocks.alchemicalChest, 1, 2), 'A', new ItemStack(ModItems.alchemicalDust, 1, 1), 'V', new ItemStack(ModItems.alchemicalDust, 1, 2), 'A', new ItemStack(ModItems.alchemicalDust, 1, 2)});
        //alchemical upgrades
        //not exactly sure what the minium upgrade does, since there are only three types of chest
        //GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalUpgrade, 1, 0), new Object[] {" V ", "VMV", " V ", 'V', 
        //	new ItemStack(ModItems.alchemicalDust, 1, 1), 'M', ModItems.stoneMinium});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalUpgrade, 1, 1), new Object[] {" A ", "AMA", " A ", 'A', 
        	new ItemStack(ModItems.alchemicalDust, 1, 2), 'M', ModItems.stoneMinium});
        
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalUpgrade, 1, 2), new Object[] {" V ", "VMV", " V ", 'V', 
        	new ItemStack(ModItems.alchemicalDust, 1, 3), 'M', ModItems.stoneMinium});
        
        //alchemical bag upgrades
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalBag, 1, 1), new Object[] { 
        	new ItemStack(ModItems.alchemicalBag, 1, 0), new ItemStack(ModItems.alchemicalUpgrade,1, 1) });
        
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.alchemicalBag, 1, 2), new Object[] { 
        	new ItemStack(ModItems.alchemicalBag, 1, 2), new ItemStack(ModItems.alchemicalUpgrade,1, 2) });
        
        
        //alchemical chest upgrades
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.alchemicalChest, 1, 1), new ItemStack(ModItems.alchemicalUpgrade, 1, 0), new ItemStack(ModBlocks.alchemicalChest, 1, 0));

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.alchemicalChest, 1, 2), new ItemStack(ModItems.alchemicalUpgrade, 1, 1), new ItemStack(ModBlocks.alchemicalChest, 1, 1));
        
        //inert stone
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.stoneInert), new Object[] {"IGI", "GSG", "IGI", 'G', 
        	Items.gold_ingot, 'I', Items.iron_ingot, 'S', Blocks.stone});
        
        //alchemical bag
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.alchemicalBag), new Object[] {"WGW", "ACA", "WGW", 'W', 
        	new ItemStack(Blocks.wool, 1, -1), 'G', Items.gold_ingot, 'C', new ItemStack(ModBlocks.alchemicalChest, 1, -1), 'A', new ItemStack(ModItems.alchemicalDust, 1, 2)});
        
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
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.bandIron, 1, 0), new Object[] {" F ", "DBD", " F ", 'F', Items.feather, 
        	'D', ModItems.darkMatter, 'B', new ItemStack(ModItems.bandIron, 1, 1)});
        
        //transmutation tablet
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.bandIron, 1, 0), new Object[] {"   ", "OMO", "DGD", 'O', new ItemStack(Blocks.obsidian),
        	'D', Items.diamond, 'M', Items.glowstone_dust, 'M', new ItemStack(ModItems.stoneMinium)});
    }

    @SubscribeEvent
    public void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event)
    {
        // TODO Set owner on who crafted the item (make sure it's not a FakePlayer)
    	
    	//take durability damage from minium stone
    	for(int counter = event.craftMatrix.getSizeInventory(); counter > 0; --counter)
    	{
    		ItemStack itemStack = event.craftMatrix.getStackInSlot(counter);
    		
    		if(itemStack.getItem() == ModItems.stoneMinium)
    		{
    			itemStack.damageItem(1, event.player);
    			
    			//and then return it
    			if(!event.player.inventory.addItemStackToInventory(itemStack))
    			{
    				event.player.entityDropItem(itemStack, 0);
    			}
    		}
    		
    	}
    }
}
