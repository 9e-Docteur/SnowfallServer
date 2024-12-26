package fr.ninedocteur.snowfall.command.command;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.ColorUtils;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;
import fr.ninedocteur.snowfall.command.ICommandExecutor;

public class FetchClientCommand extends Command {

    public FetchClientCommand() {
        super("To know how many clients are connected to the server");
    }

    @Override
    public String getPrefix() {
        return "fetch-client";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.getLogger().send(ColorUtils.YELLOW_BOLD_BRIGHT + "There's " + ColorUtils.RED_BRIGHT + Snowfall.getServer().getOnlineClientCount() + ColorUtils.YELLOW_BOLD_BRIGHT + " online clients.", Logger.Type.NORMAL);
    }
}
