package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.multiplemonomials.eer.handler.ValueFilesHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageEMCConfigUpdate implements IMessage, IMessageHandler<MessageEMCConfigUpdate, IMessage>
{
	File tempFile;
	
	File _fileToSend;
	
	String filename;
	
    public MessageEMCConfigUpdate(File fileToSend)
    {
    	_fileToSend = fileToSend;
    	filename = _fileToSend.getName();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    	filename = ByteBufUtils.readUTF8String(buf);
    	int fileLen = buf.readInt();
    	try
		{
			tempFile = File.createTempFile(filename.substring(0, filename.length() - 5), "emc");
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
    	String filename = _fileToSend.getName();
    	ByteBufUtils.writeUTF8String(out, filename);
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
    public IMessage onMessage(MessageEMCConfigUpdate message, MessageContext ctx)
    {
    	ValueFilesHandler.addValueFileFromServer(message.tempFile);

        return null;
    }
}
