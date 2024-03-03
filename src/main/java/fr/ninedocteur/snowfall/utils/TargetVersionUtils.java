package fr.ninedocteur.snowfall.utils;

import fr.ninedocteur.snowfall.api.TargetApareVersion;

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
                        //Integer parseVersion = Integer.valueOf(targetApareVersion.version().replace(".", ""));
                        if (!targetApareVersion.version().equalsIgnoreCase("1.4")) {
                            throw new RuntimeException("Snowfall is not compatible with this version of ApareAPI \nYou have version: " + "v" + " and Snowfall need: " + targetApareVersion.version());
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
                    // Si le chemin est un fichier, c'est probablement un dossier dans le système de fichiers
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
