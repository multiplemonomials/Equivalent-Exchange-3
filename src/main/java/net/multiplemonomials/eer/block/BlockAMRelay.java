package net.multiplemonomials.eer.block;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.multiplemonomials.eer.init.ModBlocks;
import net.multiplemonomials.eer.init.ModItems;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.tileentity.TileEntityAMRelay;
import net.multiplemonomials.eer.tileentity.TileEntityEE;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAMRelay extends BlockEE implements ITileEntityProvider
{
    @SideOnly(Side.CLIENT)
    private IIcon blockFront, blockSide, blockBack;
    
    private byte upgradeLevel;

    //this block is constructed and added to the registry three times for the different levels
    public BlockAMRelay(byte upgradeLevel)
    {
        super();
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setBlockName(Names.Blocks.ANTIMATTER_RELAY + Names.Blocks.ANTIMATTER_RELAY_SUBTYPES[upgradeLevel - 1]);        
        this.upgradeLevel = upgradeLevel;
    }
    
    @Override
    public int damageDropped(int metaData)
    {
        return ForgeDirection.SOUTH.ordinal();
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        if (world.getTileEntity(x, y, z) instanceof TileEntityEE)
        {
            int direction = 0;
            int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

            if (facing == 0)
            {
                direction = ForgeDirection.NORTH.ordinal();
            }
            else if (facing == 1)
            {
                direction = ForgeDirection.EAST.ordinal();
            }
            else if (facing == 2)
            {
                direction = ForgeDirection.SOUTH.ordinal();
            }
            else if (facing == 3)
            {
                direction = ForgeDirection.WEST.ordinal();
            }

            if(itemStack != null && itemStack.hasDisplayName())
            {
                ((TileEntityEE) world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
            }

            world.setBlockMetadataWithNotify(x, y, z, direction, 2);
        }
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(this);
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
        blockSide = iconRegister.registerIcon(String.format("%s_side", Reference.RESOURCE_PREFIX + Names.Blocks.ANTIMATTER_RELAY));
        blockBack = iconRegister.registerIcon(String.format("%s_back", Reference.RESOURCE_PREFIX + Names.Blocks.ANTIMATTER_RELAY));
        blockFront = iconRegister.registerIcon(String.format("%s_front", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData)
    {
        ForgeDirection orientation = ForgeDirection.getOrientation(side);
        if(orientation == ForgeDirection.values()[metaData])
        {
        	return blockFront;
        }
        else if(orientation == ForgeDirection.DOWN)
        {
        	return blockBack;
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
        else if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.alchemicalUpgrade && upgradeLevel < 3)
        {
        	if(upgradeLevel == player.getHeldItem().getItemDamage())
        	{
        		TileEntityAMRelay antiMatterRelay = (TileEntityAMRelay)(world.getTileEntity(x, y, z));
        		antiMatterRelay.upgradeLevel();
        		if(upgradeLevel == 1)
        		{
        			world.setBlock(x, y, z, ModBlocks.antiMatterRelayAzure);
        			
        			//update rotation
        			ModBlocks.antiMatterRelayAzure.onBlockPlacedBy(world, x, y, z, player, null);
        		}
        		else
        		{	
        			world.setBlock(x, y, z, ModBlocks.antiMatterRelayMinium);
        			//update rotation
        			ModBlocks.antiMatterRelayMinium.onBlockPlacedBy(world, x, y, z, player, null);
        		}
        		
        		if(!player.capabilities.isCreativeMode)
        		{
        			player.getHeldItem().stackSize -= 1;
        		}
        		
        		world.markBlockForUpdate(x, y, z);
        		
        	}
        	
        	return true;
        }
        
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {
        return new TileEntityAMRelay(upgradeLevel);
    }
}
