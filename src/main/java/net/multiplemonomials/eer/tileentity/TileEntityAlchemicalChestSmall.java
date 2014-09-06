package net.multiplemonomials.eer.tileentity;

import net.multiplemonomials.eer.inventory.ContainerAlchemicalChest;

public class TileEntityAlchemicalChestSmall extends TileEntityAlchemicalChest
{
    public TileEntityAlchemicalChestSmall()
    {
    	super(ContainerAlchemicalChest.SMALL_INVENTORY_SIZE, 0);
    }
    
    @Override
    public boolean upgradeToNextLevel()
    {
    	//thanks to cpw's IronChests for how to do this
        TileEntityAlchemicalChest newEntity = new TileEntityAlchemicalChestMedium();
        
        newEntity.orientation = orientation;
        
        System.arraycopy(inventory, 0, newEntity.inventory, 0, inventory.length);
        
        worldObj.setTileEntity(xCoord, yCoord, zCoord, newEntity);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.blockMetadata + 1, 2);
        
        ++state;
        
        markDirty();
        
        return true;
     
    }
}
