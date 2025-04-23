package gui;

import java.text.MessageFormat;
import java.util.*;


public class LocalizationManager {
    private static LocalizationManager instance;
    private Locale currentLocale;
    private ResourceBundle resourceBundle;

    private LocalizationManager() {
        setLocale(new Locale("ru", "RU"));
    }

    public static synchronized LocalizationManager getInstance() {
        if (instance == null) {
            instance = new LocalizationManager();
        }
        return instance;
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        String baseName = "messages";
        try {
            ResourceBundle.clearCache();
            this.resourceBundle = ResourceBundle.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            throw new RuntimeException("Resource bundle not found for base: " + baseName +
                    " and locale: " + locale, e);
        }
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}
