package com.httpserver;

import com.httpserver.config.Configuration;
import com.httpserver.config.ConfigurationManager;
import com.httpserver.core.ServerListenerThread;
import com.httpserver.core.io.WebRootNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

/*
*
* Driver Class for the Http Server
*
*/
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){


        if (args.length != 1) {
            LOGGER.error("No configuration file provided.");
            LOGGER.error("Syntax:  java -jar simplehttpserver-1.0-SNAPSHOT.jar  <config.json>");

            return;
        }

        LOGGER.info("Server starting...");

        // ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        ConfigurationManager.getInstance().loadConfigurationFile(args[0]);
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());

        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
        } catch (IOException e) {
            e.printStackTrace();
            // TODO handle later
        } catch(WebRootNotFoundException e){
            LOGGER.error("Webroot folder not found", e);
        }
        serverListenerThread.start();

    }

}
