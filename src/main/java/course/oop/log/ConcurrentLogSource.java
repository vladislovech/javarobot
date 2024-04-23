package course.oop.log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - потокобезопасный контейнер логов (модель):
 * - Хранит логи
 * - Возвращает логи по требованию, генерируя событие
 * - Имеет ограничение на число логов. Старые перезаписываются (удаляются)
 * Хранит записи логов в виде массива фиксированного размера.
 */
public class ConcurrentLogSource {
    /**
     * # TODO
     * Для генерации событий и навешивания слушателей. Так можно?
     * Или оставить старый интерфейс?
     */
    private PropertyChangeSupport pcs;
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
    public ConcurrentLogSource(int size) {
        pcs = new PropertyChangeSupport(this);
        logs = new LogEntry[size];
        startPointer = 0;
        endPointer = 0;
        size = 0;
    }

    /**
     * Добавляет запись в лог (O(1))
     */
    public synchronized void append(LogEntry logEntry) {
        logs[endPointer] = logEntry; // пишем новую запись
        endPointer = (endPointer + 1) % logs.length; // смещаемся по кольцу
        if (size == logs.length) // если размер максимальный
            startPointer = (startPointer + 1) % logs.length; // забываем раннюю запись
        else
            size++; // иначе пока не забываем
        notifyListeners();
    }

    /**
     * Возвращает число записей в журнале
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает диапазон записей от start в количестве count (O(n), нельзя же
     * меньше)
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
     * 
     * @return
     */
    public List<LogEntry> all() {
        return range(0, size());
    }

    /**
     * Добавляет слушателя журнала
     */
    public synchronized void registerListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Удаляет слушателя журнала
     */
    public synchronized void unregisterListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Уведомляет слушателей об изменении состояния модели
     */
    private void notifyListeners() {
        pcs.firePropertyChange("log update", null, null);
    }
}
