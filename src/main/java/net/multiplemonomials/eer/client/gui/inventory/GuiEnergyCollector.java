package net.multiplemonomials.eer.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.multiplemonomials.eer.inventory.ContainerEnergyCollector;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.tileentity.TileEntityEnergyCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergyCollector extends GuiContainer
{
    private TileEntityEnergyCollector tileEntityCollector;
    
    //height of the squiggly image that shows the current EMC in px
    private static final int EMC_GAUGE_IMAGE_HEIGHT = 44;

    public GuiEnergyCollector(InventoryPlayer inventoryPlayer, TileEntityEnergyCollector tileEntityCollector)
    {
        super(new ContainerEnergyCollector(inventoryPlayer, tileEntityCollector));
        this.tileEntityCollector = tileEntityCollector;
        xSize = 176;
        ySize = 176;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        String containerName = StatCollector.translateToLocal(tileEntityCollector.getInventoryName());
        fontRendererObj.drawString(containerName, xSize / 2 - fontRendererObj.getStringWidth(containerName) / 2, 6, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal(Names.Containers.VANILLA_INVENTORY), 8, ySize - 93, 0x404040);
        
        fontRendererObj.drawString(String.format("Stored EMC: %.1f", tileEntityCollector.getLeftoverEMC()), 15, 50, 0x404040);
        fontRendererObj.drawString("Light:", 17, 25, 0x404040);
        
        fontRendererObj.drawString(String.format("%-3d", tileEntityCollector.getLightLevel()), 50, 25, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_ENERGY_COLLECTOR);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        
        if (tileEntityCollector.state == 1)
        {
                int emcStoredScaled = MathHelper.ceiling_double_int((tileEntityCollector.getLeftoverEMC() / tileEntityCollector.getMaxStorableEMC()) * EMC_GAUGE_IMAGE_HEIGHT) ;
                drawTexturedModalRect(xStart + 120, yStart + 28, 176, 0, 14, emcStoredScaled);
        }

    }
}
