package fr.ninedocteur.snowfall.command.command;


import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;

public class StopCommand extends Command {
    @Override
    public String getPrefix() {
        return "stop";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.stop();
    }
}
