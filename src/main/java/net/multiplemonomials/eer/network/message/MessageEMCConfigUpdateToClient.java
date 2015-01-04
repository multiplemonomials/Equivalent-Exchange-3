package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.multiplemonomials.eer.handler.ValueFilesHandler;
import net.multiplemonomials.eer.util.EmcInitializationHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageEMCConfigUpdateToClient implements IMessage, IMessageHandler<MessageEMCConfigUpdateToClient, IMessage>
{
	File tempFile;
	
	File _fileToSend;
	
	String modid;
	
	//if set, reloads the EMC registry when the packet arrives
	boolean _isFinal;
	
    public MessageEMCConfigUpdateToClient(File fileToSend, boolean isFinal)
    {
    	_fileToSend = fileToSend;
    	String filename = _fileToSend.getName();
    	modid = filename.substring(0, filename.length() - 5);
    	_isFinal = isFinal;
    }
    
    public MessageEMCConfigUpdateToClient()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    	_isFinal = buf.readBoolean();
    	modid = ByteBufUtils.readUTF8String(buf);
    	int fileLen = buf.readInt();
    	try
		{
			tempFile = File.createTempFile(modid, "emc");
	    	tempFile.deleteOnExit();
	    	byte[] configBytes = new byte[fileLen];
	    	buf.readBytes(configBytes);
	    	Files.write(tempFile.toPath(), configBytes);
		} 
    	catch (IOException e)
		{
			e.printStackTrace();
		}
        
    }

    @Override
    public void toBytes(ByteBuf out)
    {
    	out.writeBoolean(_isFinal);
    	ByteBufUtils.writeUTF8String(out, modid);
        try
		{
        	byte[] configBytes = Files.readAllBytes(Paths.get(_fileToSend.getPath()));
        	out.writeInt(configBytes.length);
			out.writeBytes(configBytes);
		} 
        catch (IOException e)
		{
			e.printStackTrace();
		}
    }

    @Override
    public IMessage onMessage(MessageEMCConfigUpdateToClient message, MessageContext ctx)
    {
    	ValueFilesHandler.getClientHandler().addValueFileFromServer(message.modid, message.tempFile);

    	if(message._isFinal)
    	{
        	EmcInitializationHelper.initEmcRegistry();
    	}
    	
        return null;
    }
}
