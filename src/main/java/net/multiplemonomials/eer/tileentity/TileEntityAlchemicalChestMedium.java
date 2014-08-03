package net.multiplemonomials.eer.tileentity;

import net.minecraft.item.ItemStack;

import net.multiplemonomials.eer.inventory.ContainerAlchemicalChest;
import net.multiplemonomials.eer.reference.Flags;

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
        
        newEntity.orientation = orientation;
        
        System.arraycopy(inventory, 0, newEntity.inventory, 0, inventory.length);
        
        worldObj.setTileEntity(xCoord, yCoord, zCoord, newEntity);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.blockMetadata + 1, Flags.ALCHEMICAL_CHEST_UPGRADED_FLAG);
     
        ++state;
        
        markDirty();
        
        return true;
    }
}
