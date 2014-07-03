package com.pahimar.ee3.tileentity;

import net.minecraft.item.ItemStack;

import com.pahimar.ee3.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestSmall extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestSmall()
    {
    	super(0);
    	inventory = new ItemStack[ContainerAlchemicalChest.SMALL_INVENTORY_SIZE];
    }
}
