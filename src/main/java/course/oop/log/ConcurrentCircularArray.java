package course.oop.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - потокобезопасный контейнер логов (модель):
 * - Хранит логи
 * - Возвращает логи по требованию, генерируя событие
 * - Имеет ограничение на число логов. Старые перезаписываются (удаляются)
 * Хранит записи логов в виде массива фиксированного размера.
 */
class ConcurrentCircularArray {
    /**
     * Массив логов
     */
    private final LogEntry[] logs;
    /**
     * Стартовый индекс (указывает на самую раннюю запись)
     */
    private int startPointer;

    /**
     * Конечный индекс (указывает на самую позднюю запись)
     */
    private int endPointer;

    /**
     * Число записей лога сейчас
     */
    private int size;

    /**
     * Конструктор, инициализирующий массив(контейнер) (не меняет размер)
     */
    public ConcurrentCircularArray(int size) {
        logs = new LogEntry[size];
        startPointer = 0;
        endPointer = 0;
        size = 0;
    }

    /**
     * Добавляет запись в лог (O(1))
     */
    public synchronized void push(LogEntry logEntry) {
        logs[endPointer] = logEntry;
        endPointer = (endPointer + 1) % logs.length;
        if (size == logs.length)
            startPointer = (startPointer + 1) % logs.length;
        else
            size++;
    }

    /**
     * Возвращает число записей в журнале
     */
    public synchronized int size() {
        return size;
    }

    /**
     * Возвращает диапазон записей от start в количестве count (O(n))
     */
    public synchronized List<LogEntry> range(int start, int count) throws IllegalArgumentException {
        if (start + count > size || start < 0 || count < 0)
            throw new IllegalArgumentException(
                    "count and start must be nonnegative and less or equal log journal size in sum.");
        List<LogEntry> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            list.add(logs[(this.startPointer + start + i) % logs.length]);
        return list;
    }

    /**
     * Возвращает все записи в журнале
     */
    public List<LogEntry> all() {
        return range(0, size());
    }
}
