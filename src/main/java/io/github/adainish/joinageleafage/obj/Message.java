package io.github.adainish.joinageleafage.obj;

import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.joinageleafage.config.Config;
import io.github.adainish.joinageleafage.enums.MessageTypes;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String identifier;
    private int weight;
    private String permissionNode;
    private String display;
    private MessageTypes messageType;
    private String message;
    private List <String> lore = new ArrayList <>();
    private String itemString;

    public Message(String identifier) {
        setIdentifier(identifier);
        setWeight(Config.getConfig().get().getNode("Messages", identifier, "Weight").getInt());
        setPermissionNode(Config.getConfig().get().getNode("Messages", identifier, "PermissionNode").getString());
        setMessage(Config.getConfig().get().getNode("Messages", identifier, "Message").getString());
        setItemString(Config.getConfig().get().getNode("Messages", identifier, "ItemString").getString());
        setMessageType(Config.getConfig().get().getNode("Messages", identifier, "MessageType").getString());
        try {
            setLore(Config.getConfig().get().getNode("Messages", identifier, "Lore").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        setDisplay(Config.getConfig().get().getNode("Messages", identifier, "Display").getString());
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPermissionNode() {
        return permissionNode;
    }

    public void setPermissionNode(String permissionNode) {
        this.permissionNode = permissionNode;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public MessageTypes getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypes messageType) {
        this.messageType = messageType;
    }
    public void setMessageType(String parsedType) {
        for (MessageTypes type:MessageTypes.values()) {
            if (type.name().equalsIgnoreCase(parsedType)) {
                setMessageType(MessageTypes.valueOf(parsedType.toUpperCase()));
                break;
            }
        }
        setMessageType(MessageTypes.JOIN);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List <String> getLore() {
        return lore;
    }

    public void setLore(List <String> lore) {
        this.lore = lore;
    }

    public String getItemString() {
        return itemString;
    }

    public void setItemString(String itemString) {
        this.itemString = itemString;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
