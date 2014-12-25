package net.multiplemonomials.eer.block;

import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.RenderIds;
import net.multiplemonomials.eer.tileentity.TileEntityAludel;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAludel extends BlockEE implements ITileEntityProvider
{
    public BlockAludel()
    {
        super(Material.anvil);
        this.setHardness(5.0f);
        this.setBlockName(Names.Blocks.ALUDEL);
        this.setBlockBounds(0.10F, 0.0F, 0.10F, 0.90F, 1.0F, 0.90F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {
        return new TileEntityAludel();
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
        return RenderIds.aludel;
    }
}
