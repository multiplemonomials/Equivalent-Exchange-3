package net.multiplemonomials.eer.registry;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;

import net.multiplemonomials.eer.util.ItemHelper;

/**
 * Singleton that holds Aludel recipies
 */
public class AludelRecipeRegistry
{
	
	private ArrayList<Pair<ArrayList<ItemStack>, ItemStack>> _recipeList; 
	
	/**
	 * Varargs method that registers a recipe.
	 * Similar to the Minecraft crafting recipe registry
	 * 
	 * NOTE: does not copy its arguments, please don't pass this method ItemStacks that are going to be used by something else
	 * 
	 * @param product the ItemStack to produce
	 * @param pattern the pattern that produces the recipe. Must have two rows of one, that a row of two, than another row of one.
	 * Chars can be '\n' if there is no item.
	 * example: new String[]{"a", "b", "ab", "\n"}
	 * @param objects alternating pattern of chars and the ItemStack associated with them
	 */
	public void registerRecipe(ItemStack product, String[] patterns, Object... objects)
	{		
		if(patterns.length != 4)
		{
			System.out.println("[AludelRecipeRegistry] Not accepting item recipe because the pattern array's length was not 4");
			return;
		}
		
		if(objects.length % 2 != 0)
		{
			System.out.println("[AludelRecipeRegistry] Not accepting item recipe because the object array's length was not divisible by 2");
			return;
		}
		
		HashMap<Character, ItemStack> charStackMap = new HashMap<Character, ItemStack>();
		
		
		for(int counter = 0; counter < objects.length; counter += 2)
		{
			charStackMap.put((Character)objects[counter], (ItemStack)objects[counter + 1]);
		}
		
		ArrayList<ItemStack> ingredientList = new ArrayList<ItemStack>(6);
		
		for(int stringCounter = 0; stringCounter < patterns.length; ++stringCounter)
		{
			for(int withinStringCounter = 0; withinStringCounter < patterns[stringCounter].length(); ++withinStringCounter)
			{
				char currentChar = patterns[stringCounter].charAt(withinStringCounter);
				if(currentChar != '\n')
				{
					ingredientList.add(charStackMap.get(currentChar));
				}
				else
				{
					ingredientList.add(null);
				}
				
			}
		}
		
		_recipeList.add(Pair.<ArrayList<ItemStack>, ItemStack>of(ingredientList, product));
	}
	
	/**
	 * Takes an ArrayList of ItemStacks representing the recipe.  In order:
	 * top slot, middle slot, left slot, right slot, bottom slot
	 * 
	 * Returns what they produce as an Aludel recipe, or null
	 * if the item is not registered
	 * 
	 * NOTE: return value IS copied.
	 * @param stacks
	 */
	public ItemStack getProduct(ArrayList<ItemStack> stacks)
	{
		
		for(Pair<ArrayList<ItemStack>, ItemStack> recipe : _recipeList)
		{
			for(int counter = 0; counter < 5; ++counter)
			{
				if(!ItemHelper.similar(recipe.getLeft().get(counter), (stacks.get(counter))))
				{
					break;
				}
				
				//if looped through all ingredients and they match
				if(counter == 4)
				{
					return recipe.getRight().copy();
				}
			}
		}
		return null;
	}
	
	private AludelRecipeRegistry()
	{
		_recipeList = new ArrayList<Pair<ArrayList<ItemStack>, ItemStack>>();
	}
	
	private static AludelRecipeRegistry _instance = new AludelRecipeRegistry();
	
	public static AludelRecipeRegistry instance()
	{
		return _instance;
	}
	
	
}
