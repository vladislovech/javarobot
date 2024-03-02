package course.oop.saving;

/**
 * Исключение, возникающее при ошибке сохранения данных об окнах.
 */
public class SaveException extends Exception {
    public SaveException(String message) {
        super(message);
    }
}
