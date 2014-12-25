package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;

import java.io.File;

import net.multiplemonomials.eer.configuration.ReceivedConfigAction;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Message to request a config file from the server
 */
public class MessageRequestConfiguration implements IMessage, IMessageHandler<MessageRequestConfiguration, IMessage>
{
	String _fileName;
	
	ReceivedConfigAction _actionToPerform;

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
        _fileName = fileName;
        _actionToPerform = actionToPerform;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    	_actionToPerform = ReceivedConfigAction.values()[buf.readByte()];
        _fileName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf out)
    {
    	out.writeByte(_actionToPerform.ordinal());
        ByteBufUtils.writeUTF8String(out, _fileName);
    }

    @Override
    public IMessage onMessage(MessageRequestConfiguration message, MessageContext ctx)
    {
    	PacketHandler.INSTANCE.sendTo(new MessageConfigFileUpdateToClient(new File(Reference.BASE_CONFIGURATION_FILE_PATH + _fileName), _actionToPerform), ctx.getServerHandler().playerEntity);
    	
    	return null;
    }
}
