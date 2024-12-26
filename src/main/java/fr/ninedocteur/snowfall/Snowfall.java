package fr.ninedocteur.snowfall;

import be.ninedocteur.apare.AIN;
import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.api.event.EventFactory;
import be.ninedocteur.apare.api.mod.ApareMod;
import be.ninedocteur.apare.events.APIStartingEvent;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;
import be.ninedocteur.apare.utils.ColorUtils;

import be.ninedocteur.apare.utils.logger.Logger;
import be.ninedocteur.apare.utils.logger.LoggerInstance;
import be.ninedocteur.apare.utils.tick.Ticker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.webkit.plugin.PluginManager;
import fr.ninedocteur.snowfall.api.TargetApareVersion;
import fr.ninedocteur.snowfall.api.plugin.Plugin;
import fr.ninedocteur.snowfall.api.plugin.PluginLoader;
import fr.ninedocteur.snowfall.api.plugin.SnowfallPlugin;
import fr.ninedocteur.snowfall.command.CommandSystem;
import fr.ninedocteur.snowfall.event.SnowfallShuttingDownEvent;
import fr.ninedocteur.snowfall.event.SnowfallStartingEvent;
import fr.ninedocteur.snowfall.utils.TargetVersionUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@TargetApareVersion(version = "1.4.7")
public class Snowfall {

    private static PluginLoader pluginLoader;
    private static SnowfallServer server;
    public static CommandSystem commandSystem;
    private static SnowfallConfig snowfallConfig = new SnowfallConfig(getOrCreateConfig());
    private static Thread console;
    private static Logger logger = new LoggerInstance().create("Snowfall");
    private static List<ApareMod> loadedMods = new ArrayList<>();
    private static List<SnowfallPlugin> loadedPlugins = new ArrayList<>();

    public static void main(String[] args) {
        console = new Thread(() -> {
            TargetVersionUtils.start();
            String[] jvmArgs = {Arrays.toString(args)};
            server = new SnowfallServer(jvmArgs, SnowfallConfig.SERVER_PORT);
            server.startServer();
            pluginLoader = new PluginLoader();
            pluginLoader.loadPlugins();
            SnowfallStartingEvent event = new SnowfallStartingEvent(server);
            ApareAPI.getEventFactory().fireEvent(event);
            ApareAPI.getEventFactory().addListener(SnowfallServer::onClientConnecting);
            CommandSystem.init();
            commandSystem = new CommandSystem();
            Scanner scanner = new Scanner(System.in);
            Snowfall.getLogger().send("Started Snowfall with :", Logger.Type.NORMAL);
            Snowfall.getLogger().send("Plugin(s): " + ColorUtils.PURPLE_BOLD_BRIGHT + "Cannot show it for now...", Logger.Type.NORMAL);
            Snowfall.getLogger().send("Mod(s): " + ColorUtils.PURPLE_BOLD_BRIGHT + "Cannot show it for now...", Logger.Type.NORMAL);
            Snowfall.getLogger().send("Snowfall Server started! You can now execute command.", Logger.Type.SUCCESS);
            if(ApareAPI.getJavaArgs().containsArg("no-uptime")){
                UptimeManager.isUptimeAllowed = false;
            }
            while(true){
                String input = scanner.nextLine();
                commandSystem.scanCommand(input);
            }
        });
        console.start();
    }

    public static void stop(){
        Snowfall.getLogger().send(ColorUtils.RED_UNDERLINED + ColorUtils.RED_BACKGROUND_BRIGHT + "Shutting down...", Logger.Type.ERROR);
        SnowfallShuttingDownEvent snowfallShuttingDownEvent = new SnowfallShuttingDownEvent(server);
        ApareAPI.getEventFactory().fireEvent(snowfallShuttingDownEvent);
        System.exit(1);
    }

    public static File getOrCreatePluginFolder() {
        String jarPath = Snowfall.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        try {
            jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File jarFile = new File(jarPath);
        File parentDirectory = jarFile.getParentFile();

        File pluginFolder = new File(parentDirectory, "plugins");
        if (!pluginFolder.exists()) {
            boolean created = pluginFolder.mkdirs();
            if (!created) {
                System.err.println("Unable to create folder 'plugins'");
            }
        }

        return pluginFolder;
    }

    public static File getOrCreateConfig() {
        String jarPath = Snowfall.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        try {
            jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File jarFile = new File(jarPath);
        File parentDirectory = jarFile.getParentFile();

        File configFile = new File(parentDirectory, "config.properties");
        if (!configFile.exists()) {
            try{
                boolean created = configFile.createNewFile();
                if (!created) {
                    System.err.println("Unable to create file 'config'");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return configFile;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static SnowfallServer getServer() {
        return server;
    }

    public static SnowfallConfig getSnowfallConfig() {
        return snowfallConfig;
    }

    public static CommandSystem getCommandSystem() {
        return commandSystem;
    }

    public static List<SnowfallPlugin> getLoadedPlugins() {
        return loadedPlugins;
    }

    public static List<ApareMod> getLoadedMods() {
        return loadedMods;
    }
}
