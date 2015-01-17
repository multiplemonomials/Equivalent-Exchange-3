package net.multiplemonomials.eer.item.tool;


import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.creativetab.CreativeTab;
import net.multiplemonomials.eer.interfaces.IChargeable;
import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.item.ItemEE;
import net.multiplemonomials.eer.reference.Key;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.PowerItemUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemSwordDarkMatter extends ItemSword implements IChargeable, IKeyBound
{
	
	Matter _matterType;

	public ItemSwordDarkMatter(Matter matterType)
	{
		super(matterType._toolMaterial);
		
		setUnlocalizedName("sword" + matterType.name());
		setCreativeTab(CreativeTab.EER_TAB);
		
		setNoRepair();
		
		maxStackSize = 1;
		
		_matterType = matterType;
		
        setMaxDamage(CommonConfiguration.MAX_ITEM_CHARGES);
	}
	
	//not repairable... because it never breaks
	@Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }
	
	//do not decrease durability on block destroyed
	@Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {

        return true;
    }
	
	//thanks to Modular Powersuits for the code this is based off of
	 public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
    	
		float damageToDo = (float) (CommonConfiguration.DM_SWORD_BASE_DAMAGE + PowerItemUtils.computeEfficiencyBonus(par1ItemStack.getItemDamage(), _matterType));
		 
    	DamageSource damagesource = DamageSource.causePlayerDamage((EntityPlayer)par3EntityLivingBase);
    	
    	par2EntityLivingBase.attackEntityFrom(damagesource, damageToDo);
    	return true;
    }
	 
    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key)
    {
    	if(key == Key.CHARGE)
    	{
    		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
    		{
    			PowerItemUtils.lowerChargeOnItem(itemStack, entityPlayer);
    		}
    		else
    		{
    			PowerItemUtils.bumpChargeOnItem(itemStack, entityPlayer);
    		}
    	}
    }
    
    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, ItemEE.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.RESOURCE_PREFIX, ItemEE.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(ItemEE.getUnwrappedUnlocalizedName(this.getUnlocalizedName()));
    }
    
    public boolean isDamageable()
    {
        return false;
    }


}
