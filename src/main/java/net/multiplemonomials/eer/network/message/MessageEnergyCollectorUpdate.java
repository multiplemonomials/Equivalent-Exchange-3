package net.multiplemonomials.eer.network.message;

import net.multiplemonomials.eer.tileentity.TileEntityEnergyCollector;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * For some strange reason, World.getBlockLightLevel() doesn't work on clients, so we ended up with this workaround
 * @author Jamie
 *
 */
public class MessageEnergyCollectorUpdate implements IMessage, IMessageHandler<MessageEnergyCollectorUpdate, IMessage>
{
    public int x, y, z;
    public byte newLightLevel;
    public double storedEMC;

    public MessageEnergyCollectorUpdate()
    {
    }

    public MessageEnergyCollectorUpdate(TileEntityEnergyCollector tileEntityEnergyCollector)
    {
        this.x = tileEntityEnergyCollector.xCoord;
        this.y = tileEntityEnergyCollector.yCoord;
        this.z = tileEntityEnergyCollector.zCoord;
        this.newLightLevel = (byte) tileEntityEnergyCollector.getLightLevel();
        this.storedEMC = tileEntityEnergyCollector.getLeftoverEMC();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.newLightLevel = buf.readByte();
        this.storedEMC = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(newLightLevel);
        buf.writeDouble(storedEMC);
    }

    @Override
    public IMessage onMessage(MessageEnergyCollectorUpdate message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityEnergyCollector)
        {
        	TileEntityEnergyCollector energyCollector = (TileEntityEnergyCollector) tileEntity;
        	energyCollector.setLeftoverEMC(message.storedEMC);
        	energyCollector.setLightLevel(message.newLightLevel);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageEnergyCollectorUpdate - x:%d, y:%d, z:%d, stored emc:%.4f, new light level:%d, customName:%s, owner:%s", x, y, z, storedEMC, newLightLevel);
    }
}
