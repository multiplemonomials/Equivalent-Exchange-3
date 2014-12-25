package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.configuration.ReceivedConfigAction;
import net.multiplemonomials.eer.configuration.SerializableConfiguration;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Sends a configuration file to the server.  Unlike the client version, this requires the user to be an OP.
 * @author Jamie
 *
 */
public class MessageConfigFileUpdateToServer implements IMessage, IMessageHandler<MessageConfigFileUpdateToServer, IMessage>
{
	SerializableConfiguration configuration;
	

    public MessageConfigFileUpdateToServer()
    {
    }

    public MessageConfigFileUpdateToServer(File file, ReceivedConfigAction type)
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
    public IMessage onMessage(MessageConfigFileUpdateToServer message, MessageContext ctx)
    {
    	EntityPlayerMP player = ctx.getServerHandler().playerEntity;
    	//func_152607_e = isPlayerOpped()
    	if(player.mcServer.getConfigurationManager().func_152607_e(player.getGameProfile()))
    	{
    		switch(message.configuration._configType)
        	{
        	case LOAD_AS_COMMON_CONFIG:
        		CommonConfiguration.initAndSave(message.configuration);
        	case SAVE:
        		message.configuration.save();
        	}
            return null;
    	}
    	else
    	{
    		LogHelper.warn("Non-op player " + player.getCommandSenderName() + " tried to edit server EER config files!");
    	}
        return null;
    }
}
