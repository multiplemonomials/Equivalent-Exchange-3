package com.pahimar.ee3.client.gui.inventory;

import com.pahimar.ee3.inventory.ContainerTransmutationTablet;
import com.pahimar.ee3.reference.Textures;
import com.pahimar.ee3.tileentity.TileEntityTransmutationTablet;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiTransmutationTablet extends GuiContainer
{
    private TileEntityTransmutationTablet tileEntityTransmutationTablet;

    int xStart;
    
    int yStart;
    
	public GuiTransmutationTablet(InventoryPlayer inventoryPlayer, TileEntityTransmutationTablet tileEntityTransmutationTablet)
    {
        super(new ContainerTransmutationTablet(inventoryPlayer, tileEntityTransmutationTablet));
        this.tileEntityTransmutationTablet = tileEntityTransmutationTablet;
       
        xSize = 201;
        ySize = 223;
        
        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void initGui()
    {
    	super.initGui();
    	
    	buttonList.add(new GuiButton(0, guiLeft + 139, guiTop + 115, 55, 20, "Next"));
    	
    	buttonList.add(new GuiButton(0, guiLeft + 6, guiTop + 115, 55, 20, "Previous"));
    }
    
	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	fontRendererObj.drawString(StatCollector.translateToLocal(tileEntityTransmutationTablet.getInventoryName()), 50, 8, 0x404040);
    	
    	fontRendererObj.drawString(String.format("EMC: %.2f", tileEntityTransmutationTablet.leftoverEMC), 64, 122, 0x404040);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Textures.GUI_TRANSMUTATION_TABLET);

        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

	public void onButtonClicked(GuiButton button)
	{
		if(button.displayString.contentEquals("Next"))
		{
			if(tileEntityTransmutationTablet.getCurrentPage() < tileEntityTransmutationTablet.getNumberOfPages() - 1)
			{
				//tileEntityTransmutationTablet.showPage(tileEntityTransmutationTablet.getCurrentPage() + 1);
			}
		}
		else if(button.displayString.contentEquals("Previous"))
		{
			if(tileEntityTransmutationTablet.getCurrentPage() > 0)
			{
				//tileEntityTransmutationTablet.showPage(tileEntityTransmutationTablet.getCurrentPage() - 1);
			}
		}
	}
}
