package net.multiplemonomials.eer.api.stack;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.multiplemonomials.eer.reference.Compare;
import net.multiplemonomials.eer.util.LogHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OreStack implements Comparable<OreStack>
{
    // Gson serializer for serializing to/deserializing from json
    private static final Gson gsonSerializer = new Gson();

    public String oreName;
    public int stackSize;
    public static Comparator<OreStack> comparator = new Comparator<OreStack>()
    {

        @Override
        public int compare(OreStack oreStack1, OreStack oreStack2)
        {
        	try
        	{
        		 if (oreStack1 != null)
                 {
                     if (oreStack2 != null)
                     {
                         if (oreStack1.oreName.equalsIgnoreCase(oreStack2.oreName))
                         {
                             return oreStack1.stackSize - oreStack2.stackSize;
                         }
                         else
                         {
                             return oreStack1.oreName.compareToIgnoreCase(oreStack2.oreName);
                         }
                     }
                     else
                     {
                         return Compare.LESSER_THAN;
                     }
                 }
                 else
                 {
                     if (oreStack2 != null)
                     {
                         return Compare.GREATER_THAN;
                     }
                     else
                     {
                         return Compare.EQUALS;
                     }
                 }
        	}
        	catch(NullPointerException error)
        	{
        		LogHelper.error("Null OreStack.oreName!");
        		return Compare.GREATER_THAN;
        	}
           
        }
    };

    public OreStack(String oreName, int stackSize)
    {
        this.oreName = oreName;
        this.stackSize = stackSize;
    }

    public OreStack(String oreName)
    {
        this(oreName, 1);
    }

    public OreStack(ItemStack itemStack)
    {
        if (itemStack != null && OreDictionary.getOreIDs(itemStack).length > 0)
        {
            this.oreName = OreDictionary.getOreName(OreDictionary.getOreIDs(itemStack)[0]); // TODO Likely not ideal, revisit
            
            if(oreName == null)
            {
            	LogHelper.error("Error finding ore name for ore with IDs " + Arrays.toString(OreDictionary.getOreIDs(itemStack)) + " and unlocalized name \"" + itemStack.getUnlocalizedName() + "\"");
            }
            
            this.stackSize = itemStack.stackSize;
        }
    }

    public static boolean compareOreNames(OreStack oreStack1, OreStack oreStack2)
    {
        if (oreStack1 != null && oreStack2 != null)
        {
            if ((oreStack1.oreName != null) && (oreStack2.oreName != null))
            {
                return oreStack1.oreName.equalsIgnoreCase(oreStack2.oreName);
            }
        }

        return false;
    }

    /**
     * Deserializes a OreStack object from the given serialized json String
     *
     * @param jsonOreStack
     *         Json encoded String representing a OreStack object
     *
     * @return The OreStack that was encoded as json, or null if a valid OreStack could not be decoded from given String
     */
    public static OreStack createFromJson(String jsonOreStack)
    {
        try
        {
            return gsonSerializer.fromJson(jsonOreStack, OreStack.class);
        }
        catch (JsonSyntaxException exception)
        {
            LogHelper.warn(exception.getMessage());
        }
        catch (JsonParseException exception)
        {
            LogHelper.warn(exception.getMessage());
        }

        return null;
    }

    public static OreStack getOreStackFromList(Object... objects)
    {
        return getOreStackFromList(Arrays.asList(objects));
    }

    public static OreStack getOreStackFromList(List<?> objectList)
    {
        for (Object listElement : objectList)
        {
            if (listElement instanceof ItemStack)
            {
                ItemStack stack = (ItemStack) listElement;

                if (OreDictionary.getOreIDs(stack).length != 0)
                {
                    return new OreStack(stack);
                }
            }
        }

        return null;
    }

    public static int compare(OreStack oreStack1, OreStack oreStack2)
    {
        return comparator.compare(oreStack1, oreStack2);
    }

    @Override
    public String toString()
    {
        return String.format("%sxoreStack.%s", stackSize, oreName);
    }

    @Override
    public boolean equals(Object object)
    {
        return object instanceof OreStack && (comparator.compare(this, (OreStack) object) == Compare.EQUALS);
    }

    @Override
    public int compareTo(OreStack oreStack)
    {
        return comparator.compare(this, oreStack);
    }

    /**
     * Returns this OreStack as a json serialized String
     *
     * @return Json serialized String of this OreStack
     */
    public String toJson()
    {
        return gsonSerializer.toJson(this);
    }
}
