package course.oop.saving;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс(singlton), отвечающий за сохранение состояний об окнах.
 */
public class WindowConfigsIO implements Flushable {
    /**
     * Экземпляр синглтона.
     */
    private static WindowConfigsIO instance;

    /**
     * Таблица, хранящая информацию об окнах.
     */
    private Map<String, WindowConfig> windowStates;
    private File confFile;

    /**
     * Конструктор, открывающий файл, переносящий данные из него в поле
     * {@code windowStates}
     */
    private WindowConfigsIO() throws LoadException {
        String confFilePath = System.getProperty("user.home") +
                File.separator + "Robots" +
                File.separator + "config" +
                File.separator + "windowStates.conf";

        confFile = new File(confFilePath);

        try {
            createAppStructureIfNeeded();
            loadWindowStates();
        } catch (IOException e) {
            throw new LoadException("Error while loading window configurations", e);
        }
    }

    /**
     * Создает структуру директорий для класса, если ее нет.
     * 
     * @throws LoadException если не удалось создать структуру
     */
    private void createAppStructureIfNeeded() throws LoadException {
        File parentDirectory = confFile.getParentFile();

        if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
            throw new LoadException("Failed to create app structure at " + confFile.getPath());
        }
    }

    /**
     * 
     * @throws IOException
     * @throws LoadException
     */
    private void loadWindowStates() throws IOException, LoadException {
        if (!confFile.exists() || confFile.length() == 0) {
            windowStates = new HashMap<>();
            flush();
        } else {
            try (InputStream is = new FileInputStream(confFile);
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {

                windowStates = (HashMap<String, WindowConfig>) ois.readObject();

            } catch (FileNotFoundException e) {
                throw new LoadException("Configuration file not found at " + confFile.getPath(), e);
            } catch (ClassNotFoundException e) {
                throw new LoadException("Data is corrupted in " + confFile.getPath(), e);
            }
        }
    }

    /**
     * Метод, позволяющий получить ссылку на экземпляр синглтона.
     */
    public static synchronized WindowConfigsIO getInstance() throws LoadException {
        if (instance == null)
            instance = new WindowConfigsIO();
        return instance;
    }

    /**
     * Создает или обновляет запись в {@code windowStates}, сохраняя копию
     * переданного {@link WindowConfig}
     * 
     * @param id уникальный идентификатор окна
     * @param wc конфигурация окна
     */
    public void save(String id, WindowConfig wc) {
        windowStates.put(id, wc);
    }

    /**
     * Возвращает из {@code windowStates} запись о параметрах окна,
     * если она есть (скопированную)
     * 
     * @param id уникальный идентификатор окна
     * @throws LoadException если записи нет
     */
    public WindowConfig load(String id) throws LoadException {
        if (!windowStates.containsKey(id))
            throw new LoadException("no saved entry for %s".formatted(id));
        return new WindowConfig(windowStates.get(id));
    }

    /**
     * Сохраняет {@code windowStates} в файл {@code confFile}, перезаписывая
     * его прежнее содержимое.
     */
    @Override
    public void flush() throws IOException {
        try (OutputStream os = new FileOutputStream(confFile);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {

            oos.writeObject(windowStates);

        } catch (FileNotFoundException e) {
            throw new IOException("Can't find file structure.", e);
        }
    }

}
