package fr.ninedocteur.snowfall.command;

public interface ICommandExecutor {

    String getPrefix();
    void execute(String[] args);
}
