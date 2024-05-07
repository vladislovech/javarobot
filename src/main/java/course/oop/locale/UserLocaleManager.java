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
 * Статический класс.
 * - Хранит текущую локаль пользователя
 * - Загружает локаль пользователя из файла
 * - Сохраняет локаль пользователя в файл
 * - Возвращает ресурсы, связанные с текущей локалью пользователя
 *      в любом месте в программе
 */
public class UserLocaleManager {
    /**
     * Расположение локали пользователя по умолчанию
     */
    private static File userLocaleFile = new File(
            System.getProperty("user.home") +
                    File.separator + "Robots" +
                    File.separator + "config" +
                    File.separator + "locale.conf");

    /**
     * Хранит текущую локаль пользователя
     */
    private static UserLocale currentUserLocale;

    /**
     * Загружает локаль пользователя при загрузке класса, следовательно
     * она всегда известна.
     */
    static {
        loadUserLocale();
    }

    /**
     * Класс статический
     */
    private UserLocaleManager() {
    }

    /**
     * Возвращает текущие ресурсы со связанной текущей локалью пользователя.
     * ResourceBundle.getBundle() кешируется, так что его вызов не вызывает
     * частое чтение файла (хотя он еще и потокобезопасный, но я не заметил,
     * что это влияет на производительность). Если по каким-то причинам
     * currentUserLocale null, возвращаются ресурсы на английском.
     */
    public static ResourceBundle getCurrentBundle() {
        switch (currentUserLocale) {
            case EN:
                return ResourceBundle.getBundle("locales/en", new Locale("en"));
            case RU:
                return ResourceBundle.getBundle("locales/ru", new Locale("ru"));
        }
        return ResourceBundle.getBundle("locales/en", new Locale("en"));
    }

    /**
     * Возвращает текущую локаль пользователя.
     */
    public static UserLocale getCurrentLocale() {
        return currentUserLocale;
    }

    /**
     * Изменяет текущую локаль пользователя,
     * сохраняет ее в файл конфигурации пользователя
     */
    public static synchronized void setUserLocale(UserLocale userLocale) {
        currentUserLocale = userLocale;
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
            System.err.println("can't save user locale");
        }
    }

    /**
     * Устанавливает текущую локаль пользователя, читая ее из файла
     * с локалью пользователя
     */
    private static void loadUserLocale() {
        currentUserLocale = getUserLocaleFromLocaleFile();
    }

    /**
     * Возвращает строку из первых двух символов файла
     * $HOME/Robots/config/locale.conf (в кодировке UTF-8).
     * Если файл не существует, или слишком мал, или его содержимое некорректно,
     * возвращает локаль по умолчанию - английскую
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
                System.err.println("Incorrect locale type");
                return UserLocale.EN;
            }
        } catch (IOException e) {
            System.err.println("locale file is not exist.");
            return UserLocale.EN;
        }
    }
}
