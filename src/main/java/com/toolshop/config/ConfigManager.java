package com.toolshop.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {


    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            String envKey = key.toUpperCase().replace(".", "_");
            value = System.getenv(envKey);
        }
        return value;
    }
}
