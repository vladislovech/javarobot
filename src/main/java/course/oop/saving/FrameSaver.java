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
 * <li><i>(опционально)</i> указать путь, куда должны сохраняться состояния,
 * используя методы setSaveLocation, setConfilename</li>
 * <li>Добавить сохраняемые окна, которые нужно сохранить, используя метод
 * addSaveableFrame</li>
 * <li>Вызвать метод save() у созданного экземпляра</li>
 * </ul>
 */
public class FrameSaver extends FrameSaveLoader {
    /**
     * Стандартный конструктор. Устанавливает путь для сохранения такой, какой
     * требует вторая задача: $HOME/Robots/config/windowStates.conf
     */
    public FrameSaver() {
        super();
        super.states = new HashMap<String, FrameConfig>();
    }

    /**
     * Извлекает данные для сохранения у переданного окна и сохраняет
     * их во внутренний map.
     */
    public void addSaveableFrame(Saveable saveable) {
        super.states.put(saveable.getFrameId(), saveable.getWindowConfig());
    }

    /**
     * Сохраняет сформированный map в указанный во внутреннем поле файл.
     */
    public void save() throws SaveException {
        save(super.states);
    }

    /**
     * Приватный метод. Аналог публичного save с возможностью передать
     * map с состояниями
     */
    private void save(Map<String, FrameConfig> s) throws SaveException {
        try {
            FileUtils.forceMkdir(saveLocation);
        } catch (IOException e) {
            throw new SaveException("Не удалось создать иерархию директорий для сохранения");
        }

        File saveFile = new File(saveLocation, confilename);
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            throw new SaveException("Не удалось создать конфигурационный файл");
        }
        try {
            OutputStream os = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
            try {
                oos.writeObject(s);
            } catch (IOException e) {
                throw new SaveException("Не удалось записать состояний в файл");
            } finally {
                oos.close();
                os.close();
            }
        } catch (FileNotFoundException e) {
            throw new SaveException("Ну удалось создать поток вывода. Файл конфига не найден");
        } catch (IOException e) {
            throw new SaveException("Не удалось создать иерархию фильтров");
        }
    }

    /**
     * Создает файл конфигурации с 0 сохраненных состояний,
     * если файл конфигурации еще не существует
     */
    public void initHierachy() throws SaveException {
        File conFile = new File(super.saveLocation, super.confilename);
        if (!conFile.exists())
            save(new HashMap<String, FrameConfig>());
    }
}
