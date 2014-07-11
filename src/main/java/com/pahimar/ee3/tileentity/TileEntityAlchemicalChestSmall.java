package com.pahimar.ee3.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.init.ModBlocks;
import com.pahimar.ee3.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestSmall extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestSmall()
    {
    	super(0);
    	inventory = new ItemStack[ContainerAlchemicalChest.SMALL_INVENTORY_SIZE];
    }
    
    @Override
    public boolean upgradeToNextLevel()
    {
    	//thanks to cpw's IronChests for how to do this
        TileEntityAlchemicalChest newEntity = new TileEntityAlchemicalChestMedium();
        
        System.arraycopy(inventory, 0, newEntity.inventory, 0, inventory.length);
        
        worldObj.setTileEntity(xCoord, yCoord, zCoord, newEntity);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, Item.getIdFromItem(Item.getItemFromBlock(ModBlocks.alchemicalChest)), this.blockMetadata + 1);
        
        ++state;
        
        markDirty();
        
        return true;
     
    }
}
