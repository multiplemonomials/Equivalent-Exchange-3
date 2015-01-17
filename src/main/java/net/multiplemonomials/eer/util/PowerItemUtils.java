package net.multiplemonomials.eer.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.item.tool.Matter;
import net.multiplemonomials.eer.reference.Sounds;

/**
 * Common functions for the Dark Matter tools
 *
 */
public class PowerItemUtils 
{
	
	/**
	 * Increases the charge on an eer power item and plays a sound
	 * @param itemStack
	 */
	public static void bumpChargeOnItem(ItemStack itemStack, EntityPlayer player) 
	{
		if(itemStack.getItemDamage() > 0)
		{
			itemStack.setItemDamage(itemStack.getItemDamage() - 1);
		}	
		
		player.worldObj.playSoundAtEntity(player, Sounds.CHARGE_UP, 1, 1);
	}
	
	/**
	 * Decreases the charge on an eer power item and plays a sound
	 * @param itemStack
	 */
	public static void lowerChargeOnItem(ItemStack itemStack, EntityPlayer player) 
	{
		if(itemStack.getItemDamage() < CommonConfiguration.MAX_ITEM_CHARGES)
		{
			//reset charge to default
			itemStack.setItemDamage(itemStack.getItemDamage() + 1);
		}		
		
		player.worldObj.playSoundAtEntity(player, Sounds.CHARGE_DOWN, 1, 1);

	}
	
	/**
	 * material for Dark Matter
	 * //asianJose: And for Red Matter
	 */
	public static Item.ToolMaterial MATERIALDARKMATTER;
	public static Item.ToolMaterial MATERIALREDMATTER;
	
	
	/**
	 * Create tool materials.  This requires the configuration to be loaded.
	 */
	public static void initMaterials()
	{
		MATERIALDARKMATTER = EnumHelper.addToolMaterial("DARKMATTER", 3, CommonConfiguration.MAX_ITEM_CHARGES, 4.0F, 4.0F, 0);
		
		//TODO: Config option for red-matter max-charges
		MATERIALREDMATTER = EnumHelper.addToolMaterial("REDMATTER", 3, CommonConfiguration.MAX_ITEM_CHARGES, 8.0F, 8.0F, 0);
	}
	
	/**
	 * Computes the efficiency bonus of a DM tool from its durability
	 * @param durability
	 * @return
	 */
	public static int computeEfficiencyBonus(int durability)
	{
		return computeEfficiencyBonus(durability, Matter.DarkMatter);
	}
	
	/**
	 * Computes the efficiency bonus of a DM tool from its durability.
	 * 
	 * Matter sensitive version
	 * @param durability
	 * @return
	 */
	public static int computeEfficiencyBonus(int durability, Matter matter)
	{
		return ((int)matter._toolMaterial.getEfficiencyOnProperMaterial()) * (CommonConfiguration.MAX_ITEM_CHARGES - durability);
	}
	
}
