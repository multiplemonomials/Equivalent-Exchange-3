package com.pahimar.ee3.tileentity;

import net.minecraft.item.ItemStack;

import com.pahimar.ee3.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestMedium extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestMedium()
    {
        super(1);
        
        inventory = new ItemStack[ContainerAlchemicalChest.MEDIUM_INVENTORY_SIZE];
    }
}
