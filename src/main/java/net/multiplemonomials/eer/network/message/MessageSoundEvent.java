package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSoundEvent implements IMessage, IMessageHandler<MessageSoundEvent, IMessage>
{
    public String playerName;
    public String soundName;
    public double x, y, z;
    public float volume, pitch;
	
    public MessageSoundEvent(String playerName, String soundName, double x, double y, double z, float volume, float pitch)
    {
        this.playerName = playerName;
        this.soundName = soundName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    public MessageSoundEvent()
    {
    	
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerName = ByteBufUtils.readUTF8String(buf);
        soundName = ByteBufUtils.readUTF8String(buf);
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        volume = buf.readFloat();
        pitch = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf out)
    {
        ByteBufUtils.writeUTF8String(out, playerName);
        ByteBufUtils.writeUTF8String(out, soundName);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeFloat(volume);
        out.writeFloat(pitch);
    }

    @Override
    public IMessage onMessage(MessageSoundEvent message, MessageContext ctx)
    {
        //FMLClientHandler.instance().getClient().sndManager.playSound(soundName, (float) x, (float) y, (float) z, volume, pitch);
        
        return null;
    }
}
