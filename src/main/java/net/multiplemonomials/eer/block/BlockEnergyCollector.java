package net.multiplemonomials.eer.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.multiplemonomials.eer.EquivalentExchangeReborn;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.reference.GuiIds;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.tileentity.TileEntityEnergyCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnergyCollector extends BlockEE implements ITileEntityProvider
{
    @SideOnly(Side.CLIENT)
    private IIcon[] blockTop;
    @SideOnly(Side.CLIENT)
    private IIcon blockFront, blockSide, blockBottom;

    public BlockEnergyCollector()
    {
        super();
        this.setBlockName(Names.Blocks.ENERGY_COLLECTOR);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setLightLevel(.33F);
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(this);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length; meta++)
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public int damageDropped(int metaData)
    {
        return metaData;
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockTop = new IIcon[Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length];

        for (int i = 0; i < Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length; i++)
        {
            blockTop[i] = iconRegister.registerIcon(String.format("%s.%s_top", getUnwrappedUnlocalizedName(this.getUnlocalizedName()), Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[i]));
        }
        blockSide = iconRegister.registerIcon(String.format("%s_side", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        blockBottom = iconRegister.registerIcon(String.format("%s_bottom", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        blockFront = iconRegister.registerIcon(String.format("%s_front", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData)
    {
        metaData = MathHelper.clamp_int(metaData, 0, Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length - 1);

        ForgeDirection orientation = ForgeDirection.getOrientation(side);
        if (orientation == ForgeDirection.UP)
        {
            return blockTop[metaData];
        }
        else if(orientation == ForgeDirection.SOUTH)
        {
        	return blockFront;
        }
        else if(orientation == ForgeDirection.DOWN)
        {
        	return blockBottom;
        }
        else
        {
            return blockSide;
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (player.isSneaking())
        {
            return false;
        }
        else if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.alchemicalUpgrade)
        {
        	if(world.getBlockMetadata(x, y, z) == player.getHeldItem().getItemDamage() - 1)
        	{
        		TileEntityEnergyCollector energyCollector = (TileEntityEnergyCollector)(world.getTileEntity(x, y, z));
        		energyCollector.upgradeLevel();
        		
        		if(!player.capabilities.isCreativeMode)
        		{
        			player.getHeldItem().stackSize -= 1;
        		}
        		
        		world.markBlockForUpdate(x, y, z);
        		
        	}
        	
        	return true;
        }
        else
        {
            if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityEnergyCollector)
            {
                player.openGui(EquivalentExchangeReborn.instance, GuiIds.ENERGY_COLLECTOR, world, x, y, z);
            }

            return true;
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {
        return new TileEntityEnergyCollector();
    }
}
