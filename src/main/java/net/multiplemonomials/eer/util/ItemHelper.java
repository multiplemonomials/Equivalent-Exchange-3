package net.multiplemonomials.eer.util;

import net.multiplemonomials.eer.inventory.slot.ShowcaseSlot;
import net.multiplemonomials.eer.reference.Compare;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ItemHelper
{
    public static Comparator<ItemStack> comparator = new Comparator<ItemStack>()
    {
        public int compare(ItemStack itemStack1, ItemStack itemStack2)
        {
            if (itemStack1 != null && itemStack2 != null)
            {
                // Sort on itemID
                if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == Compare.EQUALS)
                {
                    // Then sort on meta
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                    {
                        // Then sort on NBT
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                        {
                            // Then sort on stack size
                            if (itemStack1.getTagCompound().equals(itemStack2.getTagCompound()))
                            {
                                return (itemStack1.stackSize - itemStack2.stackSize);
                            }
                            else
                            {
                                return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                            }
                        }
                        else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound())
                        {
                            return -1;
                        }
                        else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound()))
                        {
                            return 1;
                        }
                        else
                        {
                            return (itemStack1.stackSize - itemStack2.stackSize);
                        }
                    }
                    else
                    {
                        return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                    }
                }
                else
                {
                    return Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem());
                }
            }
            else if (itemStack1 != null && itemStack2 == null)
            {
                return -1;
            }
            else if (itemStack1 == null && itemStack2 != null)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    };

    /**
     * Compares two ItemStacks for equality, testing itemID, metaData, stackSize, and their NBTTagCompounds (if they are
     * present)
     *
     * @param first
     *         The first ItemStack being tested for equality
     * @param second
     *         The second ItemStack being tested for equality
     *
     * @return true if the two ItemStacks are equivalent, false otherwise
     */
    public static boolean equals(ItemStack first, ItemStack second)
    {
        return (comparator.compare(first, second) == 0);
    }

    public static int compare(ItemStack itemStack1, ItemStack itemStack2)
    {
        return comparator.compare(itemStack1, itemStack2);
    }

    public static String toString(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            return String.format("%sxitemStack[%s@%s]", itemStack.stackSize, itemStack.getUnlocalizedName(), itemStack.getItemDamage());
        }

        return "null";
    }
    
    /**
     * returns whether two ItemStacks are similar.  Like equals(), but ignores stack size and NBT.
     * 
     * Two null stacks count as similar.
     * @return
     */
    public static boolean similar(ItemStack first, ItemStack second)
    {
    	if(first == null && second == null)
    	{
    		return true;
    	}
    	
    	else if(first == null || second == null)
    	{
    		return false;
    	}
    	return first.getItem() != second.getItem() ? false : (first.getItemDamage() == second.getItemDamage()); 
    				
    }
    
    /**
     * returns whether two ItemStacks are similar.  Like equals(), but ignores stack size and NBT.
     * 
     * Two null stacks count as similar.
     * @return
     */
    public static boolean equalsIgnoreStackSize(ItemStack first, ItemStack second)
    {
    	if(first == null && second == null)
    	{
    		return true;
    	}
    	
    	else if(first == null || second == null)
    	{
    		return false;
    	}
    	return first.getItem() != second.getItem() ? false : (first.getItemDamage() != second.getItemDamage() ? false : (first.getTagCompound() == second.getTagCompound())); 
    				
    }
    
    /**
     * Adds the ItemStack to the first available slot in the inventory
     * 
     * Splits the items if the stack is more than 64.
     * 
     * Assumes it is not called with a null or zero-size ItemStack.
     * 
     * @param itemsToProduce
     * @param inventory
     * @param blacklistedSlots the set of slots to NOT put items into if they are empty.
     * 
     * @return the number of items that could not fit, if any
     */
    
    //I can't figure out how mergeItemStack works, so I wrote this instead
    public static int addItemsToInventory(ItemStack itemsToProduce, IInventory inventory, Set<Integer> blacklistedSlots)
    {
		for(int counter = 0; counter < inventory.getSizeInventory(); ++counter)
		{
			if(itemsToProduce.stackSize == 0)
			{
				break;
			}
			else if(blacklistedSlots.isEmpty())
			{
				if(blacklistedSlots.contains(counter))
				{
					continue;
				}
			}
			
			ItemStack currentStack = inventory.getStackInSlot(counter);
			
			if(currentStack == null)
			{
				currentStack = itemsToProduce.copy();
				
				currentStack.stackSize = itemsToProduce.stackSize;
				
				if(currentStack.stackSize > currentStack.getItem().getItemStackLimit(currentStack))
				{
					currentStack.stackSize = currentStack.getItem().getItemStackLimit(currentStack);
				}
				
				itemsToProduce.stackSize -= currentStack.stackSize;
				
				inventory.setInventorySlotContents(counter, currentStack);
			}
			else if(currentStack.stackSize < currentStack.getItem().getItemStackLimit(currentStack) && ItemHelper.equalsIgnoreStackSize(itemsToProduce, currentStack))
			{
				int itemsToAddToStack = itemsToProduce.stackSize;
				
				if(itemsToAddToStack + currentStack.stackSize > currentStack.getItem().getItemStackLimit(currentStack))
				{
					itemsToAddToStack = currentStack.getItem().getItemStackLimit(currentStack) - currentStack.stackSize;
				}
				
				currentStack.stackSize += itemsToAddToStack;
				itemsToProduce.stackSize -= itemsToAddToStack;
				
				inventory.setInventorySlotContents(counter, currentStack);
			}
		}
		
		return itemsToProduce.stackSize;
	}
    
    final static HashSet<Integer> emptyHashSet = new HashSet<Integer>();
    
    /**
     * Same as addItemsToInventory(itemsToProduce, inventory, new HashSet&ltInteger&gt())
     * @param itemsToProduce
     * @param inventory
     * @return
     */
    public static int addItemsToInventory(ItemStack itemsToProduce, IInventory inventory)
    {
    	return addItemsToInventory(itemsToProduce, inventory, emptyHashSet);
    }
    
    /**
     * generic transferStackInSlot method shared by most EER machines
     * 
     * @param entityPlayer
     * @param slotIndex
     * @param machineInventorySize
     * @return
     */
    public static ItemStack transferStackInSlot(EntityPlayer entityPlayer, IInventory machineInventory, Slot slot, int slotIndex, int machineInventorySize)
    {
        ItemStack itemStack = null;

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotItemStack = slot.getStack();
            
            if((slot instanceof ShowcaseSlot ? ((ShowcaseSlot)slot).canTakeStack(entityPlayer) : slot.canTakeStack(entityPlayer)))
            {
	            itemStack = slotItemStack.copy();
	            
	            int originalStackSize = itemStack.stackSize;
	
	            if (slotIndex < machineInventorySize)
	            {
	            	//copy from tile entity inventory to the player's
	                itemStack.stackSize = addItemsToInventory(itemStack, entityPlayer.inventory);
	            }
	            else
	            {
	                //copy from player inventory to the tile entity's
	            	 itemStack.stackSize = addItemsToInventory(itemStack, machineInventory);
	            }
	            
	            if(itemStack.stackSize == 0)
	            {
	                slot.putStack(null);
	            }
	            
	            else
	            {
	            	slot.putStack(itemStack);
	            }
	
	            if(itemStack.stackSize != originalStackSize)
	            {
	            	//not exactly sure what the second parameter is
	                slot.onPickupFromSlot(entityPlayer, slotItemStack);
	            }
	            
	            return itemStack.stackSize == 0 ? null : itemStack;
	            
            }
        }
        
        return null;
    }
    
    //GALGEJGDGJPODSGVJIGJPO WHHHYYYYY?
    //Because Mojang couldn't be bothered to implement ItemStack.equals(ItemStack)
    public static boolean containsItem(Set<ItemStack> set, ItemStack itemStack)
    {
    	for(ItemStack stackInSet : set)
    	{
    		if(ItemStack.areItemStacksEqual(itemStack, stackInSet))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Takes one from the itemstack, or sets it to null if it is one.
     * @param itemStack
     * @return
     */
    public static ItemStack takeOneFromItemStack(ItemStack itemStack)
    {
    	if(itemStack.stackSize > 1)
    	{
    		itemStack.stackSize -= 1;
    	}
    	else
    	{
    		itemStack = null;
    	}
    	
    	return itemStack;
    }
    
    /**
     * shorter way of itemStack.getItem().getItemStackLimit(itemStack)
     * @return
     */
    public static int maxStackSize(ItemStack itemStack)
    {
    	return itemStack.getItem().getItemStackLimit(itemStack);
    }
}
