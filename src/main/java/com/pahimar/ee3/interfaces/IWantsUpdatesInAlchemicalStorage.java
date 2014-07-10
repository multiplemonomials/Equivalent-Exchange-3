package com.pahimar.ee3.interfaces;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Interface for items that wish to be updated while in an alchemy chest, e.g. the Talisman of Repair
 * @author Jamie
 *
 */
public interface IWantsUpdatesInAlchemicalStorage
{
    public void onUpdateInAlchemyChest(ItemStack itemStack, World world, IInventory chest, int indexInInventory);
}
