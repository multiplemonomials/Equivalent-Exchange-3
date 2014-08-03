package net.multiplemonomials.eer.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

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
}
