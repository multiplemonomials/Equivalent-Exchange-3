package net.multiplemonomials.eer.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.multiplemonomials.eer.api.stack.WrappedStack;
import net.multiplemonomials.eer.util.LogHelper;
import net.multiplemonomials.eer.util.RecipeHelper;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

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

                    boolean foundWildcard = false;
                	//search for and wildcard items
                	for(WrappedStack inputStack : recipeInputs)
                	{
    	            	if(inputStack.getWrappedStack() instanceof ItemStack && ((ItemStack)inputStack.getWrappedStack()).getItemDamage() == OreDictionary.WILDCARD_VALUE)
    	            	{
    	            		LogHelper.debug("Didn't add wildcard recipe with product " + recipeOutput.getDisplayName());
    	            		
    	            		//TODO: I'm not really sure how best to handle this.
    	            		//for now, I can just get rid of it.
    	            		foundWildcard = true;
    	            		break;
    	            	}
                	}
                	
                	if(recipeOutput.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                	{
                		LogHelper.debug("Didn't add wildcard recipe with product " + recipeOutput.getDisplayName());
                		
                		foundWildcard = true;
                		break;
                	}

                    if (!foundWildcard && !recipeInputs.isEmpty())
                    {
                        vanillaRecipes.put(new WrappedStack(recipeOutput), recipeInputs);
                    }
                }
            }
        }
    }
}
