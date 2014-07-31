package com.pahimar.ee3.proxy;

import java.util.HashMap;
import java.util.Map;

import com.pahimar.ee3.handler.CraftingHandler;
import com.pahimar.ee3.handler.EntityConstructedEventHandler;
import com.pahimar.ee3.handler.EntityJoinWorldHandler;
import com.pahimar.ee3.handler.ItemEventHandler;
import com.pahimar.ee3.handler.LivingDeathEventHandler;
import com.pahimar.ee3.handler.PlayerEventHandler;
import com.pahimar.ee3.handler.WorldEventHandler;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.tileentity.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

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
