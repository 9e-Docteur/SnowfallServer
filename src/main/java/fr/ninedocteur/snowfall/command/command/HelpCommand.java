package fr.ninedocteur.snowfall.command.command;


import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;
import fr.ninedocteur.snowfall.command.CommandSystem;

import java.util.List;

public class HelpCommand extends Command {
    @Override
    public String getPrefix() {
        return "help";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.getLogger().send("Here's a list of all the command available :", Logger.Type.SUCCESS);
        for(Command command : Snowfall.commandSystem.getRegisteredCommands()){
            if(command.commandUsage == null || command.usage == null){
                if(command.getDescription() != ""){
                    Snowfall.getLogger().send(command.getPrefix() + " -- " + command.getDescription(), Logger.Type.NORMAL);
                } else {
                    Snowfall.getLogger().send(command.getPrefix(), Logger.Type.NORMAL);
                }
            } else {
                command.getHelpUsage();
            }
        }
    }
}
