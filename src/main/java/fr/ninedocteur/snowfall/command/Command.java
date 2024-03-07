package fr.ninedocteur.snowfall.command;

public abstract class Command implements ICommandExecutor{
    private String[] arguments;

    public void appendArguments(String[] args){
        this.arguments = args;
    }

    public String[] getArguments() {
        return arguments;
    }
}
