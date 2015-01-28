package net.multiplemonomials.eer.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.reference.Key;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.LogHelper;

public class ItemPhilosophersStone extends ItemEE implements IKeyBound
{
    public ItemPhilosophersStone()
    {
        super();
        this.setUnlocalizedName(Names.Items.PHILOSOPHERS_STONE);
        this.setMaxDamage(1000);
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean getShareTag()
    {
        return true;
    }
    
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack copiedStack = itemStack.copy();

        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;

        return copiedStack;
    }

    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key)
    {
        LogHelper.info(String.format("%s %s %s", entityPlayer.toString(), itemStack.toString(), key.toString()));
    }
}
