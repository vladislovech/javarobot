package course.oop.saving;

/**
 * Интерфейс, представляющий объект,
 * данные о котором могут быть сохранены и загружены.
 */
public interface Saveable {

    /**
     * Возвращает состояние окна, которое нужно сохранить
     */
    FrameConfig getWindowConfig();

    /**
     * Возвращает уникальный идентификатор окна
     */
    String getFrameId();
}
