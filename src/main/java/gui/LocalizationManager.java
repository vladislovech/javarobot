package gui;

import java.util.*;

public class LocalizationManager {
    private Locale currentLocale;
    private ResourceBundle resourceBundle;

    public LocalizationManager(Locale locale) {
        setLocale(locale);
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        this.resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    public String getFormattedString(String key, Object... args) {
        String pattern = getString(key);
        return CachedMessageFormat.format(pattern, args);
    }
}
