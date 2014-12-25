package net.multiplemonomials.eer.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IWantsUpdatesInAlchemicalStorage;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;

public class ItemTalismanRepair extends ItemEE implements IWantsUpdatesInAlchemicalStorage, IBauble
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

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity)
	{
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		onUpdateInAlchemyChest(itemstack, player.worldObj, player.inventory, 0);
    	}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
		//do nothing
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		//do nothing
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
    
    
    
}
