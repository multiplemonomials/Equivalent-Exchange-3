package net.multiplemonomials.eer.handler;

import net.multiplemonomials.eer.configuration.ReceivedConfigAction;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageRequestConfiguration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class ServerConnectionHandler
{
	@SubscribeEvent
	public void onClientConnection(ClientConnectedToServerEvent event)
	{
		//send common configuration file
		PacketHandler.INSTANCE.sendToServer(new MessageRequestConfiguration("common.properties", ReceivedConfigAction.LOAD_AS_COMMON_CONFIG));
	}
}
