package net.multiplemonomials.eer.item.tool;

import net.minecraft.item.Item.ToolMaterial;
import net.multiplemonomials.eer.util.PowerItemUtils;

//enum to store the three different types of matter
public enum Matter 
{
	Matter(null),
	DarkMatter(PowerItemUtils.MATERIALDARKMATTER), 
	RedMatter(PowerItemUtils.MATERIALREDMATTER);
	
	/** The tool material to use with this material.  Null for antimatter. */
	public ToolMaterial _toolMaterial;
	
	Matter(ToolMaterial toolMaterial)
	{
		_toolMaterial = toolMaterial;
	}
}
