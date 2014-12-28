package net.multiplemonomials.eer.network;

import net.multiplemonomials.eer.network.message.MessageCommonConfigUpdate;
import net.multiplemonomials.eer.network.message.MessageCondenserEMCUpdateToClient;
import net.multiplemonomials.eer.network.message.MessageCondenserEMCUpdateToServer;
import net.multiplemonomials.eer.network.message.MessageConfigFileUpdateToServer;
import net.multiplemonomials.eer.network.message.MessageEERExtendedPlayerUpdateClient;
import net.multiplemonomials.eer.network.message.MessageEERExtendedPlayerUpdateServer;
import net.multiplemonomials.eer.network.message.MessageEMCConfigUpdate;
import net.multiplemonomials.eer.network.message.MessageEnergyCollectorUpdate;
import net.multiplemonomials.eer.network.message.MessageKeyPressed;
import net.multiplemonomials.eer.network.message.MessageReloadEnergyRegistry;
import net.multiplemonomials.eer.network.message.MessageRequestConfiguration;
import net.multiplemonomials.eer.network.message.MessageTileAlchemicalChest;
import net.multiplemonomials.eer.network.message.MessageTileCalcinator;
import net.multiplemonomials.eer.network.message.MessageTileCondenser;
import net.multiplemonomials.eer.network.message.MessageTileEnergyCollector;
import net.multiplemonomials.eer.network.message.MessageTileEntityAMRelay;
import net.multiplemonomials.eer.network.message.MessageTileEntityAludel;
import net.multiplemonomials.eer.network.message.MessageTileEntityEE;
import net.multiplemonomials.eer.network.message.MessageTileEntityGlassBell;
import net.multiplemonomials.eer.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init()
    {
        INSTANCE.registerMessage(MessageTileEntityEE.class, MessageTileEntityEE.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileCalcinator.class, MessageTileCalcinator.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityAludel.class, MessageTileEntityAludel.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityGlassBell.class, MessageTileEntityGlassBell.class, 3, Side.CLIENT);
        INSTANCE.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 4, Side.SERVER);
        INSTANCE.registerMessage(MessageEERExtendedPlayerUpdateClient.class, MessageEERExtendedPlayerUpdateClient.class, 5, Side.CLIENT);
        INSTANCE.registerMessage(MessageEERExtendedPlayerUpdateServer.class, MessageEERExtendedPlayerUpdateServer.class, 6, Side.SERVER);
        INSTANCE.registerMessage(MessageTileAlchemicalChest.class, MessageTileAlchemicalChest.class, 7, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileCondenser.class, MessageTileCondenser.class, 8, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEnergyCollector.class, MessageTileEnergyCollector.class, 9, Side.CLIENT);
        INSTANCE.registerMessage(MessageEnergyCollectorUpdate.class, MessageEnergyCollectorUpdate.class, 10, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityAMRelay.class, MessageTileEntityAMRelay.class, 11, Side.CLIENT);
        INSTANCE.registerMessage(MessageCondenserEMCUpdateToClient.class, MessageCondenserEMCUpdateToClient.class, 12, Side.CLIENT);
        INSTANCE.registerMessage(MessageCondenserEMCUpdateToServer.class, MessageCondenserEMCUpdateToServer.class, 13, Side.SERVER);
        INSTANCE.registerMessage(MessageConfigFileUpdateToServer.class, MessageConfigFileUpdateToServer.class, 14, Side.SERVER);
        INSTANCE.registerMessage(MessageCommonConfigUpdate.class, MessageCommonConfigUpdate.class, 15, Side.CLIENT);
        INSTANCE.registerMessage(MessageEMCConfigUpdate.class, MessageEMCConfigUpdate.class, 16, Side.CLIENT);
        INSTANCE.registerMessage(MessageRequestConfiguration.class, MessageRequestConfiguration.class, 17, Side.SERVER);
        INSTANCE.registerMessage(MessageReloadEnergyRegistry.class, MessageReloadEnergyRegistry.class, 18, Side.CLIENT);

    }
}
