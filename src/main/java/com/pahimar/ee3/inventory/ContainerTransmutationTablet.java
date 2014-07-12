package com.pahimar.ee3.inventory;

import com.pahimar.ee3.tileentity.TileEntityTransmutationTablet;
import com.pahimar.ee3.util.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTransmutationTablet extends Container
{
    
    // Player Inventory
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    private TileEntityTransmutationTablet tileEntityCondenser;
    
    public ContainerTransmutationTablet(InventoryPlayer inventoryPlayer, TileEntityTransmutationTablet tileEntityTransmutationTablet)
    {
        this.tileEntityCondenser = tileEntityTransmutationTablet;
        tileEntityTransmutationTablet.openInventory();

        
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.INPUT_SLOT_INDEX, 93, 62));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.EAST_SLOT_INDEX, 132, 62));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.SOUTHEAST_SLOT_INDEX, 117, 86));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.SOUTH_SLOT_INDEX, 93, 101));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.SOUTHWEST_SLOT_INDEX, 69, 86));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.WEST_SLOT_INDEX, 54, 62));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.NORTHWEST_SLOT_INDEX, 69, 38));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.NORTH_SLOT_INDEX, 93, 23));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.NORTHEAST_SLOT_INDEX, 117, 38));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.ENERGY_SLOT_INDEX, 147, 114));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
            	this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 21 + inventoryColumnIndex * 18, 141 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
        	this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 21 + actionBarSlotIndex * 18, 199));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);
        tileEntityCondenser.closeInventory();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        return ItemHelper.transferStackInSlot(entityPlayer, tileEntityCondenser, (Slot) inventorySlots.get(slotIndex), slotIndex, tileEntityCondenser.getSizeInventory());
    }
}
