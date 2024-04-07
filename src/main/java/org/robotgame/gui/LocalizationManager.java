package org.robotgame.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static ResourceBundle messages;
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/config.properites";
    private static final String BUNDLE_BASE_NAME = "messages";
    private static final Object lock = new Object();


    static {
        loadResourceBundle();
    }

    private static void loadResourceBundle() {
        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream(PROPERTIES_FILE_PATH);
            synchronized (lock) {
                props.load(in);
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Locale locale;
        switch (props.getProperty("Language")) {
            case "EN":
                locale = Locale.ENGLISH;
                break;
            case "RU":
                locale = new Locale("ru");
                break;
            default:
                locale = Locale.getDefault();
                break;
        }

        messages = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    public static String getString(String key) {
        return messages.getString(key);
    }

    public static synchronized boolean changeLanguage(String language) {
        Properties props = new Properties();
        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE_PATH);
             FileInputStream in = new FileInputStream(PROPERTIES_FILE_PATH)) {
            synchronized (lock) {
                props.load(in);
                props.setProperty("Language", language);
                props.store(out, null);
            }
            loadResourceBundle();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}