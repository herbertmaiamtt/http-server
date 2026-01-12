package com.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.httpserver.util.Json;

import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguratuion;

    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if(myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();

        return myConfigurationManager;
    }

    // Used to load a configuration file by the path provided
    public void loadConfigurationFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        StringBuffer sb = new StringBuffer();
        int i;
        while((i = fileReader.read()) != -1){
            sb.append((char)i);
        }
        JsonNode conf = Json.parse(sb.toString());
        myCurrentConfiguratuion = Json.fromJson(conf, Configuration.class);
    }

    // Returns the Current loaded Configuration
    public void getCurrentConfiguration(){

    }

}
