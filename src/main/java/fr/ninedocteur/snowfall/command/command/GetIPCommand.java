package fr.ninedocteur.snowfall.command.command;


import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.ColorUtils;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;

public class GetIPCommand extends Command {
    @Override
    public String getPrefix() {
        return "getip";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.getLogger().send("IP: " + ColorUtils.CYAN_BOLD_BRIGHT + Snowfall.getServer().getIP(), Logger.Type.NORMAL);
    }
}
