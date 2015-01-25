package net.multiplemonomials.eer.network.message;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import net.minecraft.entity.player.EntityPlayerMP;
import net.multiplemonomials.eer.handler.ValueFilesHandler;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.EmcInitializationHelper;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Sends a configuration file to the server.  Unlike the client version, this requires the user to be an OP.
 * @author Jamie
 *
 */
public class MessageEMCConfigUpdateToServer implements IMessage, IMessageHandler<MessageEMCConfigUpdateToServer, IMessage>
{
	File _file;
	
	String _modid;
	
	boolean _reloadRegistry;
	
    public MessageEMCConfigUpdateToServer()
    {
    }

    public MessageEMCConfigUpdateToServer(File file, String modid, boolean reloadRegistry)
    {
    	if(!file.exists())
    	{
    		throw new RuntimeException("Attempted to send config file that doesn't exist!");
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
    	
    	_file = file;
    	
    	_modid = modid;
    	
    	_reloadRegistry = reloadRegistry;
        
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    	_reloadRegistry = buf.readBoolean();
    	_modid = ByteBufUtils.readUTF8String(buf);
    	int fileLen = buf.readInt();
    	try
		{
			_file = File.createTempFile(_modid, "emc");
			_file.deleteOnExit();
	    	byte[] configBytes = new byte[fileLen];
	    	buf.readBytes(configBytes);
	    	Files.write(_file.toPath(), configBytes);
		} 
    	catch (IOException e)
		{
			e.printStackTrace();
		}
        
    }

    @Override
    public void toBytes(ByteBuf out)
    {
    	out.writeBoolean(_reloadRegistry);
    	ByteBufUtils.writeUTF8String(out, _modid);
        try
		{
        	byte[] configBytes = Files.readAllBytes(Paths.get(_file.getPath()));
        	out.writeInt(configBytes.length);
			out.writeBytes(configBytes);
		} 
        catch (IOException e)
		{
			e.printStackTrace();
		}
    }

    @Override
    public IMessage onMessage(MessageEMCConfigUpdateToServer message, MessageContext ctx)
    {
    	EntityPlayerMP player = ctx.getServerHandler().playerEntity;
    	//func_152607_e = isPlayerOpped()
    	if(player.mcServer.getConfigurationManager().func_152607_e(player.getGameProfile()))
    	{
    		File oldVersionFile = ValueFilesHandler.getServerHandler().getValueFile(message._modid);
    		try
			{
				Files.copy(Paths.get(message._file.getAbsolutePath()), Paths.get(oldVersionFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			} 
    		catch (IOException e)
			{
    			LogHelper.error("Failed to load client EMC configuration: " + e.getClass().getSimpleName());
				e.printStackTrace();
			}
    		
    		if(message._reloadRegistry)
    		{
    			EmcInitializationHelper.initEmcRegistry();
    			
    			File[] emcConfigFiles = new File(Reference.BASE_CONFIGURATION_FILE_PATH + "emc").listFiles();
    	    	if(emcConfigFiles != null)
    	    	{
	    	    	for(int index = emcConfigFiles.length - 1; index >= 0; --index)
	    	    	{
	    	        	PacketHandler.INSTANCE.sendToAll(new MessageEMCConfigUpdateToClient(emcConfigFiles[index], index == 0));
	    	    	}
    	    	}
    		}
    	}
    	else
    	{
    		LogHelper.warn("Non-op player " + player.getCommandSenderName() + " tried to edit server EER config files!");
    	}
        return null;
    }
}
