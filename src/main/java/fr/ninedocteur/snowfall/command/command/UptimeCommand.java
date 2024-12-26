package fr.ninedocteur.snowfall.command.command;

import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.UptimeManager;
import fr.ninedocteur.snowfall.command.Command;
import fr.ninedocteur.snowfall.command.CommandUsage;

public class UptimeCommand extends Command {
    public CommandUsage usageofthecommand = new CommandUsage();

    public UptimeCommand() {
        super("Get the time since the program is up");
        usageofthecommand.registerCommandArgsAndDescription("-c", "continuous uptime");
        setCommandUsage(usageofthecommand);
    }

    @Override
    public String getPrefix() {
        return "uptime";
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            Snowfall.getLogger().send("Uptime : " + UptimeManager.uptime, Logger.Type.NORMAL);
        } else if (args[0].equalsIgnoreCase("-c")) {
            while(true){
                Snowfall.getLogger().send("Uptime : " + UptimeManager.uptime, Logger.Type.NORMAL);
            }
        }
    }
}
