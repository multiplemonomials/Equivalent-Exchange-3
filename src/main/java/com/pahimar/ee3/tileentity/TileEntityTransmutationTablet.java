package com.pahimar.ee3.tileentity;

import com.pahimar.ee3.reference.Names;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TileEntityTransmutationTablet extends TileEntityEE implements IInventory
{

    protected ItemStack[] inventory;
    
    public static final int INVENTORY_SIZE = 10;
    
    public static final int INPUT_SLOT_INDEX = 0;
    
    public static final int NORTH_SLOT_INDEX = 1;
    public static final int NORTHEAST_SLOT_INDEX = 2;
    public static final int EAST_SLOT_INDEX = 3;
    public static final int SOUTHEAST_SLOT_INDEX = 4;
    public static final int SOUTH_SLOT_INDEX = 5;
    public static final int SOUTHWEST_SLOT_INDEX = 6;
    public static final int WEST_SLOT_INDEX = 7;
    public static final int NORTHWEST_SLOT_INDEX = 8;

    public static final int ENERGY_SLOT_INDEX = 9;

    public int leftoverEMC;

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }
    
    public TileEntityTransmutationTablet()
    {
    	super();
    	
    	inventory = new ItemStack[INVENTORY_SIZE];
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (inventory[slotIndex] != null)
        {
            ItemStack itemStack = inventory[slotIndex];
            inventory[slotIndex] = null;
            return itemStack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }


        this.markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.TRANSMUTATION_TABLET;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }


	@Override
	public void openInventory() 
	{
		
	}

	@Override
	public void closeInventory() 
	{

	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) 
	{
		return true;
	}
	
}
