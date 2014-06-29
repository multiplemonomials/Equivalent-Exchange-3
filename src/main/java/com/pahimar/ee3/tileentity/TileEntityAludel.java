package com.pahimar.ee3.tileentity;

import java.util.ArrayList;
import java.util.Arrays;

import com.pahimar.ee3.network.PacketHandler;
import com.pahimar.ee3.network.message.MessageTileEntityAludel;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.registry.AludelRecipeRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;

public class TileEntityAludel extends TileEntityEE implements ISidedInventory
{
    public static final int INVENTORY_SIZE = 6;
    public static final int MIDDLE_INPUT_INVENTORY_INDEX = 1;
    public static final int LEFT_INPUT_INVENTORY_INDEX = 2;
    public static final int RIGHT_INPUT_INVENTORY_INDEX = 3;
    public static final int BOTTOM_INPUT_INVENTORY_INDEX = 4;
    public static final int TOP_INPUT_INVENTORY_INDEX = 0;
    public static final int OUTPUT_INVENTORY_INDEX = 5;
    
    public boolean hasGlassBell = false;
    /**
     * The ItemStacks that hold the items currently being used in the Aludel
     * public so that the renderer can use it
     */
    public ItemStack[] inventory;
    
    //inventory as of the last tick, so that aludel can tell if anything has changed
    private ItemStack[] lastTickInventory;

    public TileEntityAludel()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
        
        lastTickInventory = new ItemStack[INVENTORY_SIZE];
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int var1, ItemStack var2, int var3)
    {
        return false;
    }

    @Override
    public boolean canExtractItem(int var1, ItemStack var2, int var3)
    {
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
    	if(var1 < inventory.length)
    	{
    		return inventory[var1];
    	}
    	
    	return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2)
    {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
    	return this.hasCustomName() ? this.getCustomName() : Names.Containers.ALUDEL_NAME;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return this.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1)
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
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityAludel(this, inventory[OUTPUT_INVENTORY_INDEX]));
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        
        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null && currentIndex != OUTPUT_INVENTORY_INDEX)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        
        nbtTagCompound.setTag("Items", tagList);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length && slotIndex != OUTPUT_INVENTORY_INDEX)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }
    
    /**
     * Calculates and displays the result of crafting the current inventory
     */
    void displayProduct()
    {
    	ArrayList<ItemStack> inventoryList = new ArrayList<ItemStack>(Arrays.asList(inventory));
		
		ItemStack product = AludelRecipeRegistry.instance().getProduct(inventoryList);
		
		inventory[OUTPUT_INVENTORY_INDEX] = product;
		
    }
    
    @Override
    public void updateEntity()
    {
    	if(Arrays.equals(inventory, lastTickInventory))
    	{
    		return;
    	}
    	else
    	{
    		displayProduct();
    		
    		// update last-tick inventory
    		System.arraycopy(inventory, 0, lastTickInventory, 0, inventory.length);
    	}
    }
    
    
}
