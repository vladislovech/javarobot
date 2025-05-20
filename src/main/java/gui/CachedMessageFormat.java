package gui;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedMessageFormat {
    private static final Map<String, MessageFormat> cache = new ConcurrentHashMap<>();

    public static String format(String pattern, Object... args) {
        MessageFormat messageFormat = cache.computeIfAbsent(pattern, MessageFormat::new);
        synchronized (messageFormat) {
            return messageFormat.format(args);
        }
    }
}
