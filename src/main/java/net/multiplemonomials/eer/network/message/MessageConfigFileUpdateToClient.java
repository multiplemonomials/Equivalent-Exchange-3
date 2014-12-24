package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.configuration.ReceivedConfigAction;
import net.multiplemonomials.eer.configuration.SerializableConfiguration;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageConfigFileUpdateToClient implements IMessage, IMessageHandler<MessageConfigFileUpdateToClient, IMessage>
{
	SerializableConfiguration configuration;
	

    public MessageConfigFileUpdateToClient()
    {
    }

    public MessageConfigFileUpdateToClient(File file, ReceivedConfigAction type)
    {
    	if(!file.exists())
    	{
    		LogHelper.error("Attempted to send config file that doesn't exist!");
    	}
    	
    	try
		{
			LogHelper.info("Sending config file " + file.getCanonicalPath());
		}
    	catch(IOException e)
		{
			e.printStackTrace();
			return;
		}
    	
    	
    	configuration = new SerializableConfiguration(file);
    	
    	configuration._configType = type;
        
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        ByteBufInputStream stream = new ByteBufInputStream(buf);
        ObjectInputStream objStream;
		try
		{
			objStream = new ObjectInputStream(stream);
			configuration = ((SerializableConfiguration)objStream.readObject());
			stream.close();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Override
    public void toBytes(ByteBuf out)
    {
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        try
		{
            ObjectOutputStream oout = new ObjectOutputStream(bout);
        	oout.writeObject(configuration);
			oout.flush();
			oout.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public IMessage onMessage(MessageConfigFileUpdateToClient message, MessageContext ctx)
    {
    	switch(configuration._configType)
    	{
    	case LOAD_AS_COMMON_CONFIG:
    		CommonConfiguration.init(configuration);
    	case SAVE:
    		configuration.save();
    	}
        return null;
    }
}
