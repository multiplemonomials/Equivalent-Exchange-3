package net.multiplemonomials.eer.handler;

import net.minecraftforge.event.world.WorldEvent;
import net.multiplemonomials.eer.util.EmcInitializationHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler
{
    @SubscribeEvent
    public void onWorldLoadEvent(WorldEvent.Load event)
    {
        EmcInitializationHelper.initEmcRegistry();
    }
}
