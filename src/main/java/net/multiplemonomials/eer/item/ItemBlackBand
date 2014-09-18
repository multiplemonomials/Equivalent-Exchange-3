package net.multiplemonomials.eer.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.multiplemonomials.eer.configuration.CommonConfiguration;
import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.interfaces.IStoresEMC;
import net.multiplemonomials.eer.reference.Key;
import net.multiplemonomials.eer.reference.Names;
import net.multiplemonomials.eer.util.EMCHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlackBand extends ItemStoresEMC implements IKeyBound, IStoresEMC
{	
	IIcon [] icons;
	
	//metadatas: 
	//0 - out of fuel
	//1 - has fuel, collecting items
	
    public ItemBlackBand()
    {
        super();
        this.setUnlocalizedName("blackHoleBand");
        
        this.setMaxStackSize(1);
        
        icons = new IIcon[2];
    }

    @SideOnly(Side.CLIENT)
	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key) 
	{
    	if(key == Key.TOGGLE)
    	{
    		ItemStack heldItem = entityPlayer.getHeldItem();
    		
    		if(heldItem.getItem() instanceof ItemBlackBand)
    		{
    			if(heldItem.getItemDamage() == 1)
    			{
    				heldItem.setItemDamage(2);
    			}
    			else if(heldItem.getItemDamage() == 2)
    			{
    				heldItem.setItemDamage(1);
    			}
    				
    		}
    	}
		
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "Inactive");
        
        icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "Active");

    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damageValue)
    {
       return icons[MathHelper.clamp_int(damageValue - 1, 0, icons.length - 1)];
    }
    
    public boolean isCollectingItems(ItemStack itemStack)
    {
    	return itemStack.getItemDamage() > 1;
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isHeldItem) 
    {
    	//really have no idea why the argument is not given as an entityplayer
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		double fuelEMCLeft = getAvailableEMC(itemStack);
    		
    		if(isCollectingItems(itemStack))
    		{
    			collectItems(player);
    			if((!player.capabilities.isCreativeMode))
    			{
    			//TODO: Consume EMC. How much?
    				fuelEMCLeft -= 0;
    			}
    		}
    		
    		if(fuelEMCLeft < 0)
    		{
    		//TODO: Consume how much EMC??
    			fuelEMCLeft += EMCHelper.consumeEMCFromPlayerInventory(player, 10 * 1);
    		}
    		
    		if(fuelEMCLeft < 0)
    		{
    			itemStack.setItemDamage(0);
    		}
    		else if(itemStack.getItemDamage() == 0)
    		{
    			itemStack.setItemDamage(1);
    		}
    		
    		itemStack.stackTagCompound.setDouble("fuelEMCLeft", fuelEMCLeft);
 
    		
    		
    	}
    }
    
	private void collectItems(EntityPlayer player) 
	{
	//TODO: Alter range
		AxisAlignedBB pushBox = AxisAlignedBB.getBoundingBox(player.posX - 5, player.posY - 2, player.posZ - 5, player.posX + 5, player.posY + 1, player.posZ + 5);
		for(Object object : player.worldObj.getEntitiesWithinAABB(EntityItem.class, pushBox))
		{
			assert(object instanceof EntityItem);
			EntityItem item = (EntityItem)object;
			
			item.onCollideWithPlayer(player);
		}
	}

	@Override
	public double getMaxStorableEMC(ItemStack itemStack)
	{
		return 0;
	}

	@Override
	public boolean isEMCBattery()
	{
		return false;
	}
    
    
}
