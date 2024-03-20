package org.robotgame.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static ResourceBundle messages;

    static {
        Locale locale = Locale.getDefault();
        messages = ResourceBundle.getBundle("messages", locale);
    }

    public static String getString(String key) {
        return messages.getString(key);
    }
}
