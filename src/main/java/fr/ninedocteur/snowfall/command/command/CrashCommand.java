package fr.ninedocteur.snowfall.command.command;


import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;
import fr.ninedocteur.snowfall.exception.CrashException;

public class CrashCommand extends Command {

    public CrashCommand(){
        super("Crash the server");
    }

    @Override
    public String getPrefix() {
        return "crash";
    }

    @Override
    public void execute(String[] args) {
        throw new CrashException("You executed /crash lol");
    }
}
