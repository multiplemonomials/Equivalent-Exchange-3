package net.multiplemonomials.eer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileEntityGlassBell;
import net.multiplemonomials.eer.reference.Names;

public class TileEntityGlassBell extends TileEntityEE
{
    public static final int INVENTORY_SIZE = 1;
    public static final int DISPLAY_SLOT_INVENTORY_INDEX = 0;
    public ItemStack outputItemStack;


    public TileEntityGlassBell()
    {
        super(INVENTORY_SIZE);
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
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
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
    public Packet getDescriptionPacket()
    {
        if (getStackInSlot(DISPLAY_SLOT_INVENTORY_INDEX) != null && getStackInSlot(DISPLAY_SLOT_INVENTORY_INDEX).stackSize > 0)
        {
            return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityGlassBell(this, getStackInSlot(DISPLAY_SLOT_INVENTORY_INDEX)));
        }

        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityGlassBell(this, null));
    }
    
    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.GLASS_BELL;
    }
}
