package net.multiplemonomials.eer.inventory;

import net.multiplemonomials.eer.inventory.slot.OutputSlot;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;
import net.multiplemonomials.eer.util.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCalcinator extends Container
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    
    TileEntityCalcinator tileEntityCalcinator;

    public ContainerCalcinator(InventoryPlayer inventoryPlayer, TileEntityCalcinator tileCalcinator)
    {
    	tileEntityCalcinator = tileCalcinator;
    	
        this.addSlotToContainer(new Slot(tileCalcinator, TileEntityCalcinator.INPUT_INVENTORY_INDEX, 56, 17));
        
        this.addSlotToContainer(new Slot(tileCalcinator, TileEntityCalcinator.FUEL_INVENTORY_INDEX, 56, 62));
     
        this.addSlotToContainer(new OutputSlot(tileCalcinator, TileEntityCalcinator.OUTPUT_LEFT_INVENTORY_INDEX, 116, 35, new int[]{}));
        
        this.addSlotToContainer(new OutputSlot(tileCalcinator, TileEntityCalcinator.OUTPUT_RIGHT_INVENTORY_INDEX, 136, 35, new int[]{}));

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
