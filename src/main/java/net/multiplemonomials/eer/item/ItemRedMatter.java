package net.multiplemonomials.eer.item;

import net.multiplemonomials.eer.reference.Names;

public class ItemRedMatter extends ItemEE 
{
	public ItemRedMatter()
	{
		  super();
		  //TODO: Change unlocalized name
	      this.setUnlocalizedName("redMatter");
	      
	      //override stack size setting
	      this.maxStackSize = 64;
	}
}
