package fr.ninedocteur.snowfall.api.plugin;

public abstract class SnowfallPlugin {
    private String pluginName;

    public SnowfallPlugin(String pluginName){
        this.pluginName = pluginName;
    }

    public abstract void init();

    public String getPluginName() {
        return pluginName;
    }
}
