package fr.ninedocteur.snowfall.command;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.Logger;
import fr.ninedocteur.snowfall.command.command.StopCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSystem {
    private static List<Command> REGISTERED_COMMANDS = new ArrayList<>();
    private static boolean isInited;

    public static void init(){
        if(!isInited){
            registerCommand(new StopCommand());
        }
    }

    public static void scanCommand(String input){
        String[] formattedCmd = input.split(" ");
        String cmdName = formattedCmd[0];
        boolean found = false;
        for (Command command : REGISTERED_COMMANDS) {
            if (cmdName.equalsIgnoreCase(command.getCommandName())) {
                found = true;
                String[] withoutCmdName = Arrays.stream(formattedCmd).filter(val -> !val.equals(cmdName)).toArray(String[]::new);
                command.execute(withoutCmdName);
                break;
            }
        }
        if (!found) {
            ApareAPI.getLogger().send("Command not found", Logger.Type.ERROR);
        }
    }

    public static void registerCommand(Command command){
        REGISTERED_COMMANDS.add(command);
    }
}
