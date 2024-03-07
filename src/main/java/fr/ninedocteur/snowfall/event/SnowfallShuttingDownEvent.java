package fr.ninedocteur.snowfall.event;

import be.ninedocteur.apare.api.event.Event;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.SnowfallServer;

public class SnowfallShuttingDownEvent extends Event {
    private SnowfallServer snowfallServer;

    public SnowfallShuttingDownEvent(SnowfallServer snowfallServer){
        this.snowfallServer = snowfallServer;
    }

    public SnowfallServer getSnowfallServer() {
        return snowfallServer;
    }
}
