package fr.ninedocteur.snowfall.command.command;


import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.ColorUtils;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;

public class GetPortCommand extends Command {

    public GetPortCommand() {
        super("Get the server port");
    }

    @Override
    public String getPrefix() {
        return "getport";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.getLogger().send("Port: " + ColorUtils.CYAN_BOLD_BRIGHT + Snowfall.getServer().getPort(), Logger.Type.NORMAL);
    }
}
