package net.multiplemonomials.eer.handler;

import net.multiplemonomials.eer.client.gui.inventory.GuiTransmutationTablet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiScreenEvent;

public class ButtonHandler 
{
	@SubscribeEvent
	public void onButtonClicked(GuiScreenEvent.ActionPerformedEvent.Pre actionPerformedEvent)
	{
		if(actionPerformedEvent.gui instanceof GuiTransmutationTablet)
		{
			GuiTransmutationTablet guiTransmutationTablet = (GuiTransmutationTablet) actionPerformedEvent.gui;
			
			guiTransmutationTablet.onButtonClicked(actionPerformedEvent.button);
		}
	}
}
