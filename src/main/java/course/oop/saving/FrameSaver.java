package course.oop.saving;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * Класс отвечающий за сохранение состояний окон. Использование:
 * <ul>
 * <li>Создать экземпляр класса</li>
 * <li>Указать* путь сохранения окон</li>
 * <li>Добавить сохраняемые окна, которые нужно сохранить, используя метод
 * addSaveableFrame</li>
 * <li>Вызвать метод save() у созданного экземпляра</li>
 * </ul>
 */
public class FrameSaver {
    /**
     * Директорий, куда сохраняется файл конфигурации.
     */
    private File saveLocation;
    /**
     * Имя файла конфигурации
     */
    private String confilename;
    /**
     * Map для хранения состояний окон
     */
    private Map<String, FrameConfig> states;

    /**
     * Стандартный конструктор. Устанавливает путь для сохранения такой, какой
     * требует вторая задача: $HOME/Robots/config/windowStates.conf
     */
    public FrameSaver() {
        saveLocation = new File(
                System.getProperty("user.home") +
                        File.separator + "Robots" +
                        File.separator + "config");
        confilename = "windowStates.conf";
        states = new HashMap<String, FrameConfig>();
    }

    /**
     * Заменяет стандартную директорию для сохранения на произвольную
     */
    public void setSaveLocation(File location) {
        saveLocation = location;
    }

    /**
     * Заменяет стандартное имя конфигурационного файла, куда сохраняется файл
     * на произвольное
     */
    public void setConfilename(String confilename) {
        this.confilename = confilename;
    }

    /**
     * Извлекает данные для сохранения у переданного окна и сохраняет
     * их во внутренний map.
     */
    public void addSaveableFrame(Saveable saveable) {
        states.put(saveable.getFrameId(), saveable.getWindowConfig());
    }

    /**
     * Сохраняет сформированный map в указанный во внутреннем поле файл.
     */
    public void save() throws SaveException {
        try {
            FileUtils.forceMkdir(saveLocation);
        } catch (IOException e) {
            throw new SaveException("Не удалось создать иерархию директорий для сохранения");
        }

        File saveFile = new File(saveLocation, confilename);
        try {
            OutputStream os = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
            try {
                oos.writeObject(states);
            } catch (IOException e) {
                throw new SaveException("Не удалось записать состояний в файл");
            } finally {
                oos.close();
                os.close();
            }
        } catch (FileNotFoundException e) {
            throw new SaveException("Ну удалось создать поток вывода");
        } catch (IOException e) {
            throw new SaveException("Не удалось создать иерархию фильтров");
        }
    }

    /**
     * Геттер
     */
    public File getSaveLocation() {
        return saveLocation;
    }

    /**
     * Геттер
     */
    public String getConfilename() {
        return confilename;
    }
}
