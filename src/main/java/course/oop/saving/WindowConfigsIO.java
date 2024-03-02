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
        String confFilePath = System.getProperty("user.home")
                + File.separator + "Robots"
                + File.separator + "config"
                + File.separator + "windowStates.conf";
        confFile = new File(confFilePath);
        File p = confFile.getParentFile();
        if (!p.exists()) {
            if (p.mkdirs()) {
                try {
                    confFile.createNewFile();
                    windowStates = new HashMap<>();
                } catch (IOException e) {
                    throw new LoadException("can't create confFile %s}".formatted(confFilePath));
                }
            } else {
                throw new LoadException("can't create app structure at %s}".formatted(confFilePath));
            }
        } else {
            if (!confFile.exists()) {
                try {
                    confFile.createNewFile();
                    windowStates = new HashMap<>();
                } catch (IOException e) {
                    throw new LoadException("can't create confFile %s}".formatted(confFilePath));
                }
            } else {
                try {
                    InputStream is = new FileInputStream(confFile);
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
                    try {
                        windowStates = (HashMap<String, WindowConfig>) ois.readObject();
                    } catch (IOException e) {
                        throw new LoadException("can't read confFile %s}".formatted(confFilePath));
                    } catch (ClassNotFoundException e) {
                        throw new LoadException("data is bitten in %s}".formatted(confFilePath));
                    } finally {
                        ois.close();
                        is.close();
                    }
                } catch (FileNotFoundException e) {
                    throw new LoadException("can't read confFile %s because it's not exist}".formatted(confFilePath));
                } catch (IOException e) {
                    throw new LoadException("can't create ObjectInputStream.".formatted(confFilePath));
                }
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
     * Создает или обновляет запись в {@code windowStates}
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
        return new WindowConfig(windowStates.get(id));
    }

    /**
     * Сохраняет {@code windowStates} в файл {@code confFile}, перезаписывая
     * его прежнее содержимое.
     */
    @Override
    public void flush() throws IOException {
        try {
            OutputStream os = new FileOutputStream(confFile);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
            try {
                oos.writeObject(windowStates);
            } finally {
                oos.close();
                os.close();
            }
        } catch (FileNotFoundException e1) {
            throw new IOException("Can't find file structure.");
        }
    }

}
