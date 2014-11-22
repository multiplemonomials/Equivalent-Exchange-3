package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;
import net.multiplemonomials.eer.tileentity.TileEntityEE;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Stripped-down packet that just syncs the leftover EMC
 */
public class MessageCondenserEMCUpdateToServer implements IMessage, IMessageHandler<MessageCondenserEMCUpdateToServer, IMessage>
{
    public int x, y, z;
    public double leftoverEMC;

    public MessageCondenserEMCUpdateToServer()
    {
    }

    public MessageCondenserEMCUpdateToServer(TileEntityCondenser tileEntityCondenser)
    {
        this.x = tileEntityCondenser.xCoord;
        this.y = tileEntityCondenser.yCoord;
        this.z = tileEntityCondenser.zCoord;
        this.leftoverEMC = tileEntityCondenser.getStoredEMC();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.leftoverEMC = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeDouble(leftoverEMC);
    }

    @Override
    public IMessage onMessage(MessageCondenserEMCUpdateToServer message, MessageContext ctx)
    {
        TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityEE)
        {
        	TileEntityCondenser tileCondenser = ((TileEntityCondenser) tileEntity);
        	tileCondenser.setStoredEMC(message.leftoverEMC);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageCondenserEMCUpdate - x:%s, y:%s, z:%s, emc:%s", x, y, z, leftoverEMC);
    }
}
