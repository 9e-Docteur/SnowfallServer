package fr.ninedocteur.snowfall.utils;

import fr.ninedocteur.snowfall.Snowfall;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static File getFileInResourceFolder(String filename){ //Inside the resource folder of the program
        File data = null;
        URI uri = null;
        Path path = null;

        try {
            uri = Snowfall.class.getResource("/" + filename).toURI();
            path = Paths.get(uri);
            data = new File(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return data;
    }

}
