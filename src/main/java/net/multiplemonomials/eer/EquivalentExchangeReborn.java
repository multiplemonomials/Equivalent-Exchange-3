package net.multiplemonomials.eer;

import java.io.File;

import net.multiplemonomials.eer.exchange.EnergyValuesDefault;
import net.multiplemonomials.eer.handler.CraftingHandler;
import net.multiplemonomials.eer.handler.FuelHandler;
import net.multiplemonomials.eer.handler.GuiHandler;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.proxy.IProxy;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.LogHelper;
import net.multiplemonomials.eer.util.PowerItemUtils;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class EquivalentExchangeReborn
{
    @Instance(Reference.MOD_ID)
    public static EquivalentExchangeReborn instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;
    
    public static EnergyValuesDefault emcDefaultValues = null;


    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event)
    {

    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Phase 1 Loading Started");

        Reference.BASE_CONFIGURATION_FILE_PATH = event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID.toLowerCase() + File.separator;
        
        proxy.initConfiguration(Reference.BASE_CONFIGURATION_FILE_PATH);

    	LogHelper.info("Setting up network stuff...");
        PacketHandler.init();
        
    	LogHelper.info("Setting up keybinds...");
        proxy.registerKeybindings();

    	LogHelper.info("Loading items...");
    	PowerItemUtils.initMaterials();
    	ModItems.init();

    	LogHelper.info("Loading blocks...");
    	ModBlocks.init();
    	
        LogHelper.info("Phase 1 Loading Complete!");

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Phase 2 Loading Started");
    	
        // Register the GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        LogHelper.info("Loading tile entities...");
        proxy.registerTileEntities();

        // Initialize custom rendering and pre-load textures (Client only)
        proxy.initRenderingAndTextures();

        LogHelper.info("Registering event handlers...");
        proxy.registerEventHandlers();

        LogHelper.info("Registering crafting recipies...");
        CraftingHandler.init();

        LogHelper.info("Registering fuels...");
        GameRegistry.registerFuelHandler(new FuelHandler());
        
        LogHelper.info("Assigining EMC Values ...");
        emcDefaultValues = new EnergyValuesDefault();
        emcDefaultValues.init();
        
        LogHelper.info("Done loading!");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }

    @EventHandler
    public void handleIMCMessages(IMCEvent event)
    {
    	
    }
}
