package net.multiplemonomials.eer.handler;

import net.multiplemonomials.eer.util.EmcInitializationHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventHandler
{
    @SubscribeEvent
    public void onWorldLoadEvent(WorldEvent.Load event)
    {
        EmcInitializationHelper.initEmcRegistry();
    }
}
