package net.multiplemonomials.eer.handler;

import net.multiplemonomials.eer.configuration.ReceivedConfigAction;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageRequestConfiguration;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class ServerConnectionHandler
{
	@SubscribeEvent
	public void onClientConnection(ClientConnectedToServerEvent event)
	{
		LogHelper.info("Requesting server config file...");
		//send common configuration file
		PacketHandler.INSTANCE.sendToServer(new MessageRequestConfiguration("common.properties", ReceivedConfigAction.LOAD_AS_COMMON_CONFIG));
	}
}