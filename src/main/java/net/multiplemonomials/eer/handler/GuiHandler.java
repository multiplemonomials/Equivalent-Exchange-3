package net.multiplemonomials.eer.handler;

import net.multiplemonomials.eer.client.gui.inventory.GuiAlchemicalBag;
import net.multiplemonomials.eer.client.gui.inventory.GuiAlchemicalChest;
import net.multiplemonomials.eer.client.gui.inventory.GuiAludel;
import net.multiplemonomials.eer.client.gui.inventory.GuiCalcinator;
import net.multiplemonomials.eer.client.gui.inventory.GuiCondenser;
import net.multiplemonomials.eer.client.gui.inventory.GuiEnergyCollector;
import net.multiplemonomials.eer.client.gui.inventory.GuiGlassBell;
import net.multiplemonomials.eer.client.gui.inventory.GuiTransmutationTablet;
import net.multiplemonomials.eer.inventory.ContainerAlchemicalBag;
import net.multiplemonomials.eer.inventory.ContainerAlchemicalChest;
import net.multiplemonomials.eer.inventory.ContainerAludel;
import net.multiplemonomials.eer.inventory.ContainerCalcinator;
import net.multiplemonomials.eer.inventory.ContainerCondenser;
import net.multiplemonomials.eer.inventory.ContainerEnergyCollector;
import net.multiplemonomials.eer.inventory.ContainerGlassBell;
import net.multiplemonomials.eer.inventory.ContainerTransmutationTablet;
import net.multiplemonomials.eer.inventory.InventoryAlchemicalBag;
import net.multiplemonomials.eer.reference.GuiIds;
import net.multiplemonomials.eer.tileentity.TileEntityAlchemicalChest;
import net.multiplemonomials.eer.tileentity.TileEntityAludel;
import net.multiplemonomials.eer.tileentity.TileEntityCalcinator;
import net.multiplemonomials.eer.tileentity.TileEntityCondenser;
import net.multiplemonomials.eer.tileentity.TileEntityEnergyCollector;
import net.multiplemonomials.eer.tileentity.TileEntityGlassBell;
import net.multiplemonomials.eer.tileentity.TileEntityTransmutationTablet;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == GuiIds.ALCHEMICAL_CHEST)
        {
            TileEntityAlchemicalChest tileEntityAlchemicalChest = (TileEntityAlchemicalChest) world.getTileEntity(x, y, z);
            return new ContainerAlchemicalChest(player.inventory, tileEntityAlchemicalChest);
        }
        if (id == GuiIds.CONDENSER)
        {
            TileEntityCondenser tileEntityCondenser = (TileEntityCondenser) world.getTileEntity(x, y, z);
            return new ContainerCondenser(player.inventory, tileEntityCondenser);
        }
        else if (id == GuiIds.GLASS_BELL)
        {
            TileEntityGlassBell tileEntityGlassBell = (TileEntityGlassBell) world.getTileEntity(x, y, z);
            return new ContainerGlassBell(player.inventory, tileEntityGlassBell);
        }
        else if (id == GuiIds.ALCHEMICAL_BAG)
        {
            return new ContainerAlchemicalBag(player, new InventoryAlchemicalBag(player.getHeldItem()));
        }
        else if (id == GuiIds.CALCINATOR)
        {
        	TileEntityCalcinator tileEntityCalcinator = (TileEntityCalcinator) world.getTileEntity(x, y, z);
        	return new ContainerCalcinator(player.inventory, tileEntityCalcinator);
        }
        else if (id == GuiIds.ALUDEL)
        {
        	TileEntityAludel tileEntityAludel = (TileEntityAludel) world.getTileEntity(x, y, z);
        	return new ContainerAludel(player.inventory, tileEntityAludel);
        }
        else if (id == GuiIds.TRANSMUTATION_TABLET)
        {
        	TileEntityTransmutationTablet tileEntityTransmutationTablet = (TileEntityTransmutationTablet) world.getTileEntity(x, y, z);
        	return new ContainerTransmutationTablet(player.inventory, tileEntityTransmutationTablet);
        }
        else if (id == GuiIds.ENERGY_COLLECTOR)
        {
        	TileEntityEnergyCollector tileEntityEnergyCollector = (TileEntityEnergyCollector) world.getTileEntity(x, y, z);
        	return new ContainerEnergyCollector(player.inventory, tileEntityEnergyCollector);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == GuiIds.ALCHEMICAL_CHEST)
        {
            TileEntityAlchemicalChest tileEntityAlchemicalChest = (TileEntityAlchemicalChest) world.getTileEntity(x, y, z);
            return new GuiAlchemicalChest(player.inventory, tileEntityAlchemicalChest);
        }
        if (id == GuiIds.CONDENSER)
        {
            TileEntityCondenser tileEntityCondenser = (TileEntityCondenser) world.getTileEntity(x, y, z);
            return new GuiCondenser(player.inventory, tileEntityCondenser);
        }
        else if (id == GuiIds.GLASS_BELL)
        {
            TileEntityGlassBell tileEntityGlassBell = (TileEntityGlassBell) world.getTileEntity(x, y, z);
            return new GuiGlassBell(player.inventory, tileEntityGlassBell);
        }
        else if (id == GuiIds.ALCHEMICAL_BAG)
        {
            return new GuiAlchemicalBag(player, new InventoryAlchemicalBag(player.getHeldItem()));
        }
        else if (id == GuiIds.CALCINATOR)
        {
        	TileEntityCalcinator tileEntityCalcinator = (TileEntityCalcinator) world.getTileEntity(x, y, z);
        	return new GuiCalcinator(player.inventory, tileEntityCalcinator);
        }
        else if (id == GuiIds.ALUDEL)
        {
        	TileEntityAludel tileEntityAludel = (TileEntityAludel) world.getTileEntity(x, y, z);
        	return new GuiAludel(player.inventory, tileEntityAludel);
        }
        else if (id == GuiIds.TRANSMUTATION_TABLET)
        {
        	TileEntityTransmutationTablet tileEntityTransmutationTablet = (TileEntityTransmutationTablet) world.getTileEntity(x, y, z);
        	return new GuiTransmutationTablet(player.inventory, tileEntityTransmutationTablet);
        }
        else if (id == GuiIds.ENERGY_COLLECTOR)
        {
        	TileEntityEnergyCollector tileEntityEnergyCollector = (TileEntityEnergyCollector) world.getTileEntity(x, y, z);
        	return new GuiEnergyCollector(player.inventory, tileEntityEnergyCollector);
        }

        return null;
    }
}
