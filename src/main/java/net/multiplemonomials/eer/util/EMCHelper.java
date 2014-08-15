package net.multiplemonomials.eer.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.item.ItemKleinStar;

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
		
		for(int index = 0; index < player.inventory.mainInventory.length; ++index)
		{
			if(player.inventory.mainInventory[index] != null)
			{
				if(player.inventory.mainInventory[index].getItem() == ModItems.kleinStar)
				{
					builtupEMC += ItemKleinStar.takeEMC(player.inventory.mainInventory[index], idealEMC);
				}
				else if(isConsideredFuel(player.inventory.mainInventory[index]))
				{
					EnergyValue energyValue = EnergyRegistry.getInstance().getEnergyValue(player.inventory.mainInventory[index]);
					if(energyValue != null)
					{
						builtupEMC += energyValue.getValue();
						player.inventory.mainInventory[index] = ItemHelper.takeOneFromItemStack(player.inventory.mainInventory[index]);
					}
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
	public static boolean isConsideredFuel(ItemStack itemStack)
	{
		if(itemStack == null)
		{
			return false;
		}
		
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
