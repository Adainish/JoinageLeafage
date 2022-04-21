package io.github.adainish.joinageleafage.listeners;

import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.obj.Player;
import io.github.adainish.joinageleafage.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerListener {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player == null)
            return;


        Player p = PlayerStorage.getPlayer(event.player.getUniqueID());

        if (p == null) {
            PlayerStorage.makePlayer((EntityPlayerMP) event.player);
            p = PlayerStorage.getPlayer(event.player.getUniqueID());
        }

        p.updateCache();
        if (JoinageLeafage.getMessageHandler().doLoginMessages())
            p.sendLoginMessage();
    }


    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player == null)
            return;

        Player p = PlayerStorage.getPlayer(event.player.getUniqueID());

        if (p == null) {
            PlayerStorage.makePlayer((EntityPlayerMP) event.player);
            return;
        }

        if (JoinageLeafage.getMessageHandler().doLogOutMessages())
            p.sendLogOutMessage();

        p.update();
    }
}
