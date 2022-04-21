package io.github.adainish.joinageleafage.handlers;

import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.config.Config;
import io.github.adainish.joinageleafage.enums.MessageTypes;
import io.github.adainish.joinageleafage.obj.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MessageHandler {
    private List<Message> messageList = new ArrayList <>();
    private List<Message> logoutList = new ArrayList <>();
    private List<Message> loginList = new ArrayList <>();

    private boolean doLoginMessages = false;
    private boolean doLogOutMessages = false;
    private String staffPermissionNode = "";

    public MessageHandler(){
        loadMessages();
        setDoLoginMessages(Config.getConfig().get().getNode("Settings", "LoginMessages").getBoolean());
        setDoLogOutMessages(Config.getConfig().get().getNode("Settings", "LogoutMessages").getBoolean());
        setStaffPermissionNode(Config.getConfig().get().getNode("Settings", "StaffPermission").getString());
    }

    public void loadMessages() {
        getMessageList().clear();
        getLoginList().clear();
        getLogoutList().clear();

        CommentedConfigurationNode rootNode = Config.getConfig().get().getNode("Messages");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) ;
            else {
                String node = nodeObject.toString();
                if (node == null) ;
                else {
                    Message message = new Message(node);
                    if (message.getMessageType().equals(MessageTypes.LEAVE)) {
                        getLogoutList().add(message);
                    } else if (message.getMessageType().equals(MessageTypes.JOIN)){
                        getLoginList().add(message);
                    } else {
                        JoinageLeafage.log.info("The parsed message type for %id% doesn't exist, it has not been added as an option".replace("%id%", node));
                        continue;
                    }
                    getMessageList().add(message);
                }
            }
        }
        sortMessages();
    }
    public void sortMessages() {
        getMessageList().sort(Comparator.comparing(Message::getWeight));
        getLogoutList().sort(Comparator.comparing(Message::getWeight));
        getLoginList().sort(Comparator.comparing(Message::getWeight));
    }
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public boolean doLoginMessages() {
        return doLoginMessages;
    }

    public void setDoLoginMessages(boolean doLoginMessages) {
        this.doLoginMessages = doLoginMessages;
    }

    public boolean doLogOutMessages() {
        return doLogOutMessages;
    }

    public void setDoLogOutMessages(boolean doLogOutMessages) {
        this.doLogOutMessages = doLogOutMessages;
    }

    public String getStaffPermissionNode() {
        return staffPermissionNode;
    }

    public void setStaffPermissionNode(String staffPermissionNode) {
        this.staffPermissionNode = staffPermissionNode;
    }

    public List <Message> getLogoutList() {
        return logoutList;
    }

    public void setLogoutList(List <Message> logoutList) {
        this.logoutList = logoutList;
    }

    public List <Message> getLoginList() {
        return loginList;
    }

    public void setLoginList(List <Message> loginList) {
        this.loginList = loginList;
    }

    public Message returnMessage(String identifier) {
        for (Message m:getMessageList()) {
            if (m.getIdentifier().equalsIgnoreCase(identifier)) {
                return m;
            }
        }
        return null;
    }

    public Message returnLoginMessage(String identifier) {
        for (Message m:getLoginList()) {
            if (m.getIdentifier().equalsIgnoreCase(identifier)) {
                return m;
            }
        }
        return null;
    }

    public Message returnLogoutMessage(String identifier) {
        for (Message m:getLogoutList()) {
            if (m.getIdentifier().equalsIgnoreCase(identifier)) {
                return m;
            }
        }
        return null;
    }
}
