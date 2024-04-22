package log;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrent wrapper on {@link LinkedList}
 * Provides methods to add element, get current size, get sublist and get iterator
 */
public class ConcurrentLinkedList implements Iterable<LogEntry>{
    private volatile LinkedList<LogEntry> underlying;
    private final int capacity;
    private final Lock lock = new ReentrantLock();

    /**
     * Initialize instance with capacity
     * @param capacity - maximum list capacity
     */
    public ConcurrentLinkedList(int capacity){
        underlying = new LinkedList<>();
        this.capacity = capacity;
    }

    /**
     * Add entry to the list
     * @param entry - log entry
     */
    public void add(LogEntry entry) {
        lock.lock();
        try {
            if (underlying.size() == capacity){
                underlying.remove();
            }
            underlying.add(entry);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return underlying.size();
    }

    /**
     * Get sublist from whole list
     * @param startFrom - inclusive start index
     * @param indexTo - exclusive end index
     * @return a view of the specified range within this list
     */
    public Iterable<LogEntry> subList(int startFrom, int indexTo) {
        lock.lock();
        try {
            return underlying.subList(startFrom, indexTo);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get a list iterator
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<LogEntry> iterator() {
        lock.lock();
        try {
            return underlying.iterator();
        } finally {
            lock.unlock();
        }
    }
}
