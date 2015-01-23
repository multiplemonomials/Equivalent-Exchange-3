package net.multiplemonomials.eer.recipe;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.multiplemonomials.eer.exchange.WrappedStack;
import net.multiplemonomials.eer.util.LogHelper;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class RecipeRegistry
{

    private static RecipeRegistry recipeRegistry = null;

    private Multimap<WrappedStack, List<WrappedStack>> recipeMap;

    private RecipeRegistry()
    {
        recipeMap = HashMultimap.create();

        init();
    }

    public static RecipeRegistry getInstance()
    {
        if (recipeRegistry == null)
        {
            recipeRegistry = new RecipeRegistry();
        }

        return recipeRegistry;
    }

    private void init()
    {
        // Add recipes in the vanilla crafting manager
        for (WrappedStack outputStack : RecipesVanilla.getVanillaRecipes().keySet())
        {
            for (List<WrappedStack> inputStacks : RecipesVanilla.getVanillaRecipes().get(outputStack))
            {
            	boolean foundWildcard = false;
            	//search for and wildcard items
            	for(WrappedStack inputStack : inputStacks)
            	{
	            	if(inputStack.getWrappedStack() instanceof ItemStack && ((ItemStack)inputStack.getWrappedStack()).getItemDamage() == OreDictionary.WILDCARD_VALUE)
	            	{
	            		LogHelper.debug("Didn't add wildcard recipe with product " + ((ItemStack)outputStack.getWrappedStack()).getDisplayName());
	            		
	            		//TODO: I'm not really sure how best to handle this.
	            		//for now, I can just get rid of it.
	            		foundWildcard = true;
	            		break;
	            	}
            	}

            	
                if (!foundWildcard && !recipeMap.get(outputStack).contains(inputStacks))
                {
                    recipeMap.put(outputStack, inputStacks);
                }
            }
        }

        // Add fluid container recipes
        for (WrappedStack outputStack : RecipesFluidContainers.getFluidContainerRecipes().keySet())
        {
            for (List<WrappedStack> inputStacks : RecipesFluidContainers.getFluidContainerRecipes().get(outputStack))
            {
                if (!recipeMap.get(outputStack).contains(inputStacks))
                {
                    recipeMap.put(outputStack, inputStacks);
                }
            }
        }

        // Add potion recipes
        for (WrappedStack outputStack : RecipesPotions.getPotionRecipes().keySet())
        {
            for (List<WrappedStack> inputStacks : RecipesPotions.getPotionRecipes().get(outputStack))
            {
                if (!recipeMap.get(outputStack).contains(inputStacks))
                {
                    recipeMap.put(outputStack, inputStacks);
                }
            }
        }

        // Add Aludel recipes
//        for (RecipeAludel recipeAludel : RecipesAludel.getInstance().getRecipes())
//        {
//            WrappedStack recipeOutput = new WrappedStack(recipeAludel.getRecipeOutput());
//            List<WrappedStack> recipeInputs = recipeAludel.getRecipeInputsAsWrappedStacks();
//
//            if (!recipeMap.get(recipeOutput).contains(recipeInputs))
//            {
//                recipeMap.put(recipeOutput, recipeInputs);
//            }
//        }
    }

    public Multimap<WrappedStack, List<WrappedStack>> getRecipeMappings()
    {
        return recipeRegistry.recipeMap;
    }

    public void dumpRegistry()
    {
        // Sort the keys for output to console
        SortedSet<WrappedStack> set = new TreeSet<WrappedStack>();
        set.addAll(recipeMap.keySet());

        for (WrappedStack key : set)
        {
            Collection<List<WrappedStack>> recipeMappings = recipeMap.get(key);

            for (List<WrappedStack> recipeList : recipeMappings)
            {
                LogHelper.info(String.format("Recipe Output: %s, Recipe Input: %s", key.toString(), recipeList.toString()));
            }
        }
    }
}