package fr.ninedocteur.snowfall;

import be.ninedocteur.apare.server.ApareAPIServer;
import fr.ninedocteur.snowfall.api.TargetApareVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class SnowfallServer extends ApareAPIServer {
    private int port;
    public SnowfallServer(String[] args, int port) {
        super(args, port);
        this.port = port;
    }

    public int getOnlineClient(){
        return this.connections.size();
    }

    public int getPort() {
        return port;
    }

    public String getIP()  {
        try{
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
