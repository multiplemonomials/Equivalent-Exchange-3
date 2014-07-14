package com.pahimar.ee3.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;

import com.pahimar.ee3.exchange.EnergyRegistry;
import com.pahimar.ee3.exchange.EnergyValue;
import com.pahimar.ee3.network.PacketHandler;
import com.pahimar.ee3.network.message.MessageTileEntityEE;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.util.LogHelper;

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
    
    public Set<ItemStack> learnedItems;
    
    private int currentPage;
    
    //caches the items that can be transmuted with the current EMC and input
    private List<ItemStack> transmutableItems;

    public double leftoverEMC;
    
    private ItemStack previousInputSlotContents = null;

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }
    
    public TileEntityTransmutationTablet()
    {
    	super();
    	
    	inventory = new ItemStack[INVENTORY_SIZE];
    	
    	learnedItems = new HashSet<ItemStack>();
    	
    	transmutableItems = new ArrayList<ItemStack>();
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
	
	//it's all done using containers (!)
    public boolean canUpdate()
    {
        return false;
    }
	
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        if(learnedItems.isEmpty())
	    {
        	// Write the known items to NBT
	        NBTTagList learnedItemList = new NBTTagList();
	        
	        for(ItemStack itemStack : learnedItems)
	        {
	                NBTTagCompound tagCompound = new NBTTagCompound();
	                itemStack.writeToNBT(tagCompound);
	                learnedItemList.appendTag(tagCompound);
	        }
	        nbtTagCompound.setTag("learnedItems", learnedItemList);
	    }
        
        //write inventory to NBT
        NBTTagCompound inputSlotCompound = new NBTTagCompound();
        NBTTagCompound powerSlotCompound = new NBTTagCompound();
        
        if(inventory[INPUT_SLOT_INDEX] != null)
        {
        	inventory[INPUT_SLOT_INDEX].writeToNBT(inputSlotCompound);
        }
        
        if(inventory[ENERGY_SLOT_INDEX] != null)
        {
        	inventory[ENERGY_SLOT_INDEX].writeToNBT(powerSlotCompound);
        }
        
        nbtTagCompound.setTag("inputSlot", inputSlotCompound);

        nbtTagCompound.setTag("energySlot", powerSlotCompound);
        
        nbtTagCompound.setDouble("leftoverEMC", leftoverEMC);

    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        // read the known items from NBT
        NBTTagList learnedItemList = nbtTagCompound.getTagList("learnedItems", 10);
        for(int currentIndex = 0; currentIndex < learnedItemList.tagCount(); ++currentIndex)
        {
                NBTTagCompound tagCompound = learnedItemList.getCompoundTagAt(currentIndex);
                
                ItemStack itemStackToAdd = ItemStack.loadItemStackFromNBT(tagCompound);
                
                if(itemStackToAdd == null)
                {
                	LogHelper.error("[Transmutation Tablet] Could not load a learned item from NBT");
                	continue;
                }
                
                EnergyValue energyValue = EnergyRegistry.getInstance().getEnergyValue(itemStackToAdd);
                
                if(energyValue == null)
                {
                	LogHelper.error("[Transmutation Tablet] Could not load the EMC value of a learned item");
                	continue;
                }
                
                learnedItems.add(itemStackToAdd);
                
        }
        
        //read inventory from NBT
        NBTTagCompound inputSlotCompound = nbtTagCompound.getCompoundTag("inputSlot");
        NBTTagCompound energySlotCompound = nbtTagCompound.getCompoundTag("energySlot");
        
        inventory[INPUT_SLOT_INDEX] = ItemStack.loadItemStackFromNBT(inputSlotCompound);
        inventory[ENERGY_SLOT_INDEX] = ItemStack.loadItemStackFromNBT(energySlotCompound);
        
        leftoverEMC = nbtTagCompound.getDouble("leftoverEMC");
        
        recalculateTransmutableItems();

    }
    
    /**
     * Adds the ItemStack to the database of learned items.  
     * @param itemToLearn
     */
    public void learnNewItem(ItemStack itemToLearn)
    {       	
    	learnedItems.add(itemToLearn);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityEE(this));

    }
    
    /**
     * Attempts to transmute as many of the provided ItemStack as possible.
     * 
     * Subtracts from the tablet's EMC value.
     * 
     * @param itemStack
     * @param itemLimit how many of the item to try to transmute
     * @return how many of the item could be transmuted
     */
    public int tryTransmute(ItemStack itemStack, int itemLimit)
    {	
    	EnergyValue energyValue = EnergyRegistry.getInstance().getEnergyValue(itemStack);
    	if(energyValue == null || inventory[INPUT_SLOT_INDEX] == null)
    	{
    		return 0;
    	}
    	
    	int itemsLeftToProduce = itemLimit;
    	
    	double itemEMCValue = energyValue.getValue();
    	    	
    	//yes, in theory this could cause a null pointer exception, but the input stack should have been checked to have an EMC value
    	double inputSlotItemEMC = EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INDEX]).getValue();
    	
    	if(inputSlotItemEMC + leftoverEMC < itemEMCValue)
    	{
    		return 0;
    	}
    	
    	do
    	{
    		//TODO: get EMC from emc-holding items
    		
    		//take one item
    		if(leftoverEMC < itemEMCValue && inventory[INPUT_SLOT_INDEX] != null)
    		{
    			if(inventory[INPUT_SLOT_INDEX].stackSize > 1)
    			{
    				--inventory[INPUT_SLOT_INDEX].stackSize;
    			}
    			else
    			{
    				inventory[INPUT_SLOT_INDEX] = null;
    			}
    			
    			leftoverEMC += inputSlotItemEMC;
    		}
    		
    		//condense from leftoverEMC
	    	if(leftoverEMC >= itemEMCValue)
	    	{
	    		int itemsToMakeFromLeftovers = MathHelper.floor_double(leftoverEMC / itemEMCValue);
	    		itemsLeftToProduce -= itemsToMakeFromLeftovers;
	    		leftoverEMC -= itemEMCValue * itemsToMakeFromLeftovers;
	    	}
    	}
    	while(itemsLeftToProduce > 0 && inventory[INPUT_SLOT_INDEX] != null);
    	
    	return itemLimit - itemsLeftToProduce;
    	
    			
    		
    	
    }

    /**
     * Shows a page of the possible transmutation targets.
     * 
     * Pages start at 0.
     * @param i
     */
	public void showPage(int page) 
	{
		if(inventory[INPUT_SLOT_INDEX] != previousInputSlotContents)
		{
			recalculateTransmutableItems();
			
			previousInputSlotContents = inventory[INPUT_SLOT_INDEX];
		}
		
		if(!transmutableItems.isEmpty())
		{
			//NOTE: this code requires INPUT_SLOT_INDEX and ENERGY_SLOT_INDEX to be at opposite ends of inventory
			for(int counter = 1 + INPUT_SLOT_INDEX; counter < ENERGY_SLOT_INDEX && counter <= transmutableItems.size(); ++counter)
			{
				inventory[counter] = transmutableItems.get((counter + (page * 8)) - 1);
			}
		}	
		currentPage = page;
			
	}
	
	/**
	 * 
	 * @return  The current page.  Starts at 0.
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}
	
	/**
	 * 
	 * @return The maximum number of pages.  Starts at 1
	 */
	public int getNumberOfPages()
	{
		return MathHelper.clamp_int(MathHelper.ceiling_double_int(transmutableItems.size() / 8.0), 1, Integer.MAX_VALUE);
	}
	
	private void recalculateTransmutableItems()
	{
		
		transmutableItems = new ArrayList<ItemStack>();
		
		if(inventory[INPUT_SLOT_INDEX] == null || learnedItems.isEmpty())
		{
			return;
		}
		
		EnergyValue inputEnergyValue = EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INDEX]);
		
		//TODO: get EMC from emc-holding items
		double maxAvailableEMC = leftoverEMC + ((inputEnergyValue == null ? 0 : inputEnergyValue.getValue()) * (inventory[INPUT_SLOT_INDEX] == null ? 0 :inventory[INPUT_SLOT_INDEX].stackSize));
		
		for(ItemStack learnedItem : learnedItems)
    	{
    		if(learnedItem == null)
    		{
    			continue;
    		}
			
			double EMCValue = EnergyRegistry.getInstance().getEnergyValue(learnedItem).getValue();
    		
    		if(EMCValue <= maxAvailableEMC)
    		{
    			transmutableItems.add(new ItemStack(learnedItem.getItem(), MathHelper.floor_double(maxAvailableEMC / EMCValue), learnedItem.getItemDamage()));
    		}
    	}
	}
	
}
