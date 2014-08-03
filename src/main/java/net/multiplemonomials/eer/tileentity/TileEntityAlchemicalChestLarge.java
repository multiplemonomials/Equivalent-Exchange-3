package net.multiplemonomials.eer.tileentity;

import net.minecraft.item.ItemStack;

import net.multiplemonomials.eer.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestLarge extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestLarge()
    {
        super(2);
        
        inventory = new ItemStack[ContainerAlchemicalChest.LARGE_INVENTORY_SIZE];

    }
}
