package com.pahimar.ee3.block;

import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.RenderIds;
import com.pahimar.ee3.reference.Textures;
import com.pahimar.ee3.tileentity.TileEntityTransmutationTablet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTransmutationTablet extends BlockEE implements ITileEntityProvider
{
	
	private IIcon topTexture;
	private IIcon sideBottomTexture;
	
    public BlockTransmutationTablet()
    {
        super(Material.rock);
        this.setHardness(3.0f);
        this.setBlockName(Names.Blocks.TRANSMUTATION_TABLET);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {
        return new TileEntityTransmutationTablet();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }
	
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if(ForgeDirection.getOrientation(side) == ForgeDirection.UP)
        {
            return topTexture;
        }
        else
        {
            return sideBottomTexture;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
    	topTexture = iconRegister.registerIcon(String.format("%s_top", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    	sideBottomTexture = iconRegister.registerIcon(String.format("%s_side", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

    }
}
