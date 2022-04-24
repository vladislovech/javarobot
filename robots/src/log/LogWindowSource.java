package log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ��� ��������:
 * 1. ���� ����� ��������� ������ �������� (��������� ��������� �����������
 * ������������� � ������)
 * 2. ���� ����� ������ �������� ��������� ����, �� � ����� ���������� �� 
 * �� ���� �����������. ���� ��, ����� ���������� ��������� � ���� ���� ���������� 
 * ��������� m_iQueueLength (�.�. ������� ����� ������� ��������� 
 * ������������� �������) 
 */
public class LogWindowSource
{
    private int m_iQueueLength;
    
    private ArrayList<LogEntry> m_messages;
    private final ArrayList<LogChangeListener> m_listeners;
    private volatile WeakReference<LogChangeListener[]> m_activeListeners;
    
    public LogWindowSource(int iQueueLength) 
    {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayList<LogEntry>(iQueueLength);
        m_listeners = new ArrayList<LogChangeListener>();
    }
    
    public void registerListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.add(listener);
            m_activeListeners = null;
        }
    }
    
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.remove(listener);
            m_activeListeners = null;
        }
    }
    
    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        synchronized(m_messages) {
	        if(m_messages.size()<m_iQueueLength)
	        	m_messages.add(entry);
	        else {
	        	m_messages.remove(0);
	        	m_messages.add(entry);
	        }
        }
        
        var activeListeners = m_activeListeners;
        if (activeListeners == null)
        {
            synchronized (m_listeners)
            {
                if (m_activeListeners == null)
                {
                    activeListeners = new WeakReference<>(m_listeners.toArray(new LogChangeListener [0]));
                    m_activeListeners =  activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners.get())
        {
            listener.onLogChanged();
        }
    }
    
    public int size()
    {
        return m_messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= m_messages.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_messages.size());
        return m_messages.subList(startFrom, indexTo);
    }

    public Iterable<LogEntry> all()
    {
        return m_messages;
    }
}
