package net.multiplemonomials.eer.client.renderer.model;

import net.multiplemonomials.eer.reference.Models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ModelGlassBell
{
    private IModelCustom modelGlassBell;

    public ModelGlassBell()
    {
        modelGlassBell = AdvancedModelLoader.loadModel(Models.GLASS_BELL);
    }

    public void render()
    {
        modelGlassBell.renderPart("Bell");
    }
}
