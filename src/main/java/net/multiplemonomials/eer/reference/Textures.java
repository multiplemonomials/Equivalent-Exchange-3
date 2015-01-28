package net.multiplemonomials.eer.reference;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.multiplemonomials.eer.util.ResourceLocationHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Textures
{
    // Base file paths
    public static final String MODEL_TEXTURE_LOCATION = "textures/models/";
    // Model textures
    public static final ResourceLocation MODEL_CALCINATOR_IDLE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "calcinator_idle.png");
    public static final ResourceLocation MODEL_CALCINATOR_ACTIVE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "calcinator_active.png");
    public static final ResourceLocation MODEL_ALUDEL = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "aludel.png");
    public static final ResourceLocation MODEL_ALCHEMICAL_CHEST_SMALL = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "alchemicalChest_small.png");
    public static final ResourceLocation MODEL_ALCHEMICAL_CHEST_MEDIUM = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "alchemicalChest_medium.png");
    public static final ResourceLocation MODEL_ALCHEMICAL_CHEST_LARGE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "alchemicalChest_large.png");
    public static final ResourceLocation MODEL_GLASS_BELL = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "aludel.png");
    public static final ResourceLocation MODEL_RESEARCH_STATION = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "researchStation.png");
	public static final ResourceLocation MODEL_CONDENSER = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "condenser.png");
    
    public static final String ARMOR_SHEET_LOCATION = "textures/armor/";
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    // GUI textures
    public static final ResourceLocation GUI_CALCINATOR = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "calcinator.png");
    public static final ResourceLocation GUI_ALUDEL = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "aludel.png");
    public static final ResourceLocation GUI_ALCHEMICAL_BAG_SMALL = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "alchemicalBag_small.png");
    public static final ResourceLocation GUI_ALCHEMICAL_BAG_MEDIUM = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "alchemicalBag_medium.png");
    public static final ResourceLocation GUI_ALCHEMICAL_BAG_LARGE = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "alchemicalBag_large.png");
    public static final ResourceLocation GUI_ALCHEMICAL_CHEST_SMALL = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "alchemicalChest_small.png");
    public static final ResourceLocation GUI_ALCHEMICAL_CHEST_MEDIUM = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "alchemicalChest_medium.png");
    public static final ResourceLocation GUI_ALCHEMICAL_CHEST_LARGE = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "alchemicalChest_large.png");
    public static final ResourceLocation GUI_CONDENSER = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "condenser.png");
    public static final ResourceLocation GUI_CONDENSER_BAR = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "condenserBar.png");
    public static final ResourceLocation GUI_GLASS_BELL = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "glassBell.png");
	public static final ResourceLocation GUI_TRANSMUTATION_TABLET = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "transmutationTablet.png");
	public static final ResourceLocation GUI_ENERGY_COLLECTOR = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "energyCollector.png");


    public static final String EFFECTS_LOCATION = "textures/effects/";
    // Effect textures
    public static final ResourceLocation EFFECT_WORLD_TRANSMUTATION = ResourceLocationHelper.getResourceLocation(EFFECTS_LOCATION + "noise.png");
    // Item/Block sprite sheets
    public static final ResourceLocation VANILLA_BLOCK_TEXTURE_SHEET = TextureMap.locationBlocksTexture;
    public static final ResourceLocation VANILLA_ITEM_TEXTURE_SHEET = TextureMap.locationItemsTexture;

    
}
