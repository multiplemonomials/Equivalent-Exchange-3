package com.pahimar.ee3.block;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.pahimar.ee3.EquivalentExchangeReborn;
import com.pahimar.ee3.reference.GuiIds;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.tileentity.TileEntityTransmutationTablet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (player.isSneaking())
        {
            return true;
        }
        else
        {
            if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityTransmutationTablet)
            {
                player.openGui(EquivalentExchangeReborn.instance, GuiIds.TRANSMUTATION_TABLET, world, x, y, z);
            }

            return true;
        }
    }
    
    @Override
    protected void dropInventory(World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof TileEntityTransmutationTablet))
        {
            return;
        }

        TileEntityTransmutationTablet transmutationTablet = (TileEntityTransmutationTablet) tileEntity;

        for (int i = 0; i < transmutationTablet.getSizeInventory(); i++)
        {
            ItemStack itemStack = transmutationTablet.getStackInSlot(i);

            //semi-hack to stop tablet from dropping displayed items
            if (itemStack != null && itemStack.stackSize > 0 && (i ==TileEntityTransmutationTablet.INPUT_SLOT_INDEX || i == TileEntityTransmutationTablet.ENERGY_SLOT_INDEX))
            {
                Random rand = new Random();

                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

                if (itemStack.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}
