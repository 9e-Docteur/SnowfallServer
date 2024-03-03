package fr.ninedocteur.snowfall;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.webkit.plugin.PluginManager;
import fr.ninedocteur.snowfall.api.TargetApareVersion;
import fr.ninedocteur.snowfall.api.plugin.Plugin;
import fr.ninedocteur.snowfall.api.plugin.PluginLoader;
import fr.ninedocteur.snowfall.api.plugin.SnowfallPlugin;
import fr.ninedocteur.snowfall.command.CommandSystem;
import fr.ninedocteur.snowfall.utils.TargetVersionUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@TargetApareVersion(version = "1.4")
public class Snowfall {

    private static PluginLoader pluginLoader;
    private static SnowfallServer server;
    private static CommandSystem commandSystem;

    public static void main(String[] args) {
        Thread console = new Thread(() -> {
            TargetVersionUtils.start();
            String[] jvmArgs = {Arrays.toString(args), "--noMods"};
            pluginLoader = new PluginLoader();
            server = new SnowfallServer(jvmArgs, 8888);
            server.startServer();
            CommandSystem.init();
            commandSystem = new CommandSystem();
            Scanner scanner = new Scanner(System.in);
            while(true){
                String input = scanner.nextLine();
                commandSystem.scanCommand(input);
            }
        });
        console.start();
    }

    public static File getOrCreatePluginFolder() {
        String jarPath = PluginManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();

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

    public static SnowfallServer getServer() {
        return server;
    }
}
