package net.multiplemonomials.eer.block;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;

/**
 * a Farmland block that wil stay fertile even without water.
 * Always create this block with a metadata of 7, as it is how BlockFarmland indicates it is fertile.
 * @author Jamie
 *
 */
public class BlockIrrigatedFarmland extends BlockFarmland
{
	IIcon textureTop;
	IIcon textureSide;
	
	public BlockIrrigatedFarmland()
	{
		super();
		this.setLightLevel(2);
		this.setBlockName(Names.Blocks.IRRIGATED_FARMLAND);
	}
	
    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, BlockEE.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
		//do nothing
    }
	
    /**
     * Block's chance to react to an entity falling on it.
     */
	@Override
    public void onFallenUpon(World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity p_149746_5_, float p_149746_6_)
    {
        //do nothing
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        textureTop = iconRegister.registerIcon(String.format("%sTop", BlockEE.getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        textureSide = iconRegister.registerIcon(String.format("%sSide", BlockEE.getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if(side == ForgeDirection.UP.ordinal())
        {
        	return textureTop;
        }
        
        return textureSide;
    }
    
    /**
     * canSustainPlant coped from Block that works for BlockIrrigatedFarmland
     * @param world
     * @param x
     * @param y
     * @param z
     * @param direction
     * @param plantable
     * @return
     */
    @Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
    {
        Block plant = plantable.getPlant(world, x, y + 1, z);
        EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);

        if (plant == Blocks.cactus && this == Blocks.cactus)
        {
            return true;
        }

        if (plant == Blocks.reeds && this == Blocks.reeds)
        {
            return true;
        }

        if (plantable instanceof BlockBush)
        {
            return true;
        }

        switch(plantType)
        {
            case Desert: return false;
            case Nether: return this == Blocks.soul_sand;
            case Crop:   return this == ModBlocks.irrigatedFarmland;
            case Cave:   return isSideSolid(world, x, y, z, UP);
            case Plains: return this == Blocks.dirt || this == ModBlocks.irrigatedFarmland;
            case Water:  return world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0;
            case Beach:
                return false;
        }

        return false;
    }
    
    @Override
    public boolean isFertile(World world, int x, int y, int z)
    {
    	return true;
    }

}
