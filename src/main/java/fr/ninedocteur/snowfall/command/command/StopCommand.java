package fr.ninedocteur.snowfall.command.command;


import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;

public class StopCommand extends Command {

    public StopCommand() {
        super("Stop the server");
    }

    @Override
    public String getPrefix() {
        return "stop";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.stop();
    }
}
