package org.robotgame.log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LogWindowSource {
    private final int m_iQueueLength;
    private final BlockingDeque<LogEntry> m_messages;
    private final BlockingDeque<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;
    private boolean m_statusOperation = false;
    AtomicInteger m_counter = new AtomicInteger(0);

    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new LinkedBlockingDeque<>(iQueueLength);
        m_listeners = new LinkedBlockingDeque<>();
    }

    public void registerListener(LogChangeListener listener) {
        m_listeners.offer(listener);
        updateActiveListeners();
    }

    public void unregisterListener(LogChangeListener listener) {
        m_listeners.remove(listener);
        updateActiveListeners();
    }

    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);

        do {
            if (m_counter.getAndIncrement() >= m_iQueueLength)
            {
                m_messages.pollFirst();
                m_counter.getAndDecrement();
            }
            m_statusOperation = m_messages.offerLast(entry);
        } while(!m_statusOperation);

        notifyListeners();
    }

    public synchronized void notifyListeners() {
        LogChangeListener[] activeListeners = m_activeListeners;
        if (activeListeners == null) {
            updateActiveListeners();
            activeListeners = m_activeListeners;
        }
        if (activeListeners != null) {
            for (LogChangeListener listener : activeListeners) {
                listener.onLogChanged();
            }
        }
    }

    private synchronized void updateActiveListeners() {
        m_activeListeners = m_listeners.toArray(new LogChangeListener[0]);
    }

    public int size() {
        return m_messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count) {
        List<LogEntry> range = new ArrayList<>();
        int i = 0;
        for (LogEntry entry : m_messages) {
            if (i >= startFrom && i < startFrom + count) {
                range.add(entry);
            }
            i++;
        }
        return range;
    }

    public Iterable<LogEntry> all() {
        return new ArrayList<>(m_messages);
    }
    public int getCountListener() {
        return m_listeners.size();
    }
}
