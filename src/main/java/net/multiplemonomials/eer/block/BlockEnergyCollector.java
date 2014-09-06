package net.multiplemonomials.eer.block;

import net.multiplemonomials.eer.EquivalentExchangeReborn;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.reference.GuiIds;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockEnergyCollector extends BlockEE
{
    @SideOnly(Side.CLIENT)
    private IIcon[] blockTop;
    @SideOnly(Side.CLIENT)
    private IIcon blockFront, blockSide;

    public BlockEnergyCollector()
    {
        super();
        this.setBlockName(Names.Blocks.ENERGY_COLLECTOR);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockTop = new IIcon[Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length];

        for (int i = 0; i < Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length; i++)
        {
            blockTop[i] = iconRegister.registerIcon(String.format("%s.%s_top", getUnwrappedUnlocalizedName(this.getUnlocalizedName()), Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[i]));
            blockSide = iconRegister.registerIcon(String.format("%s.%s_side", getUnwrappedUnlocalizedName(this.getUnlocalizedName()), Names.Blocks.ENERGY_COLLECTOR_SUBTYPES[i]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData)
    {
        metaData = MathHelper.clamp_int(metaData, 0, Names.Blocks.ENERGY_COLLECTOR_SUBTYPES.length - 1);

        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP)
        {
            return blockTop[metaData];
        }
        else if(ForgeDirection.getOrientation(side) == ForgeDirection.SOUTH)
        {
        	return blockFront;
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
        		TileEntityAlchemicalChest alchemicalChest = (TileEntityAlchemicalChest)(world.getTileEntity(x, y, z));
        		alchemicalChest.upgradeToNextLevel();
        		
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
            if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityAlchemicalChest)
            {
                player.openGui(EquivalentExchangeReborn.instance, GuiIds.ALCHEMICAL_CHEST, world, x, y, z);
            }

            return true;
        }
    }
}
