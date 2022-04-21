package io.github.adainish.joinageleafage.commands;

import ca.landonjw.gooeylibs2.api.UIManager;
import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.gui.GUI;
import io.github.adainish.joinageleafage.obj.Player;
import io.github.adainish.joinageleafage.util.PermissionUtil;
import io.github.adainish.joinageleafage.util.Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Command extends CommandBase {
    @Override
    public String getName() {
        return "joinageleafage";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "&b/joinageleafage";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("joinageleafage.command.messages.base", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        if (args.length >= 2) {
            Util.send(sender, getUsage(sender));
            return;
        }


        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!PermissionUtil.canUse("joinageleafage.command.messages.reload", sender)) {
                        sendNoPermMessage(sender);
                        return;
                    }
                    JoinageLeafage.INSTANCE.reload();
                    Util.send(sender, "&cReloaded joinage-leafage");
                    return;
                }
                if (args[0].equalsIgnoreCase("toggle")) {
                    if (sender instanceof  EntityPlayerMP) {
                        if (!PermissionUtil.canUse("joinageleafage.command.messages.toggle", sender)) {
                            sendNoPermMessage(sender);
                            return;
                        }
                        Player p = JoinageLeafage.onlinePlayers.get(((EntityPlayerMP) sender).getUniqueID());
                        p.setDisplayMessage(false);
                        p.update();
                        Util.send(sender, "&cToggled your messages");
                    } else Util.send(sender, "&cOnly a player may use this command");
                }
                break;
            default:
                if (sender instanceof EntityPlayerMP) {
                    Player p = JoinageLeafage.onlinePlayers.get(((EntityPlayerMP) sender).getUniqueID());
                    if (p == null) {
                        Util.send(sender, "&cSomething went wrong loading your player data");
                        return;
                    }
                    UIManager.openUIPassively((EntityPlayerMP) sender, GUI.PlayerPage(p), 20, TimeUnit.MILLISECONDS);
                } else {
                    Util.send(sender, getUsage(sender));
                }
        }




    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List <String> getAliases() {
        return Arrays.asList("messages", "jl");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
