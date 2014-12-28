package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.multiplemonomials.eer.util.EmcInitializationHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Message to tell the client to reload its EMC registry
 */
public class MessageReloadEnergyRegistry implements IMessage, IMessageHandler<MessageReloadEnergyRegistry, IMessage>
{

    public MessageReloadEnergyRegistry()
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
    public IMessage onMessage(MessageReloadEnergyRegistry message, MessageContext ctx)
    {
    	EmcInitializationHelper.initEmcRegistry();
    	return null;
    }
}
