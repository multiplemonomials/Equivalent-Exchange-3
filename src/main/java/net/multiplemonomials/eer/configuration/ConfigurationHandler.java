package net.multiplemonomials.eer.configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static void init(String configPath)
    {
        ClientConfiguration.init(new File(configPath + "client.properties"));
    }
}
