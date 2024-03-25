package course.oop.saving;

/**
 * Интерфейс, представляющий объект,
 * данные о котором могут быть сохранены и загружены.
 */
public interface Saveable {
    /**
     * Возвращает уникальный идентификатор окна
     */
    String getFrameId();
}
