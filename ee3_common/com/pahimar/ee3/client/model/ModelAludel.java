package com.pahimar.ee3.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.Group;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.lib.Models;
import com.pahimar.ee3.lib.Reference;
import com.pahimar.ee3.lib.Textures;
import com.pahimar.ee3.tileentity.TileAludel;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Equivalent-Exchange-3
 * 
 * ModelAludel
 * 
 * @author pahimar
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class ModelAludel extends ModelBase {

    private float scale;

    private WavefrontObject modelAludelOBJ;

    public ModelAludel() {

        scale = 1F;
        modelAludelOBJ = new WavefrontObject(Models.ALUDEL);
    }

    public ModelAludel(float scale) {

        this.scale = scale;
        modelAludelOBJ = new WavefrontObject(Models.ALUDEL);
    }

    public void render(Tessellator tessellator, float scale) {

        if (modelAludelOBJ.getGroups().size() != 0) {
            for (Group group : modelAludelOBJ.getGroups()) {
                if (group.getFaces().size() != 0) {
                    for (Face face : group.getFaces()) {
                        tessellator.startDrawing(face.getDrawingMode());

                        float sumU = 0F;
                        float sumV = 0F;

                        for (int i = 0; i < face.getTextureCoordinates().length; ++i) {
                            sumU += face.getTextureCoordinates()[i].u;
                            sumV += face.getTextureCoordinates()[i].v;
                        }

                        for (int i = 0; i < face.getVertices().length; ++i) {
                            float offsetU = Reference.MODEL_TEXTURE_OFFSET;
                            float offsetV = Reference.MODEL_TEXTURE_OFFSET;
                            if (face.getTextureCoordinates()[i].u > sumU / face.getTextureCoordinates().length) {
                                offsetU = -offsetU;
                            }
                            if (face.getTextureCoordinates()[i].v > sumV / face.getTextureCoordinates().length) {
                                offsetV = -offsetV;
                            }
                            tessellator.addVertexWithUV(face.getVertices()[i].x * scale, face.getVertices()[i].y * scale, face.getVertices()[i].z * scale, face.getTextureCoordinates()[i].u + offsetU, face.getTextureCoordinates()[i].v + offsetV);
                        }

                        tessellator.draw();
                    }
                }
            }
        }
    }

    public void render(TileAludel aludel, double x, double y, double z) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        correctRotation(x, y, z, aludel.getOrientation());
        FMLClientHandler.instance().getClient().renderEngine.func_98187_b(Textures.MODEL_ALUDEL);
        this.render(Tessellator.instance, scale);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void correctRotation(double x, double y, double z, ForgeDirection orientation) {

        if (orientation == ForgeDirection.NORTH) {
            GL11.glTranslated(x + 1, y, z);
            GL11.glRotatef(180F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
        }
        else if (orientation == ForgeDirection.EAST) {
            GL11.glTranslated(x + 1, y, z + 1);
            GL11.glRotatef(90F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
        }
        else if (orientation == ForgeDirection.SOUTH) {
            GL11.glTranslated(x, y, z + 1);
            GL11.glRotatef(0F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
        }
        else if (orientation == ForgeDirection.WEST) {
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(-90F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
        }
    }
}
