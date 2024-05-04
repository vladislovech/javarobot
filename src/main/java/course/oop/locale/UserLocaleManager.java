package course.oop.locale;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

/**
 * Утилитарный класс.
 * - хранит ресурсы выбранной локали
 * - загружает локаль пользователя из файла
 * - сохраняет локаль пользователя в файл
 */
public class UserLocaleManager {
    /**
     * Расположение локали пользователя
     */
    private static File userLocaleFile = new File(
            System.getProperty("user.home") +
                    File.separator + "Robots" +
                    File.separator + "config" +
                    File.separator + "locale.conf");

    /**
     * Хранит ресурсы со связанной локалью
     */
    private static ResourceBundle currentBundle;

    static {
        loadUserLocale();
    }

    private UserLocaleManager() {
    }

    /**
     * Возвращает текущие ресурсы со связанной локалью
     */
    public static ResourceBundle getCurrentBundle() {
        return currentBundle;
    }

    /**
     * Изменяет текущую локаль, сохраняет ее в файл конфигурации пользователя
     */
    public static void setUserLocale(UserLocale userLocale) {
        switch (userLocale) {
            case EN:
                currentBundle = ResourceBundle.getBundle("locales/en", new Locale("en"));
                break;
            case RU:
                currentBundle = ResourceBundle.getBundle("locales/ru", new Locale("ru"));
                break;
            }
        saveUserLocale(userLocale);
    }

    /**
     * Сохраняет локаль, предпочитаемую пользователем в файл конфигурации
     * $HOME/Robots/config/locale.conf (в кодировке UTF-8)
     */
    private static void saveUserLocale(UserLocale userLocale) {
        try {
            FileUtils.createParentDirectories(userLocaleFile);
        } catch (IOException e) {
            System.err.println("can't create directories to localeFile");
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(userLocaleFile), "UTF-8"))) {
            writer.write(userLocale.toString());
        } catch (IOException e) {
            System.err.println("can't save user locale" + e.getMessage());
        }
    }

    /**
     * Заполняет статическое поле currentBundle на основе содержимого
     * файла с локалью пользователя. Если файл не существует или битый,
     * устанавливает локаль по умолчанию - английскую, в
     * currentBundle загружает ресурсы на английском языке
     */
    private static void loadUserLocale() {
        switch (getUserLocaleFromLocaleFile()) {
            case EN:
                currentBundle = ResourceBundle.getBundle("locales/en", new Locale("en"));
                break;
            case RU:
                currentBundle = ResourceBundle.getBundle("locales/ru", new Locale("ru"));
                break;
        }
    }

    /**
     * Возвращает строку из первых двух символов файла
     * $HOME/Robots/config/locale.conf (в кодировке UTF-8).
     * Если файл не существует, или слишком мал, возвращает локаль по умолчанию
     * - английскую
     */
    private static UserLocale getUserLocaleFromLocaleFile() {
        try (Reader reader = new InputStreamReader(new FileInputStream(userLocaleFile), "UTF-8")) {
            char[] buff = new char[2];
            if (reader.read(buff, 0, 2) != 2) {
                System.err.println("locale file is too small");
                return UserLocale.EN;
            }
            try {
                return UserLocale.valueOf(new String(buff));
            } catch (IllegalArgumentException e) {
                System.err.println("Incorrect locale type" + e.getMessage());
                return UserLocale.EN;
            }
        } catch (IOException e) {
            System.err.println("locale file is not exist." + e.getMessage());
            return UserLocale.EN;
        }
    }
}
