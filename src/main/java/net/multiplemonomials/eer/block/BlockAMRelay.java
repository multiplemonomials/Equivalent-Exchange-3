package net.multiplemonomials.eer.block;

import java.util.Random;

import net.minecraft.block.Block;
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
    private IIcon blockFront, blockSideFacingUp, blockSideFacingLeft, blockSideFacingRight, blockSideFacingDown, blockBack;
    
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
    
    //NOTE: metadatas in this block are the ID's of ForgeDirections, except up is swapped with down so it shows up properly in NEI
    /**
     * Convert block metadata to the side it's facing
     * @param metadata
     */
    public ForgeDirection metadata2Orientation(int metadata)
    {
    	ForgeDirection direction = ForgeDirection.values()[metadata];
    	if(direction == ForgeDirection.UP)
    	{
    		direction = ForgeDirection.DOWN;
    	}
    	else if(direction == ForgeDirection.DOWN)
    	{
    		direction = ForgeDirection.UP;
    	}
    	
    	return direction;
    }
    
    
    @Override
    public int damageDropped(int metaData)
    {
        return ForgeDirection.DOWN.ordinal();
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        ((TileEntityAMRelay)world.getTileEntity(x, y, z)).onNeighborUpdate();
    }

    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        if (world.getTileEntity(x, y, z) instanceof TileEntityEE)
        {
            int direction = 0;
            int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

            if(y < MathHelper.floor_double(entityLiving.posY))
            {
            	direction = ForgeDirection.UP.ordinal();
            }
            else if(y > MathHelper.floor_double(entityLiving.posY + 1))
            {
            	direction = ForgeDirection.DOWN.ordinal();
            }
            else if (facing == 0)
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

            if(itemStack != null)
            {
            	TileEntityEE energyCollector = ((TileEntityEE) world.getTileEntity(x, y, z));
            	energyCollector.setCustomName(itemStack.getDisplayName());
            	energyCollector.setOrientation(direction);
            }
            
            //flip up and down for metadata
            if(direction == 0)
            {
            	direction = 1;
            }
            else if(direction == 1)
            {
            	direction = 0;
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
        blockBack = iconRegister.registerIcon(String.format("%s%s_back", Reference.RESOURCE_PREFIX, Names.Blocks.ANTIMATTER_RELAY));
        
        blockSideFacingUp = iconRegister.registerIcon(String.format("%s%s_side_up", Reference.RESOURCE_PREFIX, Names.Blocks.ANTIMATTER_RELAY));
        blockSideFacingRight = iconRegister.registerIcon(String.format("%s%s_side_right", Reference.RESOURCE_PREFIX, Names.Blocks.ANTIMATTER_RELAY));
        blockSideFacingLeft = iconRegister.registerIcon(String.format("%s%s_side_left", Reference.RESOURCE_PREFIX, Names.Blocks.ANTIMATTER_RELAY));
        blockSideFacingDown = iconRegister.registerIcon(String.format("%s%s_side_down", Reference.RESOURCE_PREFIX, Names.Blocks.ANTIMATTER_RELAY));
        
        blockFront = iconRegister.registerIcon(String.format("%s_front", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData)
    {
        ForgeDirection sideOrientation = ForgeDirection.getOrientation(side);
        ForgeDirection blockOrientation = metadata2Orientation(metaData);
        if(sideOrientation == blockOrientation)
        {
        	return blockFront;
        }
        else if(sideOrientation == blockOrientation.getOpposite())
        {
        	return blockBack;
        }
        else
        {
        	//you need a lot of logic to make it so all the side textures are pointing the right direction

        	IIcon iconToUse = blockSideFacingUp;
        	switch(blockOrientation)
        	{
        		case UP:
        			iconToUse = blockSideFacingUp;
        			break;
        		case DOWN:
        			iconToUse = blockSideFacingDown;
        			break;
        		case NORTH:
        			if(sideOrientation == ForgeDirection.UP)
        			{
        				iconToUse = blockSideFacingUp;
        			}
        			else if(sideOrientation == ForgeDirection.EAST)
        			{
        				iconToUse = blockSideFacingRight;
        			}
        			else if(sideOrientation == ForgeDirection.WEST)
        			{
        				iconToUse = blockSideFacingLeft;
        			}
        			else if(sideOrientation == ForgeDirection.DOWN)
        			{
        				iconToUse = blockSideFacingUp;
        			}
        			break;
        		case SOUTH:
        			if(sideOrientation == ForgeDirection.UP)
        			{
        				iconToUse = blockSideFacingDown;
        			}
        			else if(sideOrientation == ForgeDirection.EAST)
        			{
        				iconToUse = blockSideFacingLeft;
        			}
        			else if(sideOrientation == ForgeDirection.WEST)
        			{
        				iconToUse = blockSideFacingRight;
        			}
        			else if(sideOrientation == ForgeDirection.DOWN)
        			{
        				iconToUse = blockSideFacingDown;
        			}
        			break;
        		case EAST:
        			if(sideOrientation == ForgeDirection.UP)
        			{
        				iconToUse = blockSideFacingRight;
        			}
        			else if(sideOrientation == ForgeDirection.NORTH)
        			{
        				iconToUse = blockSideFacingLeft;
        			}
        			else if(sideOrientation == ForgeDirection.SOUTH)
        			{
        				iconToUse = blockSideFacingRight;
        			}
        			else if(sideOrientation == ForgeDirection.DOWN)
        			{
        				iconToUse = blockSideFacingRight;
        			}
        			break;
        		case WEST:
        			if(sideOrientation == ForgeDirection.UP)
        			{
        				iconToUse = blockSideFacingLeft;
        			}
        			else if(sideOrientation == ForgeDirection.NORTH)
        			{
        				iconToUse = blockSideFacingRight;
        			}
        			else if(sideOrientation == ForgeDirection.SOUTH)
        			{
        				iconToUse = blockSideFacingLeft;
        			}
        			else if(sideOrientation == ForgeDirection.DOWN)
        			{
        				iconToUse = blockSideFacingLeft;
        			}
        			break;
        		default:
        			break;
        			
        	}
        	return iconToUse;
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
        			world.setBlock(x, y, z, ModBlocks.antiMatterRelayDarkMatter);
        			
        			//update rotation
        			ModBlocks.antiMatterRelayDarkMatter.onBlockPlacedBy(world, x, y, z, player, null);
        		}
        		else
        		{	
        			world.setBlock(x, y, z, ModBlocks.antiMatterRelayRedMatter);
        			//update rotation
        			ModBlocks.antiMatterRelayRedMatter.onBlockPlacedBy(world, x, y, z, player, null);
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
