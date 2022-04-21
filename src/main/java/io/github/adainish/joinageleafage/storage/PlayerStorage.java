package io.github.adainish.joinageleafage.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.joinageleafage.JoinageLeafage;
import io.github.adainish.joinageleafage.obj.Player;
import io.github.adainish.joinageleafage.util.Adapters;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.*;
import java.util.UUID;

public class PlayerStorage {

    public static void makePlayer(EntityPlayerMP player) {
        File dir = JoinageLeafage.getDataDir();
        dir.mkdirs();


        Player pp = new Player(player.getUniqueID());

        File file = new File(dir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUniqueID())));
        if (file.exists()) {
            JoinageLeafage.log.error("There was an issue generating the Player, Player already exists? Ending function");
            return;
        }

        Gson gson = Adapters.PRETTY_MAIN_GSON;
        String json = gson.toJson(pp);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayer(Player player) {

        File dir = JoinageLeafage.getDataDir();
        dir.mkdirs();

        File file = new File(dir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUuid())));
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (reader == null) {
            JoinageLeafage.log.error("Something went wrong attempting to read the Player Data");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(player));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.updateCache();
    }

    public static Player getPlayer(UUID uuid) {
        File dir = JoinageLeafage.getDataDir();
        dir.mkdirs();

        if (JoinageLeafage.onlinePlayers.containsKey(uuid))
            return JoinageLeafage.onlinePlayers.get(uuid);

        File guildFile = new File(dir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(uuid)));
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(guildFile));
        } catch (FileNotFoundException e) {
            JoinageLeafage.log.error("Something went wrong attempting to read the Player Data, new Player Perhaps?");
            return null;
        }

        return gson.fromJson(reader, Player.class);
    }

}
