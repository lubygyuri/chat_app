package hu.alkfejl.config;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Configuration.class.getResourceAsStream("/application.properties"));
        } catch (IOException exception) {
            System.err.println("Nem található a megadott config fájl: " + exception.getMessage());
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
