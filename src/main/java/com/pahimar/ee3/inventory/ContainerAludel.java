package com.pahimar.ee3.inventory;

import com.pahimar.ee3.tileentity.TileEntityAludel;
import com.pahimar.ee3.util.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAludel extends Container
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    
    TileEntityAludel tileEntityAludel;

    public ContainerAludel(InventoryPlayer inventoryPlayer, TileEntityAludel tileAludel)
    {
        this.addSlotToContainer(new Slot(tileAludel, TileEntityAludel.BOTTOM_INPUT_INVENTORY_INDEX, 33, 81));
        
        this.addSlotToContainer(new Slot(tileAludel, TileEntityAludel.RIGHT_INPUT_INVENTORY_INDEX, 51, 63));
        
        this.addSlotToContainer(new Slot(tileAludel, TileEntityAludel.LEFT_INPUT_INVENTORY_INDEX, 15, 63));
        
        this.addSlotToContainer(new Slot(tileAludel, TileEntityAludel.MIDDLE_INPUT_INVENTORY_INDEX, 33, 45));
        
        this.addSlotToContainer(new Slot(tileAludel, TileEntityAludel.TOP_INPUT_INVENTORY_INDEX, 33, 14));
        
        //OutputSlot does the grunt work of decrementing everything in the table on craft
        this.addSlotToContainer(new OutputSlot(tileAludel, TileEntityAludel.OUTPUT_INVENTORY_INDEX, 120, 39, new int[]
        		{TileEntityAludel.BOTTOM_INPUT_INVENTORY_INDEX,
        		TileEntityAludel.RIGHT_INPUT_INVENTORY_INDEX,
        		TileEntityAludel.LEFT_INPUT_INVENTORY_INDEX,
        		TileEntityAludel.MIDDLE_INPUT_INVENTORY_INDEX,
        		TileEntityAludel.TOP_INPUT_INVENTORY_INDEX
        		}));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 106 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 164));
        }
        
        tileEntityAludel = tileAludel;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        return ItemHelper.transferStackInSlot(entityPlayer, tileEntityAludel, (Slot)inventorySlots.get(slotIndex), TileEntityAludel.INVENTORY_SIZE);
    }
}
