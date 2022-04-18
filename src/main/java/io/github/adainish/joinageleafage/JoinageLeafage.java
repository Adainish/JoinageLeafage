package io.github.adainish.joinageleafage;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

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

    @Mod.Instance(MOD_ID)
    public static JoinageLeafage INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
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

}
