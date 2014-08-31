package net.multiplemonomials.eer.client.handler;

import java.text.DecimalFormat;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.exchange.EnergyRegistry;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.exchange.WrappedStack;
import net.multiplemonomials.eer.interfaces.IChargeable;
import net.multiplemonomials.eer.interfaces.IStoresEMC;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        	if(event.itemStack.getItem() instanceof IStoresEMC)
        	{
        		IStoresEMC itemWithEMCStorage = (IStoresEMC) event.itemStack.getItem();
        		event.toolTip.add(String.format("Stored EMC: %s", emcDecimalFormat.format(itemWithEMCStorage.getAvailableEMC(event.itemStack))));
        		
        		double maxStorableEMC = itemWithEMCStorage.getMaxStorableEMC(event.itemStack);
        		
        		if(maxStorableEMC > 0)
        		{
        			event.toolTip.add(String.format("Max EMC: %s", emcDecimalFormat.format(itemWithEMCStorage.getMaxStorableEMC(event.itemStack))));
        		}
        	}
        	else if(event.itemStack.getItem() instanceof IChargeable)
        	{
        		//this makes the possibly naive assumption that all chargeable items use their metadata to store their charge
        		// I WANT MULTIPLE INHERITANCEE!!!!!!!!!!!!!!!
        		event.toolTip.add(String.format("Charge Level: %d/%d", (CommonConfiguration.MAX_ITEM_CHARGES - event.itemStack.getItemDamage()) + 1, CommonConfiguration.MAX_ITEM_CHARGES + 1));
        		
        	}
        	else
        	{
	            WrappedStack stack = new WrappedStack(event.itemStack);
	
	            if (EnergyRegistry.getInstance().hasEnergyValue(stack))
	            {
	                EnergyValue emcValue = EnergyRegistry.getInstance().getEnergyValue(stack);
	                if (stack.getStackSize() > 1)
	                {
	                    event.toolTip.add(String.format("EMC (Item): %s", emcDecimalFormat.format(emcValue.getValue())));
	                    event.toolTip.add(String.format("EMC (Stack): %s", emcDecimalFormat.format(stack.getStackSize() * emcValue.getValue())));
	                }
	                else
	                {
	                    event.toolTip.add(String.format("EMC: %s", emcDecimalFormat.format(stack.getStackSize() * emcValue.getValue())));
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
