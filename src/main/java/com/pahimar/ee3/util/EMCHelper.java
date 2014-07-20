package com.pahimar.ee3.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.exchange.EnergyRegistry;
import com.pahimar.ee3.init.ModItems;

public class EMCHelper 
{
	public static Item[] itemsConsideredAlchemicalFuel = new Item[]
			{Items.coal, Items.redstone, Items.glowstone_dust, ModItems.alchemicalFuel};
	
	/**
	 * Consumes EMC from fuel items (Klein stars and alchemical fuels) in the player's inventory
	 * 
	 * It uses the second parameter as a guideline.  It may not find any alchemical fuel items and return 0.
	 * It may find a Klein star and return exactly the amount of EMC or less.  Or it may have to take an 
	 * item from the player's inventory and return more than the requested EMC.
	 * 
	 * @param player
	 * @param idealEMC
	 */
	public static double consumeEMCFromPlayerInventory(EntityPlayer player, double idealEMC)
	{
		double builtupEMC = 0;
		
		//TODO: add support for Klein stars
		
		for(int index = 0; index < player.inventory.mainInventory.length; ++index)
		{
			if(player.inventory.mainInventory[index] != null)
			{
				if(isConsideredFuel(player.inventory.mainInventory[index]))
				{
					builtupEMC += EnergyRegistry.getInstance().getEnergyValue(player.inventory.mainInventory[index]).getValue();
					player.inventory.mainInventory[index] = ItemHelper.takeOneFromItemStack(player.inventory.mainInventory[index]);
				}
			}
			
			if(builtupEMC >= idealEMC)
			{
				return builtupEMC;
			}
		}
		
		return builtupEMC;
	}
	
	//is there a standard-library method for an iterative array search?
	//I haven't found it yet.
	private static boolean isConsideredFuel(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		for(Item fuelItem : itemsConsideredAlchemicalFuel)
		{
			if(item == fuelItem)
			{
				return true;
			}
		}
		
		return false;
	}
}
