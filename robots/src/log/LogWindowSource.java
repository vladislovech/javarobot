package log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private final ArrayList<WeakReference<LogChangeListener>> m_listeners;
    //private final ArrayList<LogChangeListener> m_listeners;
    //private volatile LogChangeListener[] m_activeListeners;
    private volatile ArrayList<WeakReference<LogChangeListener>> m_activeListeners;
    
    public LogWindowSource(int iQueueLength) 
    {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayList<LogEntry>(iQueueLength);
        m_listeners = new ArrayList<WeakReference<LogChangeListener>>();
        //m_listeners = new ArrayList<LogChangeListener>();
    }
    
    public void registerListener(LogChangeListener listener)
    {
    	
        synchronized(m_listeners)
        {
        	WeakReference<LogChangeListener> weakListener = new WeakReference(listener);
        	m_listeners.add(weakListener);
        	//m_listeners.add(listener);
            m_activeListeners = null;
        }
    }
    
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
        	WeakReference<LogChangeListener> weakListener = new WeakReference(listener);
        	m_listeners.remove(weakListener);
        	//m_listeners.remove(listener);
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
        
        //LogChangeListener[] activeListeners = m_activeListeners;
        ArrayList<WeakReference<LogChangeListener>> activeListeners = m_activeListeners;
        if (activeListeners == null)
        {
            synchronized (m_listeners)
            {
                if (m_activeListeners == null)
                {
                	//activeListeners = m_listeners.toArray(new LogChangeListener [0]);
                	activeListeners = m_listeners;
                    m_activeListeners =  activeListeners;
                }
            }
        }
        for (WeakReference<LogChangeListener> listener : activeListeners)
        {
            listener.get().onLogChanged();
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
