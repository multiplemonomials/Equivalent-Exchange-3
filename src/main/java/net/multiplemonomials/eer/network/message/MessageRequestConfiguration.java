package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.multiplemonomials.eer.configuration.ReceivedConfigAction;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Message to request config files from the server
 */
public class MessageRequestConfiguration implements IMessage, IMessageHandler<MessageRequestConfiguration, IMessage>
{

    public MessageRequestConfiguration()
    {
    }

    /**
     * Request a config file from the server.
     * @param fileName the name of the file/its relative path from the config folder
     * @param actionToPerform what to do with the file once it is back at the client
     */
    public MessageRequestConfiguration(String fileName, ReceivedConfigAction actionToPerform)
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void toBytes(ByteBuf out)
    {
    }

    @Override
    public IMessage onMessage(MessageRequestConfiguration message, MessageContext ctx)
    {
    	LogHelper.info("Sending common configuration to player " + ctx.getServerHandler().playerEntity.getCommandSenderName());
    	PacketHandler.INSTANCE.sendTo(new MessageCommonConfigUpdate(), ctx.getServerHandler().playerEntity);
    	
    	return null;
    }
}
