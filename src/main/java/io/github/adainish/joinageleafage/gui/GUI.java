package io.github.adainish.joinageleafage.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.config.PixelmonItemsHeld;
import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.obj.Message;
import io.github.adainish.joinageleafage.obj.Player;
import io.github.adainish.joinageleafage.util.PermissionUtil;
import io.github.adainish.joinageleafage.util.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {
    public static GooeyButton filler = GooeyButton.builder()
            .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, JoinageLeafage.getMessageHandler().getPaneFillerOne()))
            .build();

    public static GooeyButton fillerTwo = GooeyButton.builder()
            .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, JoinageLeafage.getMessageHandler().getPaneFillerTwo()))
            .build();

    public static ItemStack playerSkull(String n) {
        ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("SkullOwner", n);
        skull.setTagCompound(tagCompound);
        return skull;
    }

    public static boolean hasSelected(String identifier, Message m) {
        return m.getIdentifier().equals(identifier);
    }

    public static ItemStack setEnchanted(ItemStack stack) {
        ItemStack newStack = new ItemStack(stack.getItem());
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Unbreakable", 1);
        nbt.setString("tooltip", "");
        nbt.setInteger("HideFlags", 4);
        newStack.setTagCompound(nbt);
        newStack.addEnchantment(Enchantment.getEnchantmentByID(-1), 1);
        return newStack;
    }

    public static List<String> formattedLore(List<String> lore, EntityPlayerMP p) {
        List<String> newLore = new ArrayList <>();

        for (String s:lore) {
            newLore.add(s.replaceAll("@pl", p.getName()));
        }

        return newLore;
    }

    public static List <Button> loginMessages(Player p) {
        List<Button> buttonList = new ArrayList <>();


        for (Message m: JoinageLeafage.getMessageHandler().getLoginList()) {

            if (!PermissionUtil.canUse(m.getPermissionNode(), Util.getPlayer(p.getUuid())))
                continue;

            Item it = Item.getByNameOrId(m.getItemString());
            if (it == null)
                continue;

            ItemStack stack;

            if (hasSelected(p.getEnabledLoginMessageIdentifier(), m)) {
                stack = setEnchanted(new ItemStack(it));
            } else stack = new ItemStack(it);

            GooeyButton button = GooeyButton.builder()
                    .display(stack)
                    .title(Util.formattedString(m.getDisplay()))
                    .lore(Util.formattedArrayList(formattedLore(m.getLore(), Util.getPlayer(p.getUuid()))))
                    .onClick(b -> {
                        p.setEnabledLoginMessageIdentifier(m.getIdentifier());
                        p.update();
                        Util.send(b.getPlayer(), "&m&7----------------------------");
                        Util.send(b.getPlayer(), "&cYour Login message has been set to: ");
                        Util.send(b.getPlayer(), m.getMessage().replace("@pl", b.getPlayer().getName()));
                        Util.send(b.getPlayer(), "&m&7----------------------------");
                        UIManager.closeUI(b.getPlayer());
                    })
                    .build();
            buttonList.add(button);
        }


        return buttonList;
    }

    public static List <Button> logoutMessages(Player p) {
        List<Button> buttonList = new ArrayList <>();

        for (Message m: JoinageLeafage.getMessageHandler().getLogoutList()) {

            if (!PermissionUtil.canUse(m.getPermissionNode(), Util.getPlayer(p.getUuid())))
                continue;


            Item it = Item.getByNameOrId(m.getItemString());
            if (it == null)
                continue;

            ItemStack stack;

            if (hasSelected(p.getEnabledLogOutMessageIdentifier(), m)) {
                stack = setEnchanted(new ItemStack(it));
            } else stack = new ItemStack(it);

            GooeyButton button = GooeyButton.builder()
                    .display(stack)
                    .title(Util.formattedString(m.getDisplay()))
                    .lore(Util.formattedArrayList(formattedLore(m.getLore(), Util.getPlayer(p.getUuid()))))
                    .onClick(b -> {
                        p.setEnabledLogOutMessageIdentifier(m.getIdentifier());
                        p.update();
                        Util.send(b.getPlayer(), "&m&7----------------------------");
                        Util.send(b.getPlayer(), "&cYour Logout message has been set to: ");
                        Util.send(b.getPlayer(), m.getMessage().replace("@pl", b.getPlayer().getName()));
                        Util.send(b.getPlayer(), "&m&7----------------------------");
                        UIManager.closeUI(b.getPlayer());
                    })
                    .build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static GooeyPage PlayerPage(Player p) {
        GooeyButton loginMessages = GooeyButton.builder()
                .display(new ItemStack(Items.EMERALD))
                .title(Util.formattedString("&aLogin Messages"))
                .lore(Util.formattedArrayList(Arrays.asList("&7Click to select your login message")))
                .onClick(b -> {
                    UIManager.openUIForcefully(b.getPlayer(), LoginMessagesPage(p));
                })
                .build();

        GooeyButton logoutMessages = GooeyButton.builder()
                .display(new ItemStack(Items.REDSTONE))
                .title(Util.formattedString("&4Logout Messages"))
                .lore(Util.formattedArrayList(Arrays.asList("&7Click to select your logout message")))
                .onClick(b -> {
                    UIManager.openUIForcefully(b.getPlayer(), LogoutMessagesPage(p));
                })
                .build();

        Template template = ChestTemplate.builder(3)
                .checker(0, 0, 3, 9, filler, fillerTwo)
                .set(1, 3, loginMessages)
                .set(1, 5, logoutMessages)
                .build();

        return LinkedPage.builder().title(Util.formattedString("&7Messages")).template(template).build();
    }

    public static LinkedPage LoginMessagesPage(Player p) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.LtradeHolderLeft))
                .title("Previous Page")
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.tradeHolderRight))
                .title("Next Page")
                .linkType(LinkType.Next)
                .build();
        GooeyButton backButton = GooeyButton.builder()
                .title(Util.formattedString("&cGo Back"))
                .lore(Util.formattedArrayList(Arrays.asList("&bClick to go back to the previous page")))
                .display(new ItemStack(PixelmonItemsHeld.ejectButton))
                .onClick(b -> {
                    UIManager.openUIForcefully(b.getPlayer(), PlayerPage(p));
                })
                .build();
        Template template = null;

        if (loginMessages(p).size() > 18) {
            template = ChestTemplate.builder(5)
                    .checker(0, 0, 5, 9, filler, fillerTwo)
                    .set(4, 3, previous)
                    .set(4, 4, backButton)
                    .set(4, 5, next)
                    .rectangle(1, 1, 3, 7, placeHolderButton)
                    .build();
        } else {
            template = ChestTemplate.builder(5)
                    .checker(0, 0, 5, 9, filler, fillerTwo)
                    .set(4, 4, backButton)
                    .rectangle(1, 1, 3, 7, placeHolderButton)
                    .build();
        }

        return PaginationHelper.createPagesFromPlaceholders(template, loginMessages(p), LinkedPage.builder().title(Util.formattedString("&aLogin Messages")).template(template));
    }

    public static LinkedPage LogoutMessagesPage(Player p) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.LtradeHolderLeft))
                .title("Previous Page")
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.tradeHolderRight))
                .title("Next Page")
                .linkType(LinkType.Next)
                .build();

        GooeyButton backButton = GooeyButton.builder()
                .title(Util.formattedString("&cGo Back"))
                .lore(Util.formattedArrayList(Arrays.asList("&bClick to go back to the previous page")))
                .display(new ItemStack(PixelmonItemsHeld.ejectButton))
                .onClick(b -> {
                    UIManager.openUIForcefully(b.getPlayer(), PlayerPage(p));
                })
                .build();
        Template template = null;

        if (logoutMessages(p).size() > 18) {
            template = ChestTemplate.builder(5)
                    .checker(0, 0, 5, 9, filler, fillerTwo)
                    .set(4, 3, previous)
                    .set(4, 5, next)
                    .set(4, 4, backButton)
                    .rectangle(1, 1, 3, 7, placeHolderButton)
                    .build();
        } else {
            template = ChestTemplate.builder(5)
                    .checker(0, 0, 5, 9, filler, fillerTwo)
                    .set(4, 4, backButton)
                    .rectangle(1, 1, 3, 7, placeHolderButton)
                    .build();
        }

        return PaginationHelper.createPagesFromPlaceholders(template, logoutMessages(p), LinkedPage.builder().title(Util.formattedString("&4Logout Messages")).template(template));
    }


}
