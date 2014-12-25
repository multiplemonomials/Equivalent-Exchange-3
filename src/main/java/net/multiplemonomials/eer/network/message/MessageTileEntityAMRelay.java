package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.multiplemonomials.eer.tileentity.TileEntityAMRelay;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEntityAMRelay implements IMessage, IMessageHandler<MessageTileEntityAMRelay, IMessage>
{
    public int x, y, z;
    public byte orientation, state;
    public String customName;
    public double leftoverEMC;

    public MessageTileEntityAMRelay()
    {
    }

    public MessageTileEntityAMRelay(TileEntityAMRelay tileEntityRelay)
    {
        this.x = tileEntityRelay.xCoord;
        this.y = tileEntityRelay.yCoord;
        this.z = tileEntityRelay.zCoord;
        this.orientation = (byte) tileEntityRelay.getOrientation().ordinal();
        this.state = (byte) tileEntityRelay.getState();
        this.customName = tileEntityRelay.getCustomName();
        this.leftoverEMC = tileEntityRelay.getLeftoverEMC();
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
    public IMessage onMessage(MessageTileEntityAMRelay message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityAMRelay)
        {
        	TileEntityAMRelay tileRelay = ((TileEntityAMRelay) tileEntity);
        	tileRelay.setOrientation(message.orientation);
        	tileRelay.setState(message.state);
        	tileRelay.setCustomName(message.customName);
        	tileRelay.setLeftoverEMC(message.leftoverEMC);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageTileEnergyCollector - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, leftoverEMC:%d", x, y, z, orientation, state, customName, leftoverEMC);
    }
}
