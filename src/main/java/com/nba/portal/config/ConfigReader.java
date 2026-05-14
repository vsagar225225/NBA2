package com.nba.portal.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties = new Properties();

    public ConfigReader() {
        // Load default framework settings from the classpath resource.
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.properties file was not found in src/main/resources");
            }
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load config.properties", exception);
        }
    }

    public String get(String key) {
        // System properties allow Maven/Eclipse overrides such as -Dheadless=true.
        return System.getProperty(key, properties.getProperty(key));
    }

    public String get(String key, String defaultValue) {
        // Prefer runtime override, then config file value, then caller-provided default.
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    public int getInt(String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }
}
