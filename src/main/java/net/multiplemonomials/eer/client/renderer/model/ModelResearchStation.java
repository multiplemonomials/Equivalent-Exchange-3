package net.multiplemonomials.eer.client.renderer.model;

import net.multiplemonomials.eer.reference.Models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ModelResearchStation
{
    private IModelCustom modelResearchStation;

    public ModelResearchStation()
    {
        modelResearchStation = AdvancedModelLoader.loadModel(Models.RESEARCH_STATION);
    }

    public void render()
    {
        modelResearchStation.renderAll();
    }
}
