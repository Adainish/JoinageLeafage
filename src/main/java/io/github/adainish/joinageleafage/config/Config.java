package io.github.adainish.joinageleafage.config;

import java.util.Arrays;

public class Config extends Configurable{
    private static Config config;

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }
    @Override
    public void populate() {

        this.get().getNode("GUI", "Filler", "FirstFiller").setValue(15).setComment("The meta dye colour for the first pane set");
        this.get().getNode("GUI", "Filler", "SecondFiller").setValue(7).setComment("The meta dye colour for the second pane set");
        this.get().getNode("Messages", "DefaultJoin", "Weight").setValue(0).setComment("The weight for the appearance of this item in the GUI.");
        this.get().getNode("Messages", "DefaultJoin", "Display").setValue("&7Default Message").setComment("The GUI Title for the display.");
        this.get().getNode("Messages", "DefaultJoin", "Lore").setValue(Arrays.asList("&7This is the default message!")).setComment("The Lore Displayed when seen in the GUI.");
        this.get().getNode("Messages", "DefaultJoin", "Message").setValue("&a+ @pl has joined").setComment("The message sent when this message is meant to appear.");
        this.get().getNode("Messages", "DefaultJoin", "ItemString").setValue("minecraft:paper").setComment("the GUI item.");
        this.get().getNode("Messages", "DefaultJoin", "MessageType").setValue("JOIN").setComment("What type of message this is, it can be either JOIN or LEAVE.");
        this.get().getNode("Messages", "DefaultJoin", "PermissionNode").setValue("").setComment("");

        this.get().getNode("Messages", "DefaultLeave", "Weight").setValue(0).setComment("The weight for the appearance of this item in the GUI.");
        this.get().getNode("Messages", "DefaultLeave", "Display").setValue("&7Default Message").setComment("The GUI Title for the display.");
        this.get().getNode("Messages", "DefaultLeave", "Lore").setValue(Arrays.asList("&7This is the default message!")).setComment("The Lore Displayed when seen in the GUI.");
        this.get().getNode("Messages", "DefaultLeave", "Message").setValue("&4- @pl has left").setComment("The message sent when this message is meant to appear.");
        this.get().getNode("Messages", "DefaultLeave", "ItemString").setValue("minecraft:paper").setComment("the GUI item.");
        this.get().getNode("Messages", "DefaultLeave", "MessageType").setValue("LEAVE").setComment("What type of message this is, it can be either JOIN or LEAVE.");
        this.get().getNode("Messages", "DefaultLeave", "PermissionNode").setValue("").setComment("");


        this.get().getNode("Settings", "StaffPermission").setValue("staff.loginmessage.disable").setComment("The perm staff needs to toggle their login/out messages");
        this.get().getNode("Settings", "Prefix").setValue("&7< &b&lJoinageLeafage &7>").setComment("The Message prefix for Messages");
        this.get().getNode("Settings", "LoginMessages").setValue(true).setComment("Whether login messages are enabled");
        this.get().getNode("Settings", "LogoutMessages").setValue(true).setComment("Whether logout messages are enabled");
    }

    @Override
    public String getConfigName() {
        return "JoinageLeafage.conf";
    }
}
