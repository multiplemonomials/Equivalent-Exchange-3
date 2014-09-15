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
		switch(direction)
		{
			case UP:
				result.y += 1;
				break;
			case DOWN:
				result.y -= 1;
				break;
			case NORTH:
				result.z -= 1;
				break;
			case SOUTH:
				result.z += 1;
				break;
			case WEST:
				result.x -= 1;
				break;
			case EAST:
				result.x += 1;
				break;
			default:
		}
		
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
