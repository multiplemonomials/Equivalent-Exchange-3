package com.pahimar.ee3.client.gui.inventory;

import com.pahimar.ee3.inventory.ContainerTransmutationTablet;
import com.pahimar.ee3.reference.Textures;
import com.pahimar.ee3.tileentity.TileEntityTransmutationTablet;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiTransmutationTablet extends GuiContainer
{
    private TileEntityTransmutationTablet tileEntityTransmutationTablet;

    public GuiTransmutationTablet(InventoryPlayer inventoryPlayer, TileEntityTransmutationTablet tileEntityTransmutationTablet)
    {
        super(new ContainerTransmutationTablet(inventoryPlayer, tileEntityTransmutationTablet));
        this.tileEntityTransmutationTablet = tileEntityTransmutationTablet;
       
        xSize = 201;
        ySize = 223;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	fontRendererObj.drawString(StatCollector.translateToLocal(tileEntityTransmutationTablet.getInventoryName()), 75, 8, 0x404040);
    	
    	fontRendererObj.drawString(String.format("EMC: %d", tileEntityTransmutationTablet.leftoverEMC), 12, 62, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Textures.GUI_TRANSMUTATION_TABLET);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
