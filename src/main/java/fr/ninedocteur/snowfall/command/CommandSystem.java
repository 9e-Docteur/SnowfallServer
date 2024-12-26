package fr.ninedocteur.snowfall.command;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.command.*;
import fr.ninedocteur.snowfall.event.CommandExecutedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSystem {
    private static List<Command> REGISTERED_COMMANDS = new ArrayList<>();
    private static boolean isInited;

    public static void init(){
        if(!isInited){
            registerCommand(new StopCommand());
            registerCommand(new FetchClientCommand());
            registerCommand(new HardCheckCommand());
            registerCommand(new GetIPCommand());
            registerCommand(new CrashCommand());
            registerCommand(new HelpCommand());
            registerCommand(new UptimeCommand());
        }
    }

    public static void scanCommand(String input){
        String[] formattedCmd = input.split(" ");
        String cmdName = formattedCmd[0];
        boolean found = false;
        for (Command command : REGISTERED_COMMANDS) {
            if (cmdName.equalsIgnoreCase(command.getPrefix())) {
                found = true;
                String[] withoutCmdName = Arrays.stream(formattedCmd).filter(val -> !val.equals(cmdName)).toArray(String[]::new);
                command.execute(withoutCmdName);
                CommandExecutedEvent event = new CommandExecutedEvent(input);
                ApareAPI.getEventFactory().fireEvent(event);
                break;
            }
        }
        if (!found) {
            Snowfall.getLogger().send("Command not found", Logger.Type.ERROR);
        }
    }

    public static void registerCommand(Command command){
        REGISTERED_COMMANDS.add(command);
    }

    public List<Command> getRegisteredCommands() {
        return REGISTERED_COMMANDS;
    }
}
