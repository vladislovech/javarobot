package course.oop.saving;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * Класс, отвечающий за замену состояний существующих окон на сохраненные.
 * Использование:
 * <ul>
 * <li>Создать экземпляр класса</li>
 * <li><i>(опционально)</i> Указать путь, куда сохранены состояния окон</li>
 * <li>Вызвать метод loadStates(), который подгразит найденные состояния</li>
 * <li>Вызвать метод loadFrame c переданным сохраняемым окном,
 * состояние которого нужно загрузить</li>
 * <li>Вызвать метод save() у созданного экземпляра</li>
 * </ul>
 */
public class FrameLoader extends FrameSaveLoader {
    /**
     * Конструктор.
     */
    public FrameLoader() {
        super();
    }

    /**
     * Читает указанный конфигурационный файл, заполняя внутреннее поле states
     */
    public void loadStates() throws LoadException {
        try {
            InputStream is = new FileInputStream(new File(saveLocation, confilename));
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
            try {
                states = (HashMap<String, FrameConfig>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new LoadException("Не удалось прочитать файл. класс hashmap не нашелся.");
            } finally {
                ois.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            throw new LoadException("Ну удалось создать поток вывода");
        } catch (IOException e) {
            throw new LoadException("Не удалось создать иерархию фильтров");
        }
    }

    /**
     * Пытается найти сохраненное состояние для указанного окна. Если его нет,
     * поднимает ошибку, что загружать нечего.
     */
    public void loadFrame(Saveable saveable) throws LoadException {
        if (!states.containsKey(saveable.getFrameId()))
            throw new LoadException("Нет данных для загрузки этого окна.");
        saveable.loadConfig(states.get(saveable.getFrameId()));
    }
}
