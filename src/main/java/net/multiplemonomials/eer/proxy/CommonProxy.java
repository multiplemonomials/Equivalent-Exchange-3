package net.multiplemonomials.eer.proxy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.handler.CraftingHandler;
import net.multiplemonomials.eer.handler.EntityConstructedEventHandler;
import net.multiplemonomials.eer.handler.EntityJoinWorldHandler;
import net.multiplemonomials.eer.handler.ItemEventHandler;
import net.multiplemonomials.eer.handler.LivingDeathEventHandler;
import net.multiplemonomials.eer.handler.PlayerEventHandler;
import net.multiplemonomials.eer.handler.WorldEventHandler;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChest;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChestLarge;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChestMedium;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChestSmall;
import net.multiplemonomials.eer.tileentity.TileEntityAludel;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;
import net.multiplemonomials.eer.tileentity.TileEntityGlassBell;
import net.multiplemonomials.eer.tileentity.TileEntityTransmutationTablet;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy
{
	/** Used to store IExtendedEntityProperties data temporarily between player death and respawn */
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
    public void registerEventHandlers()
    {
        ItemEventHandler itemEventHandler = new ItemEventHandler();

        FMLCommonHandler.instance().bus().register(itemEventHandler);
        MinecraftForge.EVENT_BUS.register(itemEventHandler);
        MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
        
        FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
        
        MinecraftForge.EVENT_BUS.register(new EntityConstructedEventHandler());
        MinecraftForge.EVENT_BUS.register(new LivingDeathEventHandler());
        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldHandler());
        
        FMLCommonHandler.instance().bus().register(new CraftingHandler());
    }
    
    @Override
    public void initConfiguration(String configPath)
    {
    	CommonConfiguration.init(new File(configPath + "client.properties"));
    }

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityAlchemicalChest.class, "tile." + Names.Blocks.ALCHEMICAL_CHEST);
        GameRegistry.registerTileEntity(TileEntityAlchemicalChestSmall.class, "tile." + Names.Blocks.ALCHEMICAL_CHEST + "Small");
        GameRegistry.registerTileEntity(TileEntityAlchemicalChestMedium.class, "tile." + Names.Blocks.ALCHEMICAL_CHEST + "Medium");
        GameRegistry.registerTileEntity(TileEntityAlchemicalChestLarge.class, "tile." + Names.Blocks.ALCHEMICAL_CHEST + "Large");
        GameRegistry.registerTileEntity(TileEntityAludel.class, "tile." + Names.Blocks.ALUDEL);
        GameRegistry.registerTileEntity(TileEntityCalcinator.class, "tile." + Names.Blocks.CALCINATOR);
        GameRegistry.registerTileEntity(TileEntityGlassBell.class, "tile." + Names.Blocks.GLASS_BELL);
        GameRegistry.registerTileEntity(TileEntityCondenser.class, "tile." + Names.Blocks.CONDENSER);
        GameRegistry.registerTileEntity(TileEntityTransmutationTablet.class, "tile." + Names.Blocks.TRANSMUTATION_TABLET);
    }
    
    /**
    * Adds an entity's custom data to the map for temporary storage
    * @param compound An NBT Tag Compound that stores the IExtendedEntityProperties data only
    */
    public static void storeEntityData(String name, NBTTagCompound compound)
    {
    	extendedEntityData.put(name, compound);
    }

    /**
    * Removes the compound from the map and returns the NBT tag stored for name or null if none exists
    */
    public static NBTTagCompound getEntityData(String name)
    {
    	return extendedEntityData.remove(name);
    }
}
