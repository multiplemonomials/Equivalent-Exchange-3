package net.multiplemonomials.eer.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.multiplemonomials.eer.inventory.ContainerCalcinator;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCalcinator extends GuiContainer
{
    private TileEntityCalcinator tileEntityCalcinator;

    public GuiCalcinator(InventoryPlayer inventoryPlayer, TileEntityCalcinator tileEntityCalcinator)
    {
        super(new ContainerCalcinator(inventoryPlayer, tileEntityCalcinator));
        this.tileEntityCalcinator = tileEntityCalcinator;
        xSize = 176;
        ySize = 176;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        String containerName = StatCollector.translateToLocal(tileEntityCalcinator.getInventoryName());
        fontRendererObj.drawString(containerName, xSize / 2 - fontRendererObj.getStringWidth(containerName) / 2, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal(Names.Containers.VANILLA_INVENTORY), 8, ySize - 93, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_CALCINATOR);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        
        if (tileEntityCalcinator.state == 1)
        {
                int burn = tileEntityCalcinator.getBurnTimeRemainingScaled(13);
                drawTexturedModalRect(xStart + 57, yStart + 46 + 14 - burn, 176, 14 - burn, 12, burn);
                
                int cookProgress = tileEntityCalcinator.getCookTimeRemainingScaled(22);
                drawTexturedModalRect(xStart + 84, yStart + 35, 177, 14, cookProgress, 16);
        }

    }
}
