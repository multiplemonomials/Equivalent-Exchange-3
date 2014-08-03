package net.multiplemonomials.eer.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.multiplemonomials.eer.client.util.PowerItemUtils;
import net.multiplemonomials.eer.creativetab.CreativeTab;
import net.multiplemonomials.eer.interfaces.IChargeable;
import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.item.ItemEE;
import net.multiplemonomials.eer.reference.Key;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.reference.Textures;
import net.multiplemonomials.eer.util.BlockHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFlintDarkMatter extends ItemFlintAndSteel implements IChargeable, IKeyBound
{

	public ItemFlintDarkMatter()
	{	
		setUnlocalizedName(Names.Tools.FLINT_DARK_MATTER);
		setCreativeTab(CreativeTab.EER_TAB);
		
		setNoRepair();
		
		maxStackSize = 1;
		
        this.setMaxDamage(PowerItemUtils.MATERIALDARKMATTER.getMaxUses());
	}

	@Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
		//do not decrease durability on block destroyed

        return true;
    }
	
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
    	//do not damage tool

        return true;
    }
    
    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key)
    {
    	if(key == Key.CHARGE)
    	{
    		PowerItemUtils.bumpChargeOnItem(itemStack);
    	}
    }
    
    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, ItemEE.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, ItemEE.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(ItemEE.getUnwrappedUnlocalizedName(this.getUnlocalizedName()));
    }
    
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
    {
        if (side == 0)
        {
            --y;
        }

        if (side == 1)
        {
            ++y;
        }

        if (side == 2)
        {
            --z;
        }

        if (side == 3)
        {
            ++z;
        }

        if (side == 4)
        {
            --x;
        }

        if (side == 5)
        {
            ++x;
        }

        if (!player.canPlayerEdit(x, y, z, side, itemStack))
        {
            return false;
        }
        else
        {
            if (world.isAirBlock(x, y, z))
            {
                world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                
                if(getDamage(itemStack) == Reference.MAX_ITEM_CHARGES)
                {
                	world.setBlock(x, y, z, Blocks.fire, 0, 2);
                }
                else
                {
                	BlockHelper.setBlocksAround(x, y, z, Blocks.fire, 0, Reference.MAX_ITEM_CHARGES - getDamage(itemStack), world);
                }
            }
            return true;
        }
    }


}
