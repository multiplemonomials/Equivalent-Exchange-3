package net.multiplemonomials.eer.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;

public class BlockHelper 
{
	/**
	 * Sets blocks around the given point in a square from the given radius
	 * @param x
	 * @param y
	 * @param z
	 * @param blockToSetTo
	 * @param metadata
	 * @param radius
	 * @param world
	 */
	public static void setBlocksAround(int x, int y, int z, Block blockToSetTo, int metadata, int radius, World world)
	{
		
		
		//thanks http://stackoverflow.com/questions/14285358/find-all-integer-coordinates-in-a-given-radius
		for(int currentX = -radius + x; currentX < radius + x; ++currentX)
		for(int currentZ = -radius + z; currentZ < radius + z; ++currentZ)
		{
		    world.setBlock(currentX, y, currentZ, blockToSetTo, metadata, 2);
		}
	}
	
	/**
	 * Sets only air blocks around the given point in a square from the given radius
	 * @param x
	 * @param y
	 * @param z
	 * @param blockToReplace the block to replace 
	 * @param blockToSetTo the block to add
	 * @param metadata
	 * @param radius
	 * @param world
	 */
	public static void setBlocksOfTypeAround(int x, int y, int z, Block blockToReplace, Block blockToSetTo, int metadata, int radius, World world)
	{
		
		
		//thanks http://stackoverflow.com/questions/14285358/find-all-integer-coordinates-in-a-given-radius
		for(int currentX = -radius + x; currentX < radius + x; ++currentX)
		for(int currentZ = -radius + z; currentZ < radius + z; ++currentZ)
		{
			if(world.getBlock(currentX,  y,  currentZ) == blockToReplace)
			{
			    world.setBlock(currentX, y, currentZ, blockToSetTo, metadata, 2);				
			}
		}
	}
	
	
	/**
	 * Breaks the blocks within the bounding box and returns the items dropped.
	 * If the durability of the tool is 0 after this function call, then the tool broke partway through.
	 * Breaks blocks as the provided player.
	 * Should be compatible with all Forge-based server protection tools.
	 * @param x
	 * @param y
	 * @param z
	 * @param blockToSetTo
	 * @param metadata
	 * @param radius
	 * @param world
	 */
	public static ArrayList<ItemStack> instaMineBlocks(AxisAlignedBB boundingBox, World world, EntityPlayer player, ItemStack toolToUse)
	{
		
		//convert everything to ints
		int xStart = MathHelper.floor_double(boundingBox.minX);
		int yStart = MathHelper.floor_double(boundingBox.minY);
		int zStart = MathHelper.floor_double(boundingBox.minZ);
		int xEnd = MathHelper.floor_double(boundingBox.maxX);
		int yEnd = MathHelper.floor_double(boundingBox.maxY);
		int zEnd = MathHelper.floor_double(boundingBox.maxZ);
		
		boolean hasSilkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, toolToUse) > 0;

		ArrayList<ItemStack> itemsDropped = new ArrayList<ItemStack>();
		
		for(int currentX = xStart; currentX <= xEnd; ++currentX)
		for(int currentY = yStart; currentY <= yEnd; ++currentY)
		for(int currentZ = zStart; currentZ <= zEnd; ++currentZ)
		{
			Block targetBlock = world.getBlock(currentX, currentY, currentZ);
			int targetMetadata = world.getBlockMetadata(currentX, currentY, currentZ);
			
			//send the proper Forge event
			//well, I don't know how to get the game type, but otherwise...
			
			if(!world.isRemote)
	        {
				BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, WorldSettings.GameType.SURVIVAL, (EntityPlayerMP) player, currentX, currentY, currentZ);
		        if(event.isCanceled())
		        {
		            continue;
		        }
	        }
			
			//this code taken from ItemInWorldManager
			 {
				 boolean canHarvest = targetBlock.canHarvestBlock(player, targetMetadata);
				 if(toolToUse != null)
			     {
			    	 toolToUse.func_150999_a(world, targetBlock, currentX, currentY, currentZ, player);
			
			         if (toolToUse.stackSize == 0)
			         {
			        	 return itemsDropped;
			         }
			     }
			
				
				targetBlock.onBlockHarvested(world, currentX, currentY, currentZ, targetMetadata, player);
			    boolean removed = targetBlock.removedByPlayer(world, player, currentX, currentY, currentZ, canHarvest);
			
			    if(removed)
			    {
			    	targetBlock.onBlockDestroyedByPlayer(world, currentX, currentY, currentZ, targetMetadata);
			    }
				
				 
			     if(removed && canHarvest)
			     {
			    	 itemsDropped.addAll(myHarvestBlock(targetBlock, world, player, currentX, currentY, currentZ, targetMetadata, hasSilkTouch));
			     }
			 }
			
		}
		
		return itemsDropped;
	}
	
    /**
     * Like Block.harvestBlock(), but this function returns the items dropped instead of throwing them on the ground.
     * Also, this doesn't affect the player's stats or hunger.
     */
    public static ArrayList<ItemStack> myHarvestBlock(Block targetBlock, World world, EntityPlayer player, int x, int y, int z, int meta, boolean silkTouch)
    {

        if(silkTouch && targetBlock.canSilkHarvest(world, player, x, y, z, meta))
        {
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            //instead of ItemStack itemstack = targetBlock.createStackedBlock(p_149636_6_);
            int j = 0;
            Item item = Item.getItemFromBlock(targetBlock);

            if (item != null && item.getHasSubtypes())
            {
                j = meta;
            }

            ItemStack itemstack = new ItemStack(item, 1, j);

            if (itemstack != null)
            {
                items.add(itemstack);
            }

            ForgeEventFactory.fireBlockHarvesting(items, world, targetBlock, x, y, z, meta, 0, 1.0f, true, player);
            return items;
        }
        else
        {
            int fortune = EnchantmentHelper.getFortuneModifier(player);
            //instead of this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, i1);
            ArrayList<ItemStack> items = targetBlock.getDrops(world, x, y, z, meta, fortune);
            ForgeEventFactory.fireBlockHarvesting(items, world, targetBlock, x, y, z, meta, fortune, 1.0F, false, player);
            
            return items;
        }
        
    }
}
