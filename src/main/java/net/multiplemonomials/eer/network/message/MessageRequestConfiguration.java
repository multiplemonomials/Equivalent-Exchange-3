package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;

import java.io.File;

import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.reference.Reference;
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
    	
    	LogHelper.info("Sending custom EMC configuration to player " + ctx.getServerHandler().playerEntity.getCommandSenderName());
    	for(File valueFile : new File(Reference.BASE_CONFIGURATION_FILE_PATH + "emc").listFiles())
    	{
        	PacketHandler.INSTANCE.sendTo(new MessageEMCConfigUpdate(valueFile), ctx.getServerHandler().playerEntity);
    	}
    	
    	return null;
    }
}
