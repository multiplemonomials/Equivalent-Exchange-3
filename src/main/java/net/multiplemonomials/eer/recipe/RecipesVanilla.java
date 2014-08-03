package net.multiplemonomials.eer.recipe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.multiplemonomials.eer.exchange.WrappedStack;
import net.multiplemonomials.eer.util.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesVanilla
{
    private static Multimap<WrappedStack, List<WrappedStack>> vanillaRecipes = null;

    public static Multimap<WrappedStack, List<WrappedStack>> getVanillaRecipes()
    {
        if (vanillaRecipes == null)
        {
            init();
        }

        return vanillaRecipes;
    }

    private static void init()
    {
        vanillaRecipes = HashMultimap.create();

        for (Object recipeObject : CraftingManager.getInstance().getRecipeList())
        {
            /**
             * Vanilla
             */
            if (recipeObject instanceof ShapedRecipes || recipeObject instanceof ShapelessRecipes || recipeObject instanceof ShapedOreRecipe || recipeObject instanceof ShapelessOreRecipe)
            {
                IRecipe recipe = (IRecipe) recipeObject;
                ItemStack recipeOutput = recipe.getRecipeOutput();

                if (recipeOutput != null)
                {
                    ArrayList<WrappedStack> recipeInputs = RecipeHelper.getRecipeInputs(recipe);

                    if (!recipeInputs.isEmpty())
                    {
                        vanillaRecipes.put(new WrappedStack(recipeOutput), recipeInputs);
                    }
                }
            }
        }
    }
}
