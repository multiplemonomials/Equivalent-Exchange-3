package net.multiplemonomials.eer.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.exchange.WrappedStack;
import net.multiplemonomials.eer.reference.Reference;

/**
 * ValueFilesHandler that stores its values in files in the config directory
 * @author Jamie
 *
 */
public class ValueFilesHandlerServer extends ValueFilesHandler
{
    private static File valueFileFolder;

    private static File getValueFileFolder()
    {
        if (valueFileFolder == null)
        {
            valueFileFolder = new File(Reference.BASE_CONFIGURATION_FILE_PATH + "emc");
            if (!valueFileFolder.exists())
            {
                valueFileFolder.mkdirs();
            }
        }
        return valueFileFolder;
    }

    public File getValueFile(String modid)
    {
        return new File(getValueFileFolder().getPath() + File.separator + modid + ".emc");
    }
    
    public Map<WrappedStack, EnergyValue> getAllFileValues()
    {
    	Map<WrappedStack, EnergyValue> valueMap = new HashMap<WrappedStack, EnergyValue>();


        for (File file : getValueFileFolder().listFiles())
        {
            if (file.getName().endsWith(".emc"))
            {
                valueMap.putAll(getFileValues(file));
            }
        }
    	
    	return valueMap;
    }
}
