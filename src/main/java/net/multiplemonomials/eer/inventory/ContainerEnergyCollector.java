package net.multiplemonomials.eer.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.multiplemonomials.eer.inventory.slot.EMCStorageOnlySlot;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;
import net.multiplemonomials.eer.tileentity.TileEntityEnergyCollector;
import net.multiplemonomials.eer.util.ItemHelper;

public class ContainerEnergyCollector extends Container
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    
    TileEntityEnergyCollector tileEntityCalcinator;

    public ContainerEnergyCollector(InventoryPlayer inventoryPlayer, TileEntityEnergyCollector tileCollector)
    {
    	tileEntityCalcinator = tileCollector;
    	
        this.addSlotToContainer(new EMCStorageOnlySlot(tileCollector, TileEntityEnergyCollector.ENERGY_SLOT_INVENTORY_INDEX, 152, 55));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 94 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 152));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        return ItemHelper.transferStackInSlot(entityPlayer, tileEntityCalcinator, (Slot)inventorySlots.get(slotIndex), slotIndex, TileEntityCalcinator.INVENTORY_SIZE);

    }
}
