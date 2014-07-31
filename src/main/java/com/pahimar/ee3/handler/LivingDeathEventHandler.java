package com.pahimar.ee3.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.pahimar.ee3.data.EERExtendedPlayer;

public class LivingDeathEventHandler
{
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			EERExtendedPlayer.saveProxyData((EntityPlayer) event.entity);
		}
	}
}
