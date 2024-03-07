package fr.ninedocteur.snowfall;

import be.ninedocteur.apare.AIN;
import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.api.event.ApareEventHandler;
import be.ninedocteur.apare.network.events.ClientJoiningServerEvent;
import be.ninedocteur.apare.server.ApareAPIServer;
import be.ninedocteur.apare.server.ServerConnection;
import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.api.TargetApareVersion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

public class SnowfallServer extends ApareAPIServer {
    private int port;
    public SnowfallServer(String[] args, int port) {
        super(args, port);
        this.port = port;
    }

    public int getOnlineClientCount(){
        return getOnlineClients().size();
    }

    public HashMap<Integer, ServerConnection> getOnlineClients(){
        return this.connections;
    }

    public int getPort() {
        return port;
    }

    public String getIP()  {
        if(Snowfall.getSnowfallConfig().get("allow-outside-request").equalsIgnoreCase("true")){
            try{
                URL url = new URL("http://checkip.amazonaws.com");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                return br.readLine();
            } catch (IOException e) {
                return e.getMessage();
            }
        } else {
            Snowfall.getLogger().send("Cannot contact http://checkip.amazonaws.com to get the ip adress! Config allow-outside-request is not set to true!", Logger.Type.ERROR);
        }
        return null;
    }

    @ApareEventHandler
    public static void onClientConnecting(ClientJoiningServerEvent event){
        if(Snowfall.getServer().getOnlineClientCount() >= Integer.parseInt(Snowfall.getSnowfallConfig().get("max-clients"))){
            event.getConnection().close();
        }
    }
}
