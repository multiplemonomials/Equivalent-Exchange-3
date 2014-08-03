package net.multiplemonomials.eer.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import net.multiplemonomials.eer.data.EERExtendedPlayer;
import net.multiplemonomials.eer.interfaces.IShowcaseSlotCallback;
import net.multiplemonomials.eer.inventory.slot.EMCValuesOnlySlot;
import net.multiplemonomials.eer.inventory.slot.ShowcaseSlot;
import net.multiplemonomials.eer.tileentity.TileEntityTransmutationTablet;
import net.multiplemonomials.eer.util.ItemHelper;
import net.multiplemonomials.eer.util.LogHelper;

public class ContainerTransmutationTablet extends Container
{
	
	private class TransmutationSlotHandler implements IShowcaseSlotCallback
	{
		
		private TileEntityTransmutationTablet _tileEntityTransmutationTablet;
		
		EERExtendedPlayer _playerData;
		
		public TransmutationSlotHandler(TileEntityTransmutationTablet tileEntityTransmutationTablet, EERExtendedPlayer playerData)
		{
			_tileEntityTransmutationTablet = tileEntityTransmutationTablet;
			_playerData = playerData;
		}

		@Override
		public ItemStack onSlotClick(ShowcaseSlot slot, ItemStack currentStackInSlot, boolean isShiftClicking)
		{
			if(currentStackInSlot != null)
			{
				int stackSizeToReturn =  _tileEntityTransmutationTablet.tryTransmute(currentStackInSlot, isShiftClicking ?
						currentStackInSlot.getItem().getItemStackLimit(currentStackInSlot) : 1, _playerData);
				
				if(stackSizeToReturn != 0)
				{
					currentStackInSlot.stackSize = stackSizeToReturn;
					return currentStackInSlot;
				}
			}
			
			return null;
		}
		
	}
	
	private class TransmutationTableInputSlot extends EMCValuesOnlySlot
	{
		private TileEntityTransmutationTablet _tileEntityTransmutationTablet;
		
		private EERExtendedPlayer _playerData;

		public TransmutationTableInputSlot(EERExtendedPlayer playerData, TileEntityTransmutationTablet tileEntityTransmutationTablet, int par2, int par3, int par4)
		{
			super(tileEntityTransmutationTablet, par2, par3, par4);
			
			_tileEntityTransmutationTablet = tileEntityTransmutationTablet;
			
			_playerData = playerData;

		}
		
		public void onSlotChanged()
		{
			LogHelper.debug("Recalculating transmutable objects");
			
			_playerData.learnNewItem(getStack());
			
			_tileEntityTransmutationTablet.showPage(0, _playerData);
		}
		
	}
    
    // Player Inventory
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    private TileEntityTransmutationTablet tileEntityCondenser;
    
    private TransmutationSlotHandler slotHandler;
    
    public ContainerTransmutationTablet(InventoryPlayer inventoryPlayer, TileEntityTransmutationTablet tileEntityTransmutationTablet)
    {
        this.tileEntityCondenser = tileEntityTransmutationTablet;
        tileEntityTransmutationTablet.openInventory();

        EERExtendedPlayer playerData = EERExtendedPlayer.get(inventoryPlayer.player);
        
        slotHandler = new TransmutationSlotHandler(tileEntityTransmutationTablet, playerData);
        
        this.addSlotToContainer(new TransmutationTableInputSlot(playerData, tileEntityTransmutationTablet, TileEntityTransmutationTablet.INPUT_SLOT_INDEX, 93, 62));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.EAST_SLOT_INDEX, 132, 62, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.SOUTHEAST_SLOT_INDEX, 117, 86, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.SOUTH_SLOT_INDEX, 93, 101, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.SOUTHWEST_SLOT_INDEX, 69, 86, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.WEST_SLOT_INDEX, 54, 62, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.NORTHWEST_SLOT_INDEX, 69, 38, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.NORTH_SLOT_INDEX, 93, 23, slotHandler));
        this.addSlotToContainer(new ShowcaseSlot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.NORTHEAST_SLOT_INDEX, 117, 38, slotHandler));
        this.addSlotToContainer(new Slot(tileEntityTransmutationTablet, TileEntityTransmutationTablet.ENERGY_SLOT_INDEX, 147, 60));

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
