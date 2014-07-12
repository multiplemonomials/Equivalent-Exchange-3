package com.pahimar.ee3.tileentity;

import net.minecraft.item.ItemStack;

import com.pahimar.ee3.inventory.ContainerAlchemicalChest;
import com.pahimar.ee3.reference.Flags;

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
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.blockMetadata + 1, Flags.ALCHEMICAL_CHEST_UPGRADED_FLAG);
        
        ++state;
        
        markDirty();
        
        return true;
     
    }
}
