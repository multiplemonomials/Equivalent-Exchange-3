package net.multiplemonomials.eer.inventory;

import net.multiplemonomials.eer.tileentity.TileEntityCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCondenser extends Container
{
    // Condenser
    public static final int CONDENSER_INVENTORY_ROWS = 8;
    public static final int CONDENSER_INVENTORY_COLUMNS = 13;
    public static final int CONDENSER_INVENTORY_SIZE = CONDENSER_INVENTORY_ROWS * CONDENSER_INVENTORY_COLUMNS + 1;
    
    // Player Inventory
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    private TileEntityCondenser tileEntityCondenser;
    
    public ContainerCondenser(InventoryPlayer inventoryPlayer, TileEntityCondenser tileEntityCondenser)
    {
        this.tileEntityCondenser = tileEntityCondenser;
        tileEntityCondenser.openInventory();


        // Add the Condenser output slots to the container
        for (int chestRowIndex = 0; chestRowIndex < CONDENSER_INVENTORY_ROWS; ++chestRowIndex)
        {
            for (int chestColumnIndex = 0; chestColumnIndex < CONDENSER_INVENTORY_COLUMNS; ++chestColumnIndex)
            {
                   this.addSlotToContainer(new Slot(tileEntityCondenser, chestColumnIndex + chestRowIndex * CONDENSER_INVENTORY_COLUMNS, 8 + chestColumnIndex * 18, 26 + chestRowIndex * 18));
            }
        }
        
        this.addSlotToContainer(new Slot(tileEntityCondenser, TileEntityCondenser.INPUT_SLOT_INVENTORY_INDEX, 44, 6));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
            	this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 44 + inventoryColumnIndex * 18, 174 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
        	this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 44 + actionBarSlotIndex * 18, 232));
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
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < CONDENSER_INVENTORY_SIZE)
            {
                if (!this.mergeItemStack(itemStack, CONDENSER_INVENTORY_ROWS * CONDENSER_INVENTORY_COLUMNS, inventorySlots.size(), false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemStack, 0, CONDENSER_INVENTORY_ROWS * CONDENSER_INVENTORY_COLUMNS, false))
            {
                return null;
            }

            if (itemStack.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }
}
