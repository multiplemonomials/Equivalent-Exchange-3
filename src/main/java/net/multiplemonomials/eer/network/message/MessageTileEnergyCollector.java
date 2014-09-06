package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.multiplemonomials.eer.tileentity.TileEntityEnergyCollector;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEnergyCollector implements IMessage, IMessageHandler<MessageTileEnergyCollector, IMessage>
{
    public int x, y, z;
    public byte orientation, state;
    public String customName;
    public double leftoverEMC;

    public MessageTileEnergyCollector()
    {
    }

    public MessageTileEnergyCollector(TileEntityEnergyCollector tileEntityCollector)
    {
        this.x = tileEntityCollector.xCoord;
        this.y = tileEntityCollector.yCoord;
        this.z = tileEntityCollector.zCoord;
        this.orientation = (byte) tileEntityCollector.getOrientation().ordinal();
        this.state = (byte) tileEntityCollector.getState();
        this.customName = tileEntityCollector.getCustomName();
        this.leftoverEMC = tileEntityCollector.getLeftoverEMC();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.orientation = buf.readByte();
        this.state = buf.readByte();
        int customNameLength = buf.readInt();
        this.customName = new String(buf.readBytes(customNameLength).array());
        this.leftoverEMC = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(orientation);
        buf.writeByte(state);
        buf.writeInt(customName.length());
        buf.writeBytes(customName.getBytes());
        buf.writeDouble(leftoverEMC);
    }

    @Override
    public IMessage onMessage(MessageTileEnergyCollector message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityEnergyCollector)
        {
        	TileEntityEnergyCollector tileCollector = ((TileEntityEnergyCollector) tileEntity);
        	tileCollector.setOrientation(message.orientation);
        	tileCollector.setState(message.state);
        	tileCollector.setCustomName(message.customName);
        	tileCollector.setLeftoverEMC(message.leftoverEMC);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageTileEnergyCollector - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, leftoverEMC:%d", x, y, z, orientation, state, customName, leftoverEMC);
    }
}
