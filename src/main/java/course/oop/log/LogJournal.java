package course.oop.log;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Класс журнала логов. Хранит логи, уведомляет слушателей об изменении логов
 */
public class LogJournal {
    /**
     * Контейнер с записями лога
     */
    private final ConcurrentCircularArray logs;

    /**
     * Map, хранящий слабые ссылки на слушателей
     */
    private final Map<LogChangeListener, String> listeners;

    public LogJournal(int containerSize) {
        logs = new ConcurrentCircularArray(containerSize);
        listeners = new WeakHashMap<LogChangeListener, String>();
    }

    /**
     * Добавляет слушателя журнала логов
     */
    public void registerListener(LogChangeListener listener) {
        listeners.put(listener, "listener");
    }

    /**
     * Удаляет слушателя журнала логов
     */
    public void unregisterListener(LogChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Добавляет запись лога в журнал, уведомляет об этом слушателей
     */
    public void append(LogEntry entry) {
        logs.push(entry);
        for (LogChangeListener listener : listeners.keySet())
            listener.onLogChanged();
    }

    /**
     * Возвращает диапазон записей от startFrom в количестве count
     */
    public Iterable<LogEntry> range(int startFrom, int count) {
        return logs.range(startFrom, count);
    }

    /**
     * Возвращает все записи в журнале
     */
    public Iterable<LogEntry> all() {
        return logs.all();
    }
}