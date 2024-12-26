package fr.ninedocteur.snowfall.command.command;


import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.ColorUtils;
import be.ninedocteur.apare.utils.DevicesInfos;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.command.Command;

public class HardCheckCommand extends Command {

    public HardCheckCommand() {
        super("Check the hardware");
    }

    @Override
    public String getPrefix() {
        return "hardcheck";
    }

    @Override
    public void execute(String[] args) {
        Snowfall.getLogger().send("-------------- Snowfall Server Hardware Check", Logger.Type.WARN);
        Snowfall.getLogger().send("RAM: " + ColorUtils.BLUE_BRIGHT +  DevicesInfos.getOccupiedMemory() + "MB/" + DevicesInfos.getFreeMemory() + "MB", Logger.Type.NORMAL);
        Snowfall.getLogger().send("CPU Load: " + ColorUtils.BLUE_BRIGHT +  DevicesInfos.getProcessorLoad(), Logger.Type.NORMAL);
    }
}
