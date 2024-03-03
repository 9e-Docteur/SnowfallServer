package fr.ninedocteur.snowfall.command;

public abstract class Command implements ICommandExecutor{
    private String commandName;
    private String commandPrefix;
    private String[] arguments;

    public Command(String commandName){
        this.commandName = commandName;
    }

    public void appendArguments(String[] args){
        this.arguments = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }
}
