package net.multiplemonomials.eer.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.data.EERExtendedPlayer;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageTileEntityEE;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.ItemHelper;

public class TileEntityTransmutationTablet extends TileEntityEE implements IInventory
{    
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
    	super(INVENTORY_SIZE);
    	
    	if(transmutableItems == null)
		{
			transmutableItems = new ArrayList<ItemStack>();
		}
    }
    
    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.TRANSMUTATION_TABLET;
    }
	
	//it's all done using containers and GUI code (!)
    @Override
    public boolean canUpdate()
    {
        return false;
    }
	
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        
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
        
        //read inventory from NBT
        NBTTagCompound inputSlotCompound = nbtTagCompound.getCompoundTag("inputSlot");
        NBTTagCompound energySlotCompound = nbtTagCompound.getCompoundTag("energySlot");
        
        inventory[INPUT_SLOT_INDEX] = ItemStack.loadItemStackFromNBT(inputSlotCompound);
        inventory[ENERGY_SLOT_INDEX] = ItemStack.loadItemStackFromNBT(energySlotCompound);
        leftoverEMC = nbtTagCompound.getDouble("leftoverEMC");

    }
    
    /**
     * Adds the ItemStack to the database of learned items.  
     * @param itemToLearn
     */
    public void learnNewItem(ItemStack itemToLearn, EERExtendedPlayer playerData)
    {   
    	if(itemToLearn != null)
    	{
    		ItemStack stackToAdd = new ItemStack(itemToLearn.getItem(), 1, itemToLearn.getItemDamage());
    		if(!ItemHelper.containsItem(playerData.learnedItems, stackToAdd))
    		{
        		playerData.learnedItems.add(stackToAdd);

    		}
    	}
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityEE(this));

    }
    
    /**
     * Attempts to transmute the provided ItemStack.
     * 
     * Subtracts from the tablet's EMC storage.
     * 
     * @param itemStack
     * @param itemLimit how many of the item to try to transmute
     * @return how many of the item could be transmuted
     */
    public int tryTransmute(ItemStack itemStack, int itemLimit, EERExtendedPlayer learnedItemsData)
    {	
    	EnergyValue energyValue = EnergyRegistry.getInstance().getEnergyValue(itemStack);
    	if(energyValue == null)
    	{
    		return 0;
    	}
    	
    	int itemsLeftToProduce = itemLimit;
    	
    	double itemEMCValue = energyValue.getValue();
    	    	
    	double inputSlotItemEMC = 0;
    	
    	if(inventory[INPUT_SLOT_INDEX] != null)
    	{
    		inputSlotItemEMC = EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INDEX]).getValue();
    	}
    	
    	if(getMaxAvailableEMC() < itemEMCValue)
    	{
    		return 0;
    	}
    	
    	do
    	{
    		
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
	    		int itemsToMakeFromLeftovers = MathHelper.clamp_int(MathHelper.floor_double(leftoverEMC / itemEMCValue), 0, itemLimit);
	    		itemsLeftToProduce -= itemsToMakeFromLeftovers;
	    		leftoverEMC -= itemEMCValue * itemsToMakeFromLeftovers;
	    	}
	    	
	    	//condense from Klein Star
	    	if((inventory[ENERGY_SLOT_INDEX] != null && inventory[ENERGY_SLOT_INDEX].getItem() instanceof IStoresEMC) && itemsLeftToProduce > 0)
	    	{
	    		IStoresEMC emcStoringItem = ((IStoresEMC)inventory[ENERGY_SLOT_INDEX].getItem());
	    		if(emcStoringItem.isEMCBattery())
	    		{
	    			double availableEMC =  emcStoringItem.getAvailableEMC(inventory[ENERGY_SLOT_INDEX]);
	    			int itemsToMakeFromKleinStar = MathHelper.clamp_int(MathHelper.floor_double(availableEMC / itemEMCValue), 0, itemLimit);
	    			itemsLeftToProduce -= itemsToMakeFromKleinStar;
	    			emcStoringItem.tryTakeEMC(inventory[ENERGY_SLOT_INDEX] , itemEMCValue * itemsToMakeFromKleinStar);
	    		}
	    	}
    	}
    	while(itemsLeftToProduce > 0 && inventory[INPUT_SLOT_INDEX] != null);
    	
    	int itemsProduced = itemLimit - itemsLeftToProduce;
    	
    	if(itemsProduced > 0)
    	{
    		recalculateTransmutableItems(learnedItemsData);
    		showPage(getCurrentPage(), learnedItemsData);
    	}
    	
    	return itemsProduced;
    	
    			
    		
    	
    }
    
    /**
     * Called to drain the leftover EMC to a klein star if one is in the slot
     */
    private void drainLeftoversToKleinStar()
    {
    	if(leftoverEMC != 0 && (inventory[ENERGY_SLOT_INDEX] != null && inventory[ENERGY_SLOT_INDEX].getItem() instanceof IStoresEMC))
    	{
    		IStoresEMC emcStoringItem = ((IStoresEMC)inventory[ENERGY_SLOT_INDEX].getItem());
    		if(emcStoringItem.isEMCBattery())
    		{
    			leftoverEMC -= emcStoringItem.tryAddEMC(inventory[ENERGY_SLOT_INDEX], leftoverEMC);
    		}
    	}
    }

    /**
     * Shows a page of the possible transmutation targets.
     * 
     * Pages start at 0.
     * @param i
     */
	public void showPage(int page, EERExtendedPlayer learnedItemsData) 
	{
		if(inventory[INPUT_SLOT_INDEX] != previousInputSlotContents)
		{
			recalculateTransmutableItems(learnedItemsData);
		}
		
		if(!transmutableItems.isEmpty())
		{
			//NOTE: this code requires INPUT_SLOT_INDEX and ENERGY_SLOT_INDEX to be at opposite ends of inventory
			for(int counter = 1 + INPUT_SLOT_INDEX; counter < ENERGY_SLOT_INDEX; ++counter)
			{
				int indexInTransmutableItems = (counter + (page * 8)) - 1;
				if(indexInTransmutableItems >= transmutableItems.size())
				{
					inventory[counter] = null;
				}
				else
				{
					inventory[counter] = transmutableItems.get(indexInTransmutableItems);
				}
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
	
	/**
	 *
	 * @return the sum total of all the potential and stored EMC from this block
	 */
	public double getMaxAvailableEMC()
	{
		double maxAvailableEMC = leftoverEMC;
		
		if(inventory[ENERGY_SLOT_INDEX] != null && inventory[ENERGY_SLOT_INDEX].getItem() instanceof IStoresEMC)
		{
			IStoresEMC emcStoringItem = ((IStoresEMC)inventory[ENERGY_SLOT_INDEX].getItem());
    		if(emcStoringItem.isEMCBattery())
    		{
    			maxAvailableEMC += emcStoringItem.getAvailableEMC(inventory[ENERGY_SLOT_INDEX]);
    		}
		}
		
		if(inventory[INPUT_SLOT_INDEX] != null)
		{
			EnergyValue inputEnergyValue = EnergyRegistry.getInstance().getEnergyValue(inventory[INPUT_SLOT_INDEX]);
			
			if(inputEnergyValue != null)
			{
				maxAvailableEMC += (inputEnergyValue.getValue() * inventory[INPUT_SLOT_INDEX].stackSize);
			}
		}
		
		return maxAvailableEMC;
	}
	
	private void recalculateTransmutableItems(EERExtendedPlayer learnedItemsData)
	{
		drainLeftoversToKleinStar();
		
		transmutableItems = new ArrayList<ItemStack>();
		
		if(learnedItemsData.learnedItems.isEmpty())
		{
			return;
		}
		
		double maxAvailableEMC = getMaxAvailableEMC();
		
		for(ItemStack learnedItem : learnedItemsData.learnedItems)
    	{
			
    		if(learnedItem == null)
    		{
    			continue;
    		}
			
			double EMCValue = EnergyRegistry.getInstance().getEnergyValue(learnedItem).getValue();
    		
    		if(EMCValue <= maxAvailableEMC)
    		{
    			
    			transmutableItems.add(new ItemStack(learnedItem.getItem(), 1, learnedItem.getItemDamage()));
    		}
    	}
		
		previousInputSlotContents = inventory[INPUT_SLOT_INDEX];

	}
	
}
