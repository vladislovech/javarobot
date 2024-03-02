package course.oop.saving;

/**
 * Интерфейс, представляющий объект,
 * данные о котором могут быть сохранены и загружены.
 */
public interface Saveable {
    /**
     * метод для сохранения данных об объекте.
     */
    void Save() throws SaveException;

    /**
     * метод для восстановления объекта по сохраненным данным.
     */
    void Load() throws LoadException;
}
