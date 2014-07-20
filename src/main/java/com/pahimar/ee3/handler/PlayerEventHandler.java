package com.pahimar.ee3.handler;

import com.pahimar.ee3.item.ItemRingFlight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerEventHandler
{
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		//System.out.println("[EER] Player Tick Handler Called, client side? " + event.player.worldObj.isRemote);
		
		EntityPlayer player = event.player;
		
		ItemStack ringFlight = null;
		
		//TODO: does this need to be run on both the client on the server? Maybe it can be only run on one.
		
		//process flight ring
		//this has to be done in here so that flight can be turned off once the ring is no longer in the hotbar
		//however, this has the unfortunate side effect of nuking any other mods which provide flight this way
		//TODO: use IExtendedEntityProperties to fix this
		//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571567-1-7-2-1-6-4-eventhandler-and
		for(int index = 0; index < InventoryPlayer.getHotbarSize(); ++index)
		{
			if(player.inventory.mainInventory[index] != null && player.inventory.mainInventory[index].getItem() instanceof ItemRingFlight)
			{
				ringFlight = player.inventory.mainInventory[index];
			}
		}
		
		
		if(ringFlight != null)
		{
			player.capabilities.allowFlying = true;
		}
		else if(!player.capabilities.isCreativeMode)
		{
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
		}
		
	}
}
