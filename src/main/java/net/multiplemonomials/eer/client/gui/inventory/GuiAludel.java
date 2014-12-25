package net.multiplemonomials.eer.client.gui.inventory;

import net.multiplemonomials.eer.inventory.ContainerAludel;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.tileentity.TileEntityAludel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAludel extends GuiContainer
{
    private TileEntityAludel tileEntityAludel;

    public GuiAludel(InventoryPlayer inventoryPlayer, TileEntityAludel tileEntityAludel)
    {
        super(new ContainerAludel(inventoryPlayer, tileEntityAludel));
        this.tileEntityAludel = tileEntityAludel;
        xSize = 176;
        ySize = 188;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        String containerName = StatCollector.translateToLocal(tileEntityAludel.getInventoryName());
        fontRendererObj.drawString(containerName, xSize / 2 - fontRendererObj.getStringWidth(containerName) / 2, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal(Names.Containers.VANILLA_INVENTORY), 8, ySize - 93, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_ALUDEL);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

    }
}
