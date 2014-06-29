package com.pahimar.ee3.handler;

import com.pahimar.ee3.init.ModItems;
import com.pahimar.ee3.item.crafting.RecipesAlchemicalBagDyes;
import com.pahimar.ee3.registry.AludelRecipeRegistry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class CraftingHandler
{
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
        
        AludelRecipeRegistry.instance().registerRecipe(new ItemStack(ModItems.stoneMinium), new String[]{"i", "d", "mm", "g"},
        		'i', new ItemStack(ModItems.stoneInert), 'd', new ItemStack(Items.diamond), 'm', new ItemStack(ModItems.alchemicalDust, 1, 3), 'g', new ItemStack(Items.glowstone_dust));
    }

    @SubscribeEvent
    public void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event)
    {
        // TODO Set owner on who crafted the item (make sure it's not a FakePlayer)
    }
}
