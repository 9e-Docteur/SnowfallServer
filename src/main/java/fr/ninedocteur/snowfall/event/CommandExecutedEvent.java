package fr.ninedocteur.snowfall.event;

import be.ninedocteur.apare.api.event.Event;
import fr.ninedocteur.snowfall.SnowfallServer;

public class CommandExecutedEvent extends Event {
    private String input;

    public CommandExecutedEvent(String input){
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
