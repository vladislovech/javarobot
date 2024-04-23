package course.oop.log;

/**
 * Класс-сущность, представляющий запись в логе
 * # TODO добавить время лога
 */
public class LogEntry
{
    private LogLevel m_logLevel;
    private String m_strMessage;
    
    public LogEntry(LogLevel logLevel, String strMessage)
    {
        m_strMessage = strMessage;
        m_logLevel = logLevel;
    }
    
    public String getMessage()
    {
        return m_strMessage;
    }
    
    public LogLevel getLevel()
    {
        return m_logLevel;
    }
}

