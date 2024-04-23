package course.oop.log;

/**
 * Класс, предоставляющий единый интерфейс для логирования всей программе
 * (контроллер)
 */
public final class Logger
{
    /**
     * Модель хранения логов
     */
    private static final ConcurrentLogSource defaultLogSource;
    static {
        defaultLogSource = new ConcurrentLogSource(10);
    }
    
    private Logger()
    {
    }

    /**
     * Пишет в лог отладочную информацию
     */
    public static void debug(String strMessage)
    {
        defaultLogSource.append(new LogEntry(LogLevel.Debug, strMessage));
    }
    
    /**
     * Пишет лог ошибки
     */
    public static void error(String strMessage)
    {
        defaultLogSource.append(new LogEntry(LogLevel.Error, strMessage));
    }

    /**
     * Возвращает "модель" логирования (контейнер)
     */
    public static ConcurrentLogSource getDefaultLogSource()
    {
        return defaultLogSource;
    }
}
