package net.multiplemonomials.eer.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.multiplemonomials.eer.EquivalentExchangeReborn;
import net.multiplemonomials.eer.reference.GuiIds;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;

public class ItemAssignmentGUIActivator extends ItemEE
{	
	 public ItemAssignmentGUIActivator()
    {
        super();
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setUnlocalizedName(Names.Items.ASSIGNMENT_GUI_ACTIVATOR);
    }
	 
    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, Names.Items.ASSIGNMENT_GUI_ACTIVATOR);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if(world.isRemote)
        {
            entityPlayer.openGui(EquivalentExchangeReborn.instance, GuiIds.EMC_ASSIGNMENT, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        }

        return itemStack;
    }
    
    
}
