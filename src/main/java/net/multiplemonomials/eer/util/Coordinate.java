package net.multiplemonomials.eer.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A BlockStack, like an ItemStack to an Item, represents an instance of a block in the world and its 4D coordinates
 * @author Jamie
 *
 */
public class Coordinate
{	
	public int x, y, z;
	
	public World world;
	
	public Coordinate(int x, int y, int z, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	
	public Coordinate(Coordinate otherCoordinate)
	{
		this.x = otherCoordinate.x;
		this.y = otherCoordinate.y;
		this.z = otherCoordinate.z;
		this.world = otherCoordinate.world;
	}
	
	@Override
	public Coordinate clone()
	{
		return new Coordinate(this);
	}
	
	@Override
	public int hashCode()
	{
		return x + 10 * y + 100 * z + 10000 * world.provider.dimensionId;
	}
	
	/**
	 * Moves the supplied Coordinate by one block in the supplied ForgeDirection
	 * @param coordinate
	 * @param direction
	 * @return
	 */
	public static Coordinate offsetByOne(Coordinate coordinate, ForgeDirection direction)
	{
		Coordinate result = coordinate.clone();
		result.x += direction.offsetX;
		result.y += direction.offsetY;
		result.z += direction.offsetZ;
		
		return result;
	}
	
	public Block getBlock()
	{
		return world.getBlock(x, y, z);
	}
	
	public TileEntity getTileEntity()
	{
		return world.getTileEntity(x, y, z);
	}
	
	@Override
	public String toString()
	{
		return String.format("(%d, %d, %d)", x, y, z);
	}
}
