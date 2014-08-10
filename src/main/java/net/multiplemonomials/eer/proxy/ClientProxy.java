package net.multiplemonomials.eer.proxy;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.multiplemonomials.eer.client.handler.ItemTooltipEventHandler;
import net.multiplemonomials.eer.client.handler.KeyInputEventHandler;
import net.multiplemonomials.eer.client.renderer.item.ItemAlchemicalChestRenderer;
import net.multiplemonomials.eer.client.renderer.item.ItemAludelRenderer;
import net.multiplemonomials.eer.client.renderer.item.ItemCalcinatorRenderer;
import net.multiplemonomials.eer.client.renderer.item.ItemCondenserRenderer;
import net.multiplemonomials.eer.client.renderer.item.ItemGlassBellRenderer;
import net.multiplemonomials.eer.client.renderer.item.ItemResearchStationRenderer;
import net.multiplemonomials.eer.client.renderer.tileentity.TileEntityAlchemicalChestRenderer;
import net.multiplemonomials.eer.client.renderer.tileentity.TileEntityAludelRenderer;
import net.multiplemonomials.eer.client.renderer.tileentity.TileEntityCalcinatorRenderer;
import net.multiplemonomials.eer.client.renderer.tileentity.TileEntityCondenserRenderer;
import net.multiplemonomials.eer.client.renderer.tileentity.TileEntityGlassBellRenderer;
import net.multiplemonomials.eer.client.renderer.tileentity.TileEntityResearchStationRenderer;
import net.multiplemonomials.eer.client.settings.Keybindings;
import net.multiplemonomials.eer.configuration.ClientConfiguration;
import net.multiplemonomials.eer.handler.ButtonHandler;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.reference.RenderIds;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChest;
import net.multiplemonomials.eer.tileentity.TileEntityAludel;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;
import net.multiplemonomials.eer.tileentity.TileEntityGlassBell;
import net.multiplemonomials.eer.tileentity.TileEntityResearchStation;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
        MinecraftForge.EVENT_BUS.register(new ItemTooltipEventHandler());
    }

    @Override
    public void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(Keybindings.charge);
        ClientRegistry.registerKeyBinding(Keybindings.extra);
        ClientRegistry.registerKeyBinding(Keybindings.release);
        ClientRegistry.registerKeyBinding(Keybindings.toggle);
    }

    @Override
    public void initRenderingAndTextures()
    {
        RenderIds.calcinator = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.aludel = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.alchemicalChest = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.glassBell = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.researchStation = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.condenser = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.alchemicalChest), new ItemAlchemicalChestRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.aludel), new ItemAludelRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.calcinator), new ItemCalcinatorRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.glassBell), new ItemGlassBellRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.researchStation), new ItemResearchStationRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.condenser), new ItemCondenserRenderer());
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchemicalChest.class, new TileEntityAlchemicalChestRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalcinator.class, new TileEntityCalcinatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAludel.class, new TileEntityAludelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlassBell.class, new TileEntityGlassBellRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResearchStation.class, new TileEntityResearchStationRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCondenser.class, new TileEntityCondenserRenderer());

        MinecraftForge.EVENT_BUS.register(new ButtonHandler());

        
    }
    
    @Override
    public void initConfiguration(String configPath)
    {
    	super.initConfiguration(configPath);
    	ClientConfiguration.init(new File(configPath + "common.properties"));
    }
}
