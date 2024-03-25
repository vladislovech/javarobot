package course.oop.saving;

/**
 * Интерфейс, предоставляющий методы для загрузки окон
 */
public interface FrameLoader {
    /**
     * Загружает окна из файла
     */
    public void loadStates() throws LoadException;

    /**
     * Пытается найти сохраненное состояние для указанного окна и загружает его.
     * Если его нет, поднимает ошибку, что загружать нечего.
     */
    public void loadFrame(Saveable saveable) throws LoadException;
}
