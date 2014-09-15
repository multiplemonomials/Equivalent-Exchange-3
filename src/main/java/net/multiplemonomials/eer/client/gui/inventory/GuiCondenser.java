package net.multiplemonomials.eer.client.gui.inventory;

import net.multiplemonomials.eer.inventory.ContainerCondenser;
import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiCondenser extends GuiContainer
{
    private TileEntityCondenser tileEntityCondenser;

    public GuiCondenser(InventoryPlayer inventoryPlayer, TileEntityCondenser condenser)
    {
        super(new ContainerCondenser(inventoryPlayer, condenser));
        tileEntityCondenser = condenser;
       
        xSize = 248;
        ySize = 256;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	fontRendererObj.drawString(StatCollector.translateToLocal(tileEntityCondenser.getInventoryName()), 70, 9, 0x404040);
    	
    	String leftoverEMC = String.format("EMC: %.2f", tileEntityCondenser.getStoredEMC());
    	
    	fontRendererObj.drawString(leftoverEMC, 174, 9, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Textures.GUI_CONDENSER);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
