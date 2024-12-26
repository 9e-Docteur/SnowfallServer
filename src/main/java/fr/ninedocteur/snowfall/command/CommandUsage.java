package fr.ninedocteur.snowfall.command;

import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;

import java.util.HashMap;
import java.util.Map;

public class CommandUsage {
    private HashMap<String, String> COMMANDS_ARGS_DESCRIPTION = new HashMap<>();

    public void registerCommandArgsAndDescription(String commandargs, String description) {
        this.COMMANDS_ARGS_DESCRIPTION.put(commandargs, description);
    }

    public void printUsages() {
        for(Map.Entry<String, String> entry : this.COMMANDS_ARGS_DESCRIPTION.entrySet()) {
            Snowfall.getLogger().send("       " + entry.getKey() + " -- " + entry.getValue(), Logger.Type.NORMAL);
        }
    }
}
