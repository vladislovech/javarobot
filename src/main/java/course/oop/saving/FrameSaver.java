package course.oop.saving;

/**
 * Интерфейс, предоставляющий методы для сохранения окон
 */
public interface FrameSaver {

    /**
     * Извлекает данные для сохранения у переданного окна и запоминает их для
     * будущего сохранения
     */
    public void addSaveableFrame(Saveable saveable);

    /**
     * Сохраняет добавленные состояния
     */
    public void save() throws SaveException;
}
