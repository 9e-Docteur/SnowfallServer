package fr.ninedocteur.snowfall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SnowfallConfig {
    private File file;

    public static int SERVER_PORT, MAX_CLIENTS;
    public static boolean ALLOW_OUTSIDE_REQUEST;

    private Properties properties;
    private HashMap<String, String> CONFIGS = new HashMap<>(); //CONFIG; DEFAULT VALUE

    public SnowfallConfig(File file)
    {
        CONFIGS.put("server-port", "8888");
        CONFIGS.put("max-clients", "20");
        CONFIGS.put("allow-outside-request", "true");

        this.file = file;
        this.properties = new Properties();

        if (file.exists())
            load();

        for(Map.Entry<String, String> entry : CONFIGS.entrySet()){
            if(get(entry.getKey()) == null){
                set(entry.getKey(), entry.getValue());
            }
        }
    }
    public void set(String key, String value)
    {
        properties.setProperty(key, value);
        save();
    }
    public String get(String key)
    {
        return properties.getProperty(key);
    }

    public String get(String key, String def)
    {
        String value = properties.getProperty(key);
        return value == null ? def : value;
    }

    public void save()
    {
        try
        {
            properties.store(new BufferedWriter(new FileWriter(file)), "Snowfall Config");
        }
        catch (Throwable t)
        {
            throw new RuntimeException("Can't save the properties", t);
        }
    }

    public void load()
    {
        try
        {
            properties.load(new FileInputStream(file));
            loadVariables();
        }
        catch (Throwable t)
        {
            throw new RuntimeException("Can't load the properties", t);
        }
    }

    public void loadVariables(){
        this.SERVER_PORT = Integer.parseInt(properties.getProperty("server-port"));
        this.MAX_CLIENTS = Integer.parseInt(properties.getProperty("max-clients"));
        this.ALLOW_OUTSIDE_REQUEST = Boolean.parseBoolean(properties.getProperty("allow-outside-request"));
    }
}
