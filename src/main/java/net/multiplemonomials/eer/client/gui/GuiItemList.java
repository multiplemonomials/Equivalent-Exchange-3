package net.multiplemonomials.eer.client.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.ItemHelper;
import cpw.mods.fml.client.GuiScrollingList;

public class GuiItemList extends GuiScrollingList
{
    private GuiEmcAssignment parent;

    public GuiItemList(GuiEmcAssignment parent, int listWidth)
    {
        super(parent.getMinecraftInstance(), listWidth, parent.getWidth(), 40, parent.getHeight() - 40, 10, 35);
        this.parent = parent;
    }

    @Override
    protected int getSize()
    {
        return  parent.getItemStackList().size();
    }

    @Override
    protected void elementClicked(int var1, boolean var2)
    {
        this.parent.selectItemIndex(var1);
    }

    @Override
    protected boolean isSelected(int var1)
    {
        return this.parent.itemIndexSelected(var1);
    }

    @Override
    protected void drawBackground()
    {
        this.parent.drawBackground();
    }

    @Override
    protected int getContentHeight()
    {
        return (this.getSize()) * 35 + 1;
    }

    @Override
    protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5)
    {
        ItemStack itemStack = parent.getItemStackList().get(listIndex);
        if (itemStack != null)
        {
        	String lookupString = ItemHelper.itemToString(itemStack);
        	EnergyValue emcValue;
        	
        	//load it from the list of changed values if we can
        	if(parent.changedItemValues.containsKey(lookupString))
        	{
        		emcValue = parent.changedItemValues.get(lookupString);
        	}
        	else
        	{
        		emcValue = EnergyRegistry.getInstance().getEnergyValue(itemStack);
        	}
            
            boolean hasValue = emcValue != null;

            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(itemStack.getDisplayName(), listWidth - 10), this.left + 3 , var3 + 2, 0xFFFFFF);
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(hasValue? StatCollector.translateToLocal(Names.GUI.HAS_ENERGY_VALUE): StatCollector.translateToLocal(Names.GUI.HAS_NO_ENERGY_VALUE), listWidth - 10), this.left + 3 , var3 + 12, hasValue? 0x00FF00: 0xFF0000);
            if (hasValue)
            {
                this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(String.valueOf(emcValue.getValue()), listWidth - 10), this.left + 3 , var3 + 22, 0xFFFFF);
            }
        }
    }
}