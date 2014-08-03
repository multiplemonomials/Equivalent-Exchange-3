package net.multiplemonomials.eer.block;

import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.RenderIds;
import net.multiplemonomials.eer.tileentity.TileEntityResearchStation;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockResearchStation extends BlockEE implements ITileEntityProvider
{
    public BlockResearchStation()
    {
        super(Material.rock);
        this.setHardness(2.0f);
        this.setBlockName(Names.Blocks.RESEARCH_STATION);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {
        return new TileEntityResearchStation();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.researchStation;
    }
}
