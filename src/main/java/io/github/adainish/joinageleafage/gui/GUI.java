package io.github.adainish.joinageleafage.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.obj.Message;
import io.github.adainish.joinageleafage.obj.Player;
import io.github.adainish.joinageleafage.util.Util;
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
            .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 7))
            .build();

    public static ItemStack playerSkull(String n) {
        ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("SkullOwner", n);
        skull.setTagCompound(tagCompound);
        return skull;
    }

    public static List <Button> buttonList() {
        List<Button> buttonList = new ArrayList <>();


//        Item it = Item.getByNameOrId(bt.getAvailableItem());
//        if (it == null)
//            continue;
//        ItemStack stack = new ItemStack(it);
//
//        GooeyButton button = GooeyButton.builder()
//                .display(stack)
//                .title(Util.formattedString(bt.getDisplay()))
//                .lore(Util.formattedArrayList(bt.getLore()))
//                .build();
//        buttonList.add(button);
        return buttonList;
    }

    public static List <Button> loginMessages(Player p) {
        List<Button> buttonList = new ArrayList <>();


        for (Message m: JoinageLeafage.getMessageHandler().getLoginList()) {
            Item it = Item.getByNameOrId(m.getItemString());
            if (it == null)
                continue;
            ItemStack stack = new ItemStack(it);

            GooeyButton button = GooeyButton.builder()
                    .display(stack)
                    .title(Util.formattedString(m.getDisplay()))
                    .lore(Util.formattedArrayList(m.getLore()))
                    .onClick(b -> {
                        p.setEnabledLoginMessageIdentifier(m.getIdentifier());
                        Util.send(b.getPlayer(), "&cYour Login message has been set to: ");
                        Util.send(b.getPlayer(), m.getMessage());
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
            Item it = Item.getByNameOrId(m.getItemString());
            if (it == null)
                continue;
            ItemStack stack = new ItemStack(it);

            GooeyButton button = GooeyButton.builder()
                    .display(stack)
                    .title(Util.formattedString(m.getDisplay()))
                    .lore(Util.formattedArrayList(m.getLore()))
                    .onClick(b -> {
                        p.setEnabledLoginMessageIdentifier(m.getIdentifier());
                        Util.send(b.getPlayer(), "&cYour Logout message has been set to: ");
                        Util.send(b.getPlayer(), m.getMessage());
                        UIManager.closeUI(b.getPlayer());
                    })
                    .build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static LinkedPage PlayerPage(Player p) {

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
                .border(0, 0, 3, 9, filler)
                .set(1, 4, loginMessages)
                .set(1, 6, logoutMessages)
                .build();

        return PaginationHelper.createPagesFromPlaceholders(template, buttonList(), LinkedPage.builder().title(Util.formattedString("&7Messages")).template(template));
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

        Template template = null;

        if (loginMessages(p).size() > 18) {
            template = ChestTemplate.builder(5)
                    .border(0, 0, 5, 9, filler)
                    .set(0, 3, previous)
                    .set(0, 5, next)
                    .rectangle(1, 1, 3, 6, placeHolderButton)
                    .build();
        } else {
            template = ChestTemplate.builder(5)
                    .border(0, 0, 5, 9, filler)
                    .rectangle(1, 1, 2, 5, placeHolderButton)
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

        Template template = null;

        if (logoutMessages(p).size() > 18) {
            template = ChestTemplate.builder(5)
                    .border(0, 0, 5, 9, filler)
                    .set(0, 3, previous)
                    .set(0, 5, next)
                    .rectangle(1, 1, 3, 6, placeHolderButton)
                    .build();
        } else {
            template = ChestTemplate.builder(5)
                    .border(0, 0, 5, 9, filler)
                    .rectangle(1, 1, 2, 5, placeHolderButton)
                    .build();
        }

        return PaginationHelper.createPagesFromPlaceholders(template, logoutMessages(p), LinkedPage.builder().title(Util.formattedString("&4Logout Messages")).template(template));
    }


}
