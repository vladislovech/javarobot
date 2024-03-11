package org.robotgame.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogWindowSourceTest {
    private LogWindowSource logWindowSource;

    @BeforeEach
    public void setUp() {
        logWindowSource = new LogWindowSource(5);
    }

    @Test
    public void testRegisterListener() {
        LogChangeListener listener = new LogChangeListener() {
            @Override
            public void onLogChanged() {
                // Пустая реализация, так как нам не важно, что происходит при изменении лога
            }
        };

        logWindowSource.registerListener(listener);
        assertEquals(1, logWindowSource.getCountListener());
    }


    @Test
    public void testUnregisterListener() {
        LogChangeListener listener = new LogChangeListener() {
            @Override
            public void onLogChanged() {
            }
        };
        logWindowSource.registerListener(listener);

        logWindowSource.unregisterListener(listener);

        assertEquals(0, logWindowSource.getCountListener());
    }

    @Test
    public void testAppend() {
        LogChangeListener listener = new LogChangeListener() {
            @Override
            public void onLogChanged() {
            }
        };
        logWindowSource.registerListener(listener);

        logWindowSource.append(LogLevel.Info, "Message");

        assertEquals(1, logWindowSource.size());
    }


    @Test
    public void testSize() {
        logWindowSource.append(LogLevel.Info, "Message 1");
        logWindowSource.append(LogLevel.Info, "Message 2");

        assertEquals(2, logWindowSource.size());
    }

    @Test
    public void testSizeOverFlow(){
        logWindowSource.append(LogLevel.Info, "Message 1");
        logWindowSource.append(LogLevel.Info, "Message 2");
        logWindowSource.append(LogLevel.Info, "Message 3");
        logWindowSource.append(LogLevel.Info, "Message 4");
        logWindowSource.append(LogLevel.Info, "Message 5");
        logWindowSource.append(LogLevel.Info, "Message 6");

        assertEquals(5, logWindowSource.size());
    }

    @Test
    public void testValidDeque(){
        logWindowSource.append(LogLevel.Info, "Message 1");
        logWindowSource.append(LogLevel.Info, "Message 2");
        logWindowSource.append(LogLevel.Info, "Message 3");
        logWindowSource.append(LogLevel.Info, "Message 4");
        logWindowSource.append(LogLevel.Info, "Message 5");
        logWindowSource.append(LogLevel.Info, "Message 6");
        logWindowSource.notifyListeners();

        int i = 1;
        for (LogEntry entry : logWindowSource.all()){
            assertEquals("Message " + (i+1), entry.getMessage());
            assertEquals(LogLevel.Info, entry.getLevel());
            i++;
        }
    }

    @Test
    public void testRange() {
        logWindowSource.append(LogLevel.Info, "Message 1");
        logWindowSource.append(LogLevel.Info, "Message 2");
        logWindowSource.append(LogLevel.Info, "Message 3");

        Iterable<LogEntry> range = logWindowSource.range(1, 2);

        int count = 0;
        for (LogEntry entry : range) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testAll() {
        logWindowSource.append(LogLevel.Info, "Message 1");
        logWindowSource.append(LogLevel.Info, "Message 2");

        Iterable<LogEntry> allEntries = logWindowSource.all();

        int count = 0;
        for (LogEntry entry : allEntries) {
            count++;
        }
        assertEquals(2, count);
    }
}
