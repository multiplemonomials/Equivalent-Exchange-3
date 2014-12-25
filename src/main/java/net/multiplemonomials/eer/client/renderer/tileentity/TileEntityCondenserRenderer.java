package net.multiplemonomials.eer.client.renderer.tileentity;

import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class TileEntityCondenserRenderer extends TileEntitySpecialRenderer
{
    private final ModelChest modelChest = new ModelChest();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
    {
        if (tileEntity instanceof TileEntityCondenser)
        {
        	TileEntityCondenser tileEntityCondenser = (TileEntityCondenser) tileEntity;
            ForgeDirection direction = null;

            if (tileEntityCondenser.getWorldObj() != null)
            {
                direction = tileEntityCondenser.getOrientation();
            }

            bindTexture(Textures.MODEL_CONDENSER);

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short angle = 0;

            if (direction != null)
            {
                if (direction == ForgeDirection.NORTH)
                {
                    angle = 180;
                }
                else if (direction == ForgeDirection.SOUTH)
                {
                    angle = 0;
                }
                else if (direction == ForgeDirection.WEST)
                {
                    angle = 90;
                }
                else if (direction == ForgeDirection.EAST)
                {
                    angle = -90;
                }
            }

            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float adjustedLidAngle = tileEntityCondenser.prevLidAngle + (tileEntityCondenser.lidAngle - tileEntityCondenser.prevLidAngle) * tick;
            adjustedLidAngle = 1.0F - adjustedLidAngle;
            adjustedLidAngle = 1.0F - adjustedLidAngle * adjustedLidAngle * adjustedLidAngle;
            modelChest.chestLid.rotateAngleX = -(adjustedLidAngle * (float) Math.PI / 2.0F);
            modelChest.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        
        else //this is here in an attempt to find out if this instanceof check is actually needed
        {
        	System.out.println("[EER] What? Somebody tried to render the block at " + x + ", " + y + ", " + z + 
        			", which is not a Condenser, as a Condenser");
        	
        }
    }
}
