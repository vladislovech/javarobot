package org.robotgame.gui;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static ResourceBundle messages;

    static {

        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream("src/main/resources/config.properites");

            props.load(in);
            in.close();

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

        messages = ResourceBundle.getBundle("messages", locale);
    }

    public static String getString(String key) {
        return messages.getString(key);
    }

    public static void changeLanguage( String language) {
        Properties props = new Properties();
        try {
            FileOutputStream out = new FileOutputStream("src/main/resources/config.properites");
            FileInputStream in = new FileInputStream("src/main/resources/config.properites");

            props.load(in);
            in.close();

            props.setProperty("Language", language);
            props.store(out, null);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
