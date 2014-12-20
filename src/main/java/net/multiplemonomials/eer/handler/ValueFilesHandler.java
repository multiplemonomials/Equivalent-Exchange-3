package net.multiplemonomials.eer.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.multiplemonomials.eer.exchange.EnergyValue;
import net.multiplemonomials.eer.exchange.WrappedStack;
import net.multiplemonomials.eer.util.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

public class ValueFilesHandler
{
    public static final String CATEGORY_EMC_VALUES = "emcvalues";
    private static File valueFileFolder;

    private static File getValueFileFolder()
    {
        if (valueFileFolder == null)
        {
            valueFileFolder = new File("config" + File.separator + "eer" + File.separator + "emc");
            if (!valueFileFolder.exists())
            {
                valueFileFolder.mkdirs();
            }
        }
        return valueFileFolder;
    }

    private static File getValueFile(String modid)
    {
        return new File(getValueFileFolder().getPath() + File.separator + modid + ".emc");
    }

    public static void addFileValue(String modid, ItemStack itemStack, EnergyValue emcValue)
    {
        File emcFile = getValueFile(modid);
        Configuration emcConfiguration = new Configuration(emcFile);

        try
        {
            emcConfiguration.load();

            UniqueIdentifier itemID = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());
            
            emcConfiguration.get(CATEGORY_EMC_VALUES, itemID.modId + "|" + itemID.name + "|" + itemStack.getItemDamage(), emcValue.getValue()).set(emcValue.getValue());
        }
        catch (Exception e)
        {
            LogHelper.error(String.format("Unable to save EMC Value(%s) for (%s)!", emcValue.getValue(), itemStack.getDisplayName()));
        }
        finally
        {
            emcConfiguration.save();
        }
    }

    public static EnergyValue getFileValue(String modid, ItemStack itemStack)
    {
        File emcFile = getValueFile(modid);
        Configuration emcConfiguration = new Configuration(emcFile);

        EnergyValue emcValue = null;

        try
        {
            emcConfiguration.load();
            
            UniqueIdentifier itemID = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());

            Float value = (float)emcConfiguration.get(CATEGORY_EMC_VALUES, itemID.modId + "|" + itemID.name + "|" + itemStack.getItemDamage(), 0.0F).getDouble(0.0F);
            emcValue = new EnergyValue(value);
        }
        catch (Exception e)
        {
            LogHelper.error(String.format("Unable to load EMC Value for (%s)!",  itemStack.getDisplayName()));
        }
        finally
        {
            emcConfiguration.save();
        }
        return emcValue;
    }

    public static Map<WrappedStack, EnergyValue> getFileValues(File emcFile)
    {
        Map<WrappedStack, EnergyValue> valueMap = new HashMap<WrappedStack, EnergyValue>();

        try
        {
            Configuration emcConfiguration = new Configuration(emcFile);

            ConfigCategory category = emcConfiguration.getCategory(CATEGORY_EMC_VALUES);

            for(Map.Entry<String, Property> entry : category.entrySet())
            {
                ItemStack stack = null;
                String[] stackStrings = entry.getKey().split("\\|");
                if (stackStrings.length >= 3)
                {
                    Item item = GameRegistry.findItem(stackStrings[0], stackStrings[1]);
                    int metadata = Integer.parseInt(stackStrings[2]);
                    
                    if(item == null)
                    {
                    	LogHelper.error("Unable to load item with mod ID " + stackStrings[0] + " and unlocalized name " + stackStrings[1]);
                    }

                    stack = new ItemStack(item, 1, metadata);
                }

                float value = (float)entry.getValue().getDouble(0.0F);
                EnergyValue emcValue = new EnergyValue(value);

                if (stack != null && emcValue.getValue() != 0.0F)
                {
                    valueMap.put(new WrappedStack(stack), emcValue);
                }
            }
        }
        catch (Exception e)
        {
            LogHelper.error("Unable to load EMC Value file!");
        }
        return valueMap;
    }

    public static Map<WrappedStack, EnergyValue> getFileValues(String modid)
    {
        File emcFile = getValueFile(modid);
        return getFileValues(getValueFile(modid));
    }

    public static Map<WrappedStack, EnergyValue> getFileValues()
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
