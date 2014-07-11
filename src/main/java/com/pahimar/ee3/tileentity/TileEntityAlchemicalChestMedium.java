package com.pahimar.ee3.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.init.ModBlocks;
import com.pahimar.ee3.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestMedium extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestMedium()
    {
        super(1);
        
        inventory = new ItemStack[ContainerAlchemicalChest.MEDIUM_INVENTORY_SIZE];
    }
    
    public boolean upgradeToNextLevel()
    {
    	//thanks to cpw's IronChests for how to do this
        TileEntityAlchemicalChest newEntity = new TileEntityAlchemicalChestLarge();
        
        System.arraycopy(inventory, 0, newEntity.inventory, 0, inventory.length);
        
        worldObj.setTileEntity(xCoord, yCoord, zCoord, newEntity);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, Item.getIdFromItem(Item.getItemFromBlock(ModBlocks.alchemicalChest)), this.blockMetadata + 1);
     
        ++state;
        
        markDirty();
        
        return true;
    }
}
