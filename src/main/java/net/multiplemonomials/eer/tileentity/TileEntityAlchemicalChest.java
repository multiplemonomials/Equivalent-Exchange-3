package net.multiplemonomials.eer.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.interfaces.IWantsUpdatesInAlchemicalStorage;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileAlchemicalChest;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Sounds;

public class TileEntityAlchemicalChest extends TileEntityEE
{
    /**
     * The current angle of the chest lid (between 0 and 1)
     */
    public float lidAngle;

    /**
     * The angle of the chest lid last tick
     */
    public float prevLidAngle;

    /**
     * The number of players currently using this chest
     */
    public int numUsingPlayers;

    /**
     * Server sync counter (once per 20 ticks)
     */
    private int ticksSinceSync;

    public TileEntityAlchemicalChest(int inventorySize, int metaData)
    {
        super(inventorySize);
        this.state = (byte) metaData;
    }
    
    public boolean upgradeToNextLevel()
    {
    	return false;
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    @Override
    public boolean receiveClientEvent(int eventID, int numUsingPlayers)
    {
        if (eventID == 1)
        {
            this.numUsingPlayers = numUsingPlayers;
            return true;
        }
        else
        {
            return super.receiveClientEvent(eventID, numUsingPlayers);
        }
    }

    @Override
    public void openInventory()
    {
        ++numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.alchemicalChest, 1, numUsingPlayers);
    }

    @Override
    public void closeInventory()
    {
        --numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.alchemicalChest, 1, numUsingPlayers);
    }
    
    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.ALCHEMICAL_CHEST;
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
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileAlchemicalChest(this));

    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        //this seems kinda laggy
        //not sure if there's a better way
        for(int index = 0; index < inventory.length; ++index)
        {
        	if(inventory[index] != null && inventory[index].getItem() instanceof IWantsUpdatesInAlchemicalStorage)
        	{
        		IWantsUpdatesInAlchemicalStorage itemToUpdate = (IWantsUpdatesInAlchemicalStorage)(inventory[index].getItem());
        		
        		itemToUpdate.onUpdateInAlchemyChest(inventory[index], worldObj, this, index);
        	}
        }

        if (++ticksSinceSync % 20 * 4 == 0)
        {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.alchemicalChest, 1, numUsingPlayers);
        }

        prevLidAngle = lidAngle;
        float angleIncrement = 0.1F;
        double adjustedXCoord, adjustedZCoord;

        if (numUsingPlayers > 0 && lidAngle == 0.0F)
        {
            adjustedXCoord = xCoord + 0.5D;
            adjustedZCoord = zCoord + 0.5D;
            worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, Sounds.CHEST_OPEN, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
        {
            float var8 = lidAngle;

            if (numUsingPlayers > 0)
            {
                lidAngle += angleIncrement;
            }
            else
            {
                lidAngle -= angleIncrement;
            }

            if (lidAngle > 1.0F)
            {
                lidAngle = 1.0F;
            }

            if (lidAngle < 0.5F && var8 >= 0.5F)
            {
                adjustedXCoord = xCoord + 0.5D;
                adjustedZCoord = zCoord + 0.5D;
                worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, Sounds.CHEST_CLOSE, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (lidAngle < 0.0F)
            {
                lidAngle = 0.0F;
            }
        }
    }
}
