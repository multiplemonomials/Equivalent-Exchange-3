package net.multiplemonomials.eer.handler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.exchange.WrappedStack;

/**
 * ValueFilesHandler that operates on temp files fed in by the server
 * @author Jamie
 *
 */
public class ValueFilesHandlerClient extends ValueFilesHandler
{   
    private HashMap<String, File> serverValueFiles = new HashMap<String, File>();

    public File getValueFile(String modid)
    {
    	//if we got a file for this mod from the server, return that.
    	if(serverValueFiles.containsKey(modid))
    	{
    		return serverValueFiles.get(modid);
    	}
    	
		File tempFile = null;
		try
		{
			tempFile = File.createTempFile(modid, "emc");
	    	tempFile.deleteOnExit();
	    	serverValueFiles.put(modid, tempFile);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
    	return tempFile;
    }
    
    
    public void addValueFileFromServer(String modid, File valueFile)
    {
    	 serverValueFiles.put(modid, valueFile);
    }
    
    public Map<WrappedStack, EnergyValue> getAllFileValues()
    {
    	Map<WrappedStack, EnergyValue> valueMap = new HashMap<WrappedStack, EnergyValue>();

        for (File file : serverValueFiles.values())
        {
            valueMap.putAll(getFileValues(file));
        }
    	
    	return valueMap;
    }
}
