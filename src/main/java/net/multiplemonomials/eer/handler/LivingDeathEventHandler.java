package net.multiplemonomials.eer.handler;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.multiplemonomials.eer.chat.EntityLivingDeathMessager;
import net.multiplemonomials.eer.data.EERExtendedPlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

public class LivingDeathEventHandler
{
	//for some reason, it seems that the LivingDeathEvent can sometimes be sent twice
	static LivingDeathEvent previousEvent;
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(previousEvent == null || event != previousEvent)
		{
			Side currentEffectiveSide = FMLCommonHandler.instance().getEffectiveSide();
			if(currentEffectiveSide == Side.SERVER && event.entity instanceof EntityPlayer)
			{
				EERExtendedPlayer.saveProxyData((EntityPlayer) event.entity);
			}
			//stop bats in caves from burning to death all the time
			else if(event.entityLiving instanceof EntityBat && event.source.isFireDamage())
			{
				event.setCanceled(true);
			}
			else if(currentEffectiveSide == Side.SERVER && event.entityLiving instanceof EntityLiving)
			{
				EntityLivingDeathMessager.showDeathMessage(((EntityLiving)event.entityLiving), event.source);
			}
		}
		
		previousEvent = event;
	}
}
