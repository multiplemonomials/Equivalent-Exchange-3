package net.multiplemonomials.eer.chat;

import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import cpw.mods.fml.common.FMLCommonHandler;

public class EntityLivingDeathMessager 
{
    //server instance stored for us to use later
    static MinecraftServer server;
	
	public static void showDeathMessage(EntityLiving entityLiving, DamageSource damageSource)
	{
		if(CommonConfiguration.SHOW_ALL_DEATH_MESSAGES)
		{
			if(server == null)
			{
				server = FMLCommonHandler.instance().getMinecraftServerInstance();
			}
			
			//                                         getCombatTracker().getDeathMessage()
			IChatComponent deathMessage = entityLiving.func_110142_aN().func_151521_b();
			
			server.getConfigurationManager().sendChatMsg(deathMessage);
		}
	}
}
