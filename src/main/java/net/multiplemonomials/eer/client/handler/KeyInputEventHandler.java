package net.multiplemonomials.eer.client.handler;

import org.lwjgl.input.Keyboard;

import net.multiplemonomials.eer.client.settings.Keybindings;
import net.multiplemonomials.eer.interfaces.IKeyBound;
import net.multiplemonomials.eer.network.PacketHandler;
import net.multiplemonomials.eer.network.message.MessageKeyPressed;
import net.multiplemonomials.eer.reference.Key;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class KeyInputEventHandler
{
    private static Key getPressedKeybinding()
    {
        if (Keybindings.charge.isPressed())
        {
            return Key.CHARGE;
        }
        else if (Keybindings.extra.isPressed())
        {
            return Key.EXTRA;
        }
        else if (Keybindings.release.isPressed())
        {
            return Key.RELEASE;
        }
        else if (Keybindings.toggle.isPressed())
        {
            return Key.TOGGLE;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
        	//TODO: use Minecraft jump key (can you even change that???)
        	return Key.JUMP;
        }

        return Key.UNKNOWN;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        if (FMLClientHandler.instance().getClient().inGameHasFocus)
        {
            if (FMLClientHandler.instance().getClientPlayerEntity() != null)
            {
                EntityPlayer entityPlayer = FMLClientHandler.instance().getClientPlayerEntity();

                if (entityPlayer.getCurrentEquippedItem() != null)
                {
                    ItemStack currentlyEquippedItemStack = entityPlayer.getCurrentEquippedItem();

                    if (currentlyEquippedItemStack.getItem() instanceof IKeyBound)
                    {
                        if (entityPlayer.worldObj.isRemote)
                        {
                            PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(getPressedKeybinding()));
                        }
                        else
                        {
                            ((IKeyBound) currentlyEquippedItemStack.getItem()).doKeyBindingAction(entityPlayer, currentlyEquippedItemStack, getPressedKeybinding());
                        }
                    }
                }
            }
        }
    }
}
