package net.multiplemonomials.eer.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.multiplemonomials.eer.inventory.ContainerCondenser;
import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;

import org.lwjgl.opengl.GL11;

public class GuiCondenser extends GuiContainer
{
    private TileEntityCondenser tileEntityCondenser;
    
    int xStart;
    int yStart;

    public GuiCondenser(InventoryPlayer inventoryPlayer, TileEntityCondenser condenser)
    {
        super(new ContainerCondenser(inventoryPlayer, condenser));
        tileEntityCondenser = condenser;
       
        xSize = 256;
        ySize = 256;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	
    	String leftoverEMC = String.format("%.2f", tileEntityCondenser.getStoredEMC());
    	
    	fontRendererObj.drawString(leftoverEMC, 196, 9, 0x404040);
    	
    	int progress = MathHelper.ceiling_double_int((tileEntityCondenser.getStoredEMC() / tileEntityCondenser.getTargetItemEMC()) * 100);
    	
    	this.mc.getTextureManager().bindTexture(Textures.GUI_CONDENSER_BAR);
    	
    	drawTexturedModalRect(84, 6, 0, 0, progress, 15);
    	
        this.mc.getTextureManager().bindTexture(Textures.GUI_CONDENSER);
    }
    
    public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel){
		Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0,1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1,0);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        xStart = (width - xSize) / 2;
        yStart = (height - 256) / 2;

        this.mc.getTextureManager().bindTexture(Textures.GUI_CONDENSER);

        drawTexturedQuadFit(xStart, yStart, xSize, ySize, zLevel);
    }
}
