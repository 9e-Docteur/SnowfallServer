package fr.ninedocteur.snowfall.utils;

import fr.ninedocteur.snowfall.Snowfall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private File file;
    private static String userHomeDirectory = System.getProperty("user.home");
    private static File apareAPIDirectory = new File(userHomeDirectory, "ApareAPI");

    public FileManager(String fileName) {
        if (!apareAPIDirectory.exists()) {
            apareAPIDirectory.mkdirs();
        }
        this.file = new File(apareAPIDirectory, fileName);
        fileCheck();
    }

    public FileManager(File file) {
        if (!apareAPIDirectory.exists()) {
            apareAPIDirectory.mkdirs();
        }
        this.file = file;
        fileCheck();
    }

    public boolean writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readFile() {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public int getLineCount() {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (; reader.readLine() != null; lineCount++) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    public boolean fileCheck() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLine(int lineNumber) {
        if (lineNumber < 1) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int currentLine;
            for (currentLine = 1; (line = reader.readLine()) != null; currentLine++) {
                if (currentLine == lineNumber) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserHomeDirectory() {
        return userHomeDirectory;
    }

    public File getBlackWoodDirectory() {
        return apareAPIDirectory;
    }

    public List<String> readFileAsList() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static FileManager getLocalGameDataFile(String fileName) {
        FileManager data = null;
        URI uri = null;
        Path path = null;

        try {
            uri = Snowfall.class.getResource("/" + fileName).toURI();
            path = Paths.get(uri);
            data = new FileManager(path.getFileName().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static FileManager getFile(File file){ //Outside the resources folder of the game (in BlackWood Folder)
        return new FileManager(file);
    }

    public File getFile() {
        return file;
    }
}
