package fr.ninedocteur.snowfall.command;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;

public abstract class Command implements ICommandExecutor{
    private String[] arguments;
    public String usage;
    private String description;
    public CommandUsage commandUsage;
    private boolean argsRequired = false;

    public Command(){

    }

    public Command(String description){
        this.description = description;
    }

    public Command(String usage, String description) {
        this.usage = usage;
        this.description = description;
    }

    public Command(String usage, String description, boolean argsRequired) {
        this.usage = usage;
        this.description = description;
        this.argsRequired = argsRequired;
    }

    public Command(CommandUsage commandUsage, String description) {
        this.commandUsage = commandUsage;
        this.description = description;
    }

    public Command(CommandUsage commandUsage, String description, boolean argsRequired) {
        this.commandUsage = commandUsage;
        this.description = description;
        this.argsRequired = argsRequired;
    }

    public void appendArguments(String[] args){
        this.arguments = args;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void getUsage() {
        if(usage == null || usage == ""){
            if(commandUsage != null){
                commandUsage.printUsages();
            } else {
                Snowfall.getLogger().send("No usages for this command.", Logger.Type.ERROR);
            }
        } else {
            Snowfall.getLogger().send("Usage: " + usage, Logger.Type.WARN);
        }
    }

    public void getHelpUsage() {
        if(usage == null || usage == ""){
            if(commandUsage != null){
                commandUsage.printUsages();
            }
        } else {
            Snowfall.getLogger().send("Usage: " + usage, Logger.Type.WARN);
        }
    }

    public String getDescription() {
        return description == null || description.isEmpty() ? "" : description;
    }

    public void setCommandUsage(CommandUsage commandUsage) {
        this.commandUsage = commandUsage;
    }
}
