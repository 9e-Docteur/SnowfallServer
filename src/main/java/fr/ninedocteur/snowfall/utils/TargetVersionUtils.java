package fr.ninedocteur.snowfall.utils;

import be.ninedocteur.apare.ApareAPI;

import be.ninedocteur.apare.utils.SharedConstants;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.api.TargetApareVersion;
import fr.ninedocteur.snowfall.api.TargetSnowfallVersion;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TargetVersionUtils {
    private static boolean isStarted;
    public static void start() {
            List<String> packages = getPackages();
            for (String packageName : packages) {
                List<Class<?>> classes = getClasses(packageName);

                for (Class<?> clazz : classes) {
                    if (clazz.isAnnotationPresent(TargetApareVersion.class)) {
                        TargetApareVersion targetApareVersion = clazz.getAnnotation(TargetApareVersion.class);
                        Integer parseVersion = Integer.valueOf(targetApareVersion.version().replace(".", ""));
                        Integer parseCurrentVersion = Integer.valueOf(SharedConstants.VERSION.replace(".", ""));
                        if(parseCurrentVersion > parseVersion){
                            if(targetApareVersion.supportHigher()){
                                Snowfall.getLogger().send(clazz.getName() + " is supported since version " + parseVersion + " but because it support higher version, it may be broken on newer release. Caution!", Logger.Type.WARN);
                            } else {
                                throw new RuntimeException(clazz.getName() + " is not compatible with this version of ApareAPI \nYou have version: " + SharedConstants.VERSION + " and " + clazz.getName() + " need: " + targetApareVersion.version());
                            }
                        } else if (parseCurrentVersion < parseVersion) {
                            if(targetApareVersion.supportLower()){
                                Snowfall.getLogger().send(clazz.getName() + " is supported since version " + parseVersion + " but because it support lower version, it may be broken on older release. Caution!", Logger.Type.WARN);
                            } else {
                                throw new RuntimeException(clazz.getName() + " is not compatible with this version of ApareAPI \nYou have version: " + SharedConstants.VERSION + " and " + clazz.getName() + " need: " + targetApareVersion.version());
                            }
                        } else {
                            throw new RuntimeException(clazz.getName() + " is not compatible with this version of ApareAPI \nYou have version: " + SharedConstants.VERSION + " and " + clazz.getName() + " need: " + targetApareVersion.version());
                        }
                    }
                    if (clazz.isAnnotationPresent(TargetSnowfallVersion.class)) {
                        TargetSnowfallVersion targetSnowfallVersion = clazz.getAnnotation(TargetSnowfallVersion.class);
                        Integer parseVersion = Integer.valueOf(targetSnowfallVersion.snowfallRequiredVersion().replace(".", ""));
                        Integer parseCurrentVersion = Integer.valueOf(targetSnowfallVersion.yourContentVersion().replace(".", ""));
                        if(parseCurrentVersion > parseVersion){
                            if(targetSnowfallVersion.supportHigher()){
                                Snowfall.getLogger().send(clazz.getName() + " is supported since version " + parseVersion + " but because it support higher version, it may be broken on newer release. Caution!", Logger.Type.WARN);
                            } else {
                                throw new RuntimeException(clazz.getName() + " is not compatible with this version of SnowfallServer \nYou have version: " + targetSnowfallVersion.yourContentVersion() + " and " + clazz.getName() + " need: " + targetSnowfallVersion.snowfallRequiredVersion());
                            }
                        } else if (parseCurrentVersion < parseVersion) {
                            if(targetSnowfallVersion.supportLower()){
                                Snowfall.getLogger().send(clazz.getName() + " is supported since version " + parseVersion + " but because it support lower version, it may be broken on older release. Caution!", Logger.Type.WARN);
                            } else {
                                throw new RuntimeException(clazz.getName() + " is not compatible with this version of SnowfallServer \nYou have version: " + targetSnowfallVersion.yourContentVersion() + " and " + clazz.getName() + " need: " + targetSnowfallVersion.snowfallRequiredVersion());
                            }
                        } else {
                            throw new RuntimeException(clazz.getName() + " is not compatible with this version of SnowfallServer \nYou have version: " + targetSnowfallVersion.yourContentVersion() + " and " + clazz.getName() + " need: " + targetSnowfallVersion.snowfallRequiredVersion());
                        }
                    }
                }
            }
    }

    private static List<String> getPackages() {
        List<String> packages = new ArrayList<>();
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        for (String classpathEntry : classpathEntries) {
            File entry = new File(classpathEntry);
            if (entry.isDirectory()) {
                exploreDirectory(entry, "", packages);
            }
        }
        return packages;
    }

    private static void exploreDirectory(File directory, String parentPackage, List<String> packages) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String packageName = parentPackage.isEmpty() ? file.getName() : parentPackage + "." + file.getName();
                    packages.add(packageName);
                    exploreDirectory(file, packageName, packages);
                }
            }
        }
    }

    private static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File directory = new File(resource.toURI());
                    if (directory.isDirectory()) {
                        String[] files = directory.list();
                        for (String fileName : files) {
                            if (fileName.endsWith(".class")) {
                                String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                                Class<?> clazz = Class.forName(className);
                                classes.add(clazz);
                            }
                        }
                    }
                } else if (resource.getProtocol().equals("jar")) {
                    JarFile jarFile = new JarFile(resource.getFile());
                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                            try {
                                String clazzName = packageName + '.' + entry.getName().substring(0, entry.getName().length() - 6);
                                Class<?> clazz = Class.forName(className);
                                classes.add(clazz);
                            } catch (ClassNotFoundException e) {
                                System.out.println("Failed to load class " + className + " from " + entry.getName());
                            }
                        }
                    }

                    jarFile.close();

                }
            }
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        return classes;
    }

}
