package course.oop.saving;

import java.io.File;
import java.util.Map;

/**
 * Класс, который создан, чтобы не дублировать код в FrameLoader и FrameSaver
 */
public abstract class FrameSaveLoader {
/**
     * Директория, куда сохраняется файл конфигурации.
     */
    protected File saveLocation;
    /**
     * Имя файла конфигурации
     */
    protected String confilename;
    /**
     * Map для хранения состояний окон
     */
    protected Map<String, FrameConfig> states;

     /**
     * Стандартный конструктор. Устанавливает путь для сохранения такой, какой
     * требует вторая задача: $HOME/Robots/config/windowStates.conf
     */
    public FrameSaveLoader() {
        saveLocation = new File(
                System.getProperty("user.home") +
                        File.separator + "Robots" +
                        File.separator + "config");
        confilename = "windowStates.conf";
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
