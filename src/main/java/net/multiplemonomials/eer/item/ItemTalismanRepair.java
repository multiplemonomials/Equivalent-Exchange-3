package net.multiplemonomials.eer.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IWantsUpdatesInAlchemicalStorage;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;

public class ItemTalismanRepair extends ItemEE implements IWantsUpdatesInAlchemicalStorage
{
	int _ticksLeftBeforeAction;
	
	 public ItemTalismanRepair()
    {
        super();
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setUnlocalizedName(Names.Items.TALISMAN_REPAIR);
        
        _ticksLeftBeforeAction = CommonConfiguration.TALISMAN_OF_REPAIR_TICKS_PER_DURABILITY;
    }
	 
    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, Names.Items.TALISMAN_REPAIR);
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isHeldItem) 
    {
    	//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		onUpdateInAlchemyChest(itemStack, world, player.inventory, indexInInventory);
    	}
    }
    
    public void onUpdateInAlchemyChest(ItemStack itemStack, World world, IInventory chest, int indexInInventory)
    {
    	if(_ticksLeftBeforeAction-- <= 0)
    	{
	    	for(int index = chest.getSizeInventory() - 1; index >= 0; --index)
	    	{
	    		ItemStack currentStack = chest.getStackInSlot(index);
	    		
	    		if(currentStack != null && (currentStack.getItem().isRepairable() && currentStack.isItemDamaged()))
	    		{
	    			currentStack.setItemDamage(currentStack.getItemDamage() - 1);
	    		}
	    	}
	    	
	    	_ticksLeftBeforeAction = CommonConfiguration.TALISMAN_OF_REPAIR_TICKS_PER_DURABILITY;
    	}
    }
    
    
    
}
