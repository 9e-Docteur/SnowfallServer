package fr.ninedocteur.snowfall;

import be.ninedocteur.apare.utils.tick.ITicker;
import be.ninedocteur.apare.utils.tick.Ticker;

@Ticker
public class UptimeManager implements ITicker {
    public static int uptime = 0;
    public static boolean isUptimeAllowed;

    @Override
    public void tick() {
        if(isUptimeAllowed){
            uptime++;
        }
    }
}
