package io.github.adainish.joinageleafage.obj;

import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.storage.PlayerStorage;
import io.github.adainish.joinageleafage.util.PermissionUtil;
import io.github.adainish.joinageleafage.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class Player {

    private String enabledLoginMessageIdentifier;
    private String enabledLogOutMessageIdentifier;
    private UUID uuid;
    private boolean displayMessage = true;

    public Player(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEnabledLoginMessageIdentifier() {
        return enabledLoginMessageIdentifier;
    }

    public void setEnabledLoginMessageIdentifier(String enabledMessageIdentifier) {
        this.enabledLoginMessageIdentifier = enabledMessageIdentifier;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean doDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(boolean displayMessage) {
        this.displayMessage = displayMessage;
    }

    public void updateCache() {
        JoinageLeafage.onlinePlayers.remove(uuid);
        if (Util.isOnline(uuid)) {
            JoinageLeafage.onlinePlayers.put(uuid, this);
        }
    }

    public boolean isStaffMember() {
        return PermissionUtil.canUse(JoinageLeafage.getMessageHandler().getStaffPermissionNode(), Util.getPlayer(uuid));
    }

    public void update() {
        updateCache();
        PlayerStorage.savePlayer(this);
    }

    public void sendLoginMessage() {
        Message m = JoinageLeafage.getMessageHandler().returnLoginMessage(getEnabledLoginMessageIdentifier());
        if (m == null) {
            JoinageLeafage.log.info("Message didn't exist, preventing broadcast");
            return;
        }

        if (isStaffMember()) {
            if (!displayMessage) {
                return;
            }

        }

        EntityPlayerMP target = Util.getPlayer(uuid);
        Util.broadcast(m.getMessage().replace("@pl", target.getName()));
    }


    public void sendLogOutMessage() {
        Message m = JoinageLeafage.getMessageHandler().returnLogoutMessage(getEnabledLogOutMessageIdentifier());
        if (m == null) {
            JoinageLeafage.log.info("Message didn't exist, preventing broadcast");
            return;
        }
        if (isStaffMember()) {
            if (!displayMessage) {
                return;
            }
        }
        EntityPlayerMP target = Util.getPlayer(uuid);
        Util.broadcast(m.getMessage().replace("@pl", target.getName()));
    }

    public String getEnabledLogOutMessageIdentifier() {
        return enabledLogOutMessageIdentifier;
    }

    public void setEnabledLogOutMessageIdentifier(String enabledLogOutMessageIdentifier) {
        this.enabledLogOutMessageIdentifier = enabledLogOutMessageIdentifier;
    }
}
