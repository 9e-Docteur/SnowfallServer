package fr.ninedocteur.snowfall.api.plugin;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.api.mod.ApareMod;
import be.ninedocteur.apare.api.mod.Mod;
import be.ninedocteur.apare.utils.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ninedocteur.snowfall.Snowfall;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    private static String userDir = System.getProperty("user.home");
    private static String commonFolder = userDir + "/ApareAPI/Plugins/common/";
    private static boolean isLoaded;
    private static List<String> REQUIRED_FIELD = new ArrayList<>();
    public static HashMap<String, Boolean> MOD_LOADED = new HashMap<>();

    public PluginLoader(){
        if(!isLoaded){
            ApareAPI.getLogger().send("Starting mod loader...", Logger.Type.WARN);
            REQUIRED_FIELD.add("mod_name");
            REQUIRED_FIELD.add("mod_version");
            REQUIRED_FIELD.add("authors");
            REQUIRED_FIELD.add("credits");
            REQUIRED_FIELD.add("description");
        }
    }

    public void loadPlugins() {
        if(!isLoaded){
            ApareAPI.getLogger().send("Looking for plugins...", Logger.Type.WARN);
            File folder = new File(String.valueOf(Snowfall.getOrCreatePluginFolder()));
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));

            if (files == null) {
                System.out.println("No plugins found");
                return;
            }

            for (File file : files) {
                try {
                    URL url = file.toURI().toURL();
                    URLClassLoader loader = new URLClassLoader(new URL[] { url });

                    ObjectMapper mapper = new ObjectMapper();

                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().equals("plugins.json")) {
                            JsonNode modNode = mapper.readTree(jarFile.getInputStream(entry));
                            validatePluginJson(file.getName() , modNode);
                        }
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                            try {
                                Class<?> clazz = loader.loadClass(className);
                                if (clazz.isAnnotationPresent(Plugin.class)) {
                                    Object instance = clazz.getAnnotation(Plugin.class).value().newInstance();
                                    Plugin modAnnonation = clazz.getAnnotation(Plugin.class);
                                    if (instance instanceof SnowfallPlugin) {
                                        ((SnowfallPlugin) instance).init();
                                        ApareAPI.getLogger().send("Finded plugin: " + ((SnowfallPlugin) instance).getPluginName(), Logger.Type.WARN);
                                    }
                                }
                            } catch (ClassNotFoundException e) {
                                System.out.println("Failed to load class " + className + " from " + file.getName());
                            }
                        }
                    }

                    jarFile.close();
                    loader.close();

                } catch (IOException e) {
                    System.out.println("Failed to load mod from " + file.getName() + ": " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException e) {
                    System.out.println("Failed to load mod from " + file.getName() + ": " + e.getMessage());
                }
            }
            isLoaded = true;
        }
    }


    public static File getOrCreateModDir() {
        File imagesDir = new File(commonFolder);
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static File getOrCreateModDir(String modInstance) {
        String modInstanceFolder = userDir + "/ApareAPI/plugins/";
        File imagesDir = new File(modInstanceFolder + modInstance + "/");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static String getModFileDir() {
        return commonFolder;
    }

    public static void setModFileDir(String fileDir) {
        PluginLoader.commonFolder = fileDir;
    }

    private static boolean validatePluginJson(String modName, JsonNode modNode) {
        for(String requiredString : REQUIRED_FIELD){
            if(modNode.findParent(requiredString) == null){
                throw new RuntimeException("Missing a value: " + requiredString + " in plugins.json from mod " + modName);
            }
        }
        return true;
    }
}
