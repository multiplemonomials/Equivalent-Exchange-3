package net.multiplemonomials.eer.client.handler;

import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.exchange.WrappedStack;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.item.ItemKleinStar;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;

/**
 * Equivalent-Exchange-3
 * <p/>
 * ItemTooltipEventHandler
 *
 * @author pahimar
 */
@SideOnly(Side.CLIENT)
public class ItemTooltipEventHandler
{
    private static DecimalFormat emcDecimalFormat = new DecimalFormat("###,###,###,###,###.###");

    @SubscribeEvent
    public void handleItemTooltipEvent(ItemTooltipEvent event)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        {
        	if(event.itemStack.getItem() == ModItems.kleinStar)
        	{
        		event.toolTip.add("Stored EMC: " + String.format("%s", emcDecimalFormat.format(ItemKleinStar.getAvailableEMC(event.itemStack))));
        		event.toolTip.add("Max EMC: " + String.format("%s", emcDecimalFormat.format(ItemKleinStar.getMaxStorableEMC(event.itemStack))));
        	}
        	else
        	{
	            WrappedStack stack = new WrappedStack(event.itemStack);
	
	            if (EnergyRegistry.getInstance().hasEnergyValue(stack))
	            {
	                EnergyValue emcValue = EnergyRegistry.getInstance().getEnergyValue(stack);
	                if (stack.getStackSize() > 1)
	                {
	                    event.toolTip.add("EMC (Item): " + String.format("%s", emcDecimalFormat.format(emcValue.getValue())));
	                    event.toolTip.add("EMC (Stack): " + String.format("%s", emcDecimalFormat.format(stack.getStackSize() * emcValue.getValue())));
	                }
	                else
	                {
	                    event.toolTip.add("EMC: " + String.format("%s", emcDecimalFormat.format(stack.getStackSize() * emcValue.getValue())));
	                }
	            }
	            else
	            {
	                event.toolTip.add("No EMC value");
	            }
        	}
        }
    }
}
