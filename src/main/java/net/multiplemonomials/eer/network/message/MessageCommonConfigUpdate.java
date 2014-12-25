package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.minecraftforge.common.config.Configuration;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageCommonConfigUpdate implements IMessage, IMessageHandler<MessageCommonConfigUpdate, IMessage>
{
	File tempFile;
	
    public MessageCommonConfigUpdate()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    	String[] filenameParts = CommonConfiguration.FILENAME.split(".");
    	int fileLen = buf.readInt();
    	try
		{
			tempFile = File.createTempFile(filenameParts[0], filenameParts[1]);
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
        try
		{
        	byte[] configBytes = Files.readAllBytes(Paths.get(Reference.BASE_CONFIGURATION_FILE_PATH + CommonConfiguration.FILENAME));
        	out.writeInt(configBytes.length);
			out.writeBytes(configBytes);
		} 
        catch (IOException e)
		{
			e.printStackTrace();
		}
    }

    @Override
    public IMessage onMessage(MessageCommonConfigUpdate message, MessageContext ctx)
    {
    	CommonConfiguration.init(new Configuration(tempFile));

        return null;
    }
}
