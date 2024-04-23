package log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrent circular array implementation
 * Provides methods to add element, get current size, get sublist and get whole array as iterable
 */
public class ConcurrentCircularArray {
    private final LogEntry[] underlying;
    private final int capacity;
    private int size;
    private int head;
    private int tail;
    private final Lock lock = new ReentrantLock();

    /**
     * Initialize instance with capacity
     *
     * @param capacity - maximum list capacity
     */
    public ConcurrentCircularArray(int capacity) {
        this.underlying = new LogEntry[capacity];
        this.capacity = capacity;
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    /**
     * Add entry to the list
     *
     * @param entry - log entry
     */
    public void add(LogEntry entry) {
        lock.lock();
        try {
            if (size == underlying.length) {
                head = (head + 1) % underlying.length; // удаление старого элемента
            } else {
                size++;
            }
            underlying[tail] = entry;
            tail = (tail + 1) % underlying.length;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return size;
    }

    /**
     * Get sublist from whole array
     *
     * @param startFrom - inclusive start index
     * @param indexTo   - exclusive end index
     * @return an array list of the specified range within this array
     */
    public Iterable<LogEntry> subList(int startFrom, int indexTo) {
        lock.lock();
        try {
            List<LogEntry> result = new ArrayList<>();
            for (int i = startFrom; i < indexTo; i++) {
                result.add(underlying[(head + i) % capacity]);
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get whole array as iterable
     *
     * @return array list with all array elements
     */
    public Iterable<LogEntry> all() {
        List<LogEntry> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int shift = (head + i) % capacity;
            list.add(underlying[shift]);
        }
        return list;
    }
}
