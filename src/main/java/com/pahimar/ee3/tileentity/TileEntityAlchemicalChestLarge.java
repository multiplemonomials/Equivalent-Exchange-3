package com.pahimar.ee3.tileentity;

import net.minecraft.item.ItemStack;

import com.pahimar.ee3.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestLarge extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestLarge()
    {
        super(2);
        
        inventory = new ItemStack[ContainerAlchemicalChest.LARGE_INVENTORY_SIZE];

    }
}
