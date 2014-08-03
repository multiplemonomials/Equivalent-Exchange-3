package net.multiplemonomials.eer.item;

import net.multiplemonomials.eer.reference.Names;

public class ItemDarkMatter extends ItemEE 
{
	public ItemDarkMatter()
	{
		  super();
	      this.setUnlocalizedName(Names.Items.DARK_MATTER);
	      
	      //override stack size setting
	      this.maxStackSize = 64;
	}
}
