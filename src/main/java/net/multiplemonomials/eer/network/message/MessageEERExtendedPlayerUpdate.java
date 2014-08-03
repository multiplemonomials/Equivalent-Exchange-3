package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

import net.multiplemonomials.eer.data.EERExtendedPlayer;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Message that sets the given EEREExtebdedPlayer as the player data for the client it's sent to
 *
 */
public class MessageEERExtendedPlayerUpdate implements IMessage, IMessageHandler<MessageEERExtendedPlayerUpdate, IMessage>
{
	NBTTagCompound _playerDataCompound;
	
    public MessageEERExtendedPlayerUpdate()
    {
    }

    public MessageEERExtendedPlayerUpdate(EERExtendedPlayer playerData)
    {
    	_playerDataCompound = new NBTTagCompound();
        playerData.saveNBTData(_playerDataCompound);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    	 _playerDataCompound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    	ByteBufUtils.writeTag(buf, _playerDataCompound);
    }

    @Override
    public IMessage onMessage(MessageEERExtendedPlayerUpdate message, MessageContext ctx)
    {
    	EERExtendedPlayer.get(Minecraft.getMinecraft().thePlayer).loadNBTData(_playerDataCompound);
    	
    	return null;
    }

    @Override
    public String toString()
    {
        return "EERExtendedPlayerUpdate";
    }
}
