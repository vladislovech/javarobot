package course.oop.log;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Класс журнала логов. Хранит логи, уведомляет слушателей об изменении логов
 */
public class LogJournal {
    /**
     * Контейнер с записями лога
     */
    private final ConcurrentCircularArray logs;
    /**
     * Потокобезопасный set
     */
    private final Set<LogChangeListener> listeners;

    public LogJournal(int containerSize) {
        logs = new ConcurrentCircularArray(containerSize);
        listeners = new CopyOnWriteArraySet<LogChangeListener>();
    }

    /**
     * Добавляет слушателя журнала
     */
    public void registerListener(LogChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Удаляет слушателя журнала
     */
    public void unregisterListener(LogChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Добавляет запись лога в журнал, уведомляет об этом слушателей
     */
    public void append(LogEntry entry) {
        logs.push(entry);
        for (LogChangeListener listener : listeners)
            listener.onLogChanged();
    }

    /**
     * Возвращает диапазон записей от startFrom в количестве count
     */
    public Iterable<LogEntry> range(int startFrom, int count) {
        return logs.range(startFrom, count);
    }

    /**
     * Возвращает последнюю запись в журнале логов
     */
    public LogEntry getLastLogEntry() {
        return logs.range(logs.size() - 1, 1).get(0);
    }

    /**
     * Возвращает все записи в журнале
     */
    public Iterable<LogEntry> all() {
        return logs.all();
    }
}