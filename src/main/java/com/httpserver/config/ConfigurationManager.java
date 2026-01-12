package com.httpserver.config;

public class ConfigurationManager {

    private static ConfigurationManager myCongigurationManager;
    private static Configuration myCurrentConfiguratuion;

    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if(myCongigurationManager == null)
            myCongigurationManager = new ConfigurationManager();

        return myCongigurationManager;
    }

    // Used to load a configuration file by the path provided
    public void loadConfigurationFile(String filePath){

    }

    // Returns the Current loaded Configuration
    public void getCurrentConfiguration(){

    }

}
