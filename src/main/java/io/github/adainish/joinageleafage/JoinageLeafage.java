package io.github.adainish.joinageleafage;

import io.github.adainish.joinageleafage.commands.Command;
import io.github.adainish.joinageleafage.config.Config;
import io.github.adainish.joinageleafage.handlers.MessageHandler;
import io.github.adainish.joinageleafage.listeners.PlayerListener;
import io.github.adainish.joinageleafage.obj.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

@Mod(
        modid = JoinageLeafage.MOD_ID,
        name = JoinageLeafage.MOD_NAME,
        version = JoinageLeafage.VERSION,
        acceptableRemoteVersions = "*"
)
public class JoinageLeafage {

    public static final String MOD_ID = "joinageleafage";
    public static final String MOD_NAME = "JoinageLeafage";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2022";
    public static Logger log = LogManager.getLogger(MOD_NAME);

    private static File configDir;
    private static File dataDir;

    private static MessageHandler messageHandler;

    @Mod.Instance(MOD_ID)
    public static JoinageLeafage INSTANCE;

    public static HashMap <UUID, Player> onlinePlayers = new HashMap <>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
        setConfigDir(new File(event.getModConfigurationDirectory() + "/"));
        getConfigDir().mkdirs();

        setDataDir(new File(getConfigDir() + "/JoinageLeafage/data"));
        getDataDir().mkdirs();

        setupConfigs();
        loadConfigs();
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        initMessageHandler();

        event.registerServerCommand(new Command());

    }

    public void setupConfigs() {
        Config.getConfig().setup();
    }

    public void loadConfigs() {
        Config.getConfig().load();
    }

    public void initMessageHandler() {
        setMessageHandler(new MessageHandler());
    }

    public void reload() {
        loadConfigs();
        initMessageHandler();
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        JoinageLeafage.configDir = configDir;
    }

    public static File getDataDir() {
        return dataDir;
    }

    public static void setDataDir(File dataDir) {
        JoinageLeafage.dataDir = dataDir;
    }

    public static MessageHandler getMessageHandler() {return messageHandler;}

    public static void setMessageHandler(MessageHandler messageHandler) {JoinageLeafage.messageHandler = messageHandler;}


}
