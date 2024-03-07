package fr.ninedocteur.snowfall.event;

import be.ninedocteur.apare.api.event.Event;
import fr.ninedocteur.snowfall.SnowfallServer;

public class SnowfallStartingEvent extends Event {
    private SnowfallServer snowfallServer;

    public SnowfallStartingEvent(SnowfallServer snowfallServer){
        this.snowfallServer = snowfallServer;
    }

    public SnowfallServer getSnowfallServer() {
        return snowfallServer;
    }
}
