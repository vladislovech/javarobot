package course.oop.saving;

/**
 * Исключение, возникающее при ошибке загрузки данных об окнах.
 */
public class LoadException extends Exception {
    public LoadException(String message) {
        super(message);
    }

    public LoadException(String message, Exception e) {
        super(message, e);
    }
}
