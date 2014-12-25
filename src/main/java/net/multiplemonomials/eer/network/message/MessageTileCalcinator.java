package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileCalcinator implements IMessage, IMessageHandler<MessageTileCalcinator, IMessage>
{
    public int x, y, z;
    
    public int burnTimeLeftInTicks;              
    public int totalBurnTimeInTicks; 
    public int itemTimeLeftInTicks;
	public int itemTimeTotalInTicks;
    
    public byte orientation, state;
    public String customName, owner;
    
    public NBTTagCompound itemsToOutputTag;

    public MessageTileCalcinator()
    {
    }

    public MessageTileCalcinator(TileEntityCalcinator tileEntityCalcinator)
    {
        this.x = tileEntityCalcinator.xCoord;
        this.y = tileEntityCalcinator.yCoord;
        this.z = tileEntityCalcinator.zCoord;
        
        this.burnTimeLeftInTicks = tileEntityCalcinator.burnTimeLeftInTicks;
        this.totalBurnTimeInTicks = tileEntityCalcinator.totalBurnTimeInTicks;
        this.itemTimeLeftInTicks = tileEntityCalcinator.itemTimeLeftInTicks;
        this.itemTimeTotalInTicks = tileEntityCalcinator.itemTimeTotalInTicks;
        
        this.itemsToOutputTag = tileEntityCalcinator.getItemsToOutputNBTTag();
        
        this.orientation = (byte) tileEntityCalcinator.getOrientation().ordinal();
        this.state = (byte) tileEntityCalcinator.getState();
        this.customName = tileEntityCalcinator.getCustomName();
        this.owner = tileEntityCalcinator.getOwner();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        
        this.burnTimeLeftInTicks = buf.readInt();
        this.totalBurnTimeInTicks = buf.readInt();
        this.itemTimeLeftInTicks = buf.readInt();
        this.itemTimeTotalInTicks = buf.readInt();
        
        this.orientation = buf.readByte();
        this.state = buf.readByte();
        this.customName = ByteBufUtils.readUTF8String(buf);
        this.owner = ByteBufUtils.readUTF8String(buf);
        
        itemsToOutputTag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        
        buf.writeInt(burnTimeLeftInTicks);
        buf.writeInt(totalBurnTimeInTicks);
        buf.writeInt(itemTimeLeftInTicks);
        buf.writeInt(itemTimeTotalInTicks);
        
        buf.writeByte(orientation);
        buf.writeByte(state);
        
        ByteBufUtils.writeUTF8String(buf, customName);
        
        ByteBufUtils.writeUTF8String(buf, owner);
        
        ByteBufUtils.writeTag(buf, itemsToOutputTag);
    }

    @Override
    public IMessage onMessage(MessageTileCalcinator message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityCalcinator)
        {
        	TileEntityCalcinator calcinator = ((TileEntityCalcinator) tileEntity);
        	
        	calcinator.setOrientation(message.orientation);
        	calcinator.setState(message.state);
        	calcinator.setCustomName(message.customName);
        	calcinator.setOwner(message.owner);
        	
        	calcinator.burnTimeLeftInTicks = burnTimeLeftInTicks;
        	calcinator.totalBurnTimeInTicks = totalBurnTimeInTicks;
        	calcinator.itemTimeLeftInTicks = itemTimeLeftInTicks;
        	calcinator.itemTimeTotalInTicks = itemTimeTotalInTicks;

        }

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageTileEntityCalcinator - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s", x, y, z, orientation, state, customName, owner);
    }
}
