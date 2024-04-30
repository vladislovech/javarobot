package course.oop.log;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Тестирует класс циклического массива, хранящий логи
 */
public class ConcurrentCircularArrayTest {
    /**
     * Проверяет корректность добавления записей в лог
     * (можно проверять по ссылкам) и их чтения
     */
    @Test
    public void testConcurrentContainer() {
        ConcurrentCircularArray cls = new ConcurrentCircularArray(3);
        Assert.assertEquals(0, 0);
        List<LogEntry> le = new ArrayList<>();
        le.add(new LogEntry(LogLevel.Debug, "1"));
        le.add(new LogEntry(LogLevel.Error, "2"));
        le.add(new LogEntry(LogLevel.Debug, "3"));
        le.add(new LogEntry(LogLevel.Debug, "4"));
        le.add(new LogEntry(LogLevel.Debug, "5"));
        le.add(new LogEntry(LogLevel.Debug, "6"));
        le.add(new LogEntry(LogLevel.Debug, "7"));

        cls.push(le.get(0));
        Assert.assertEquals(0, cls.range(0, 0).size());
        Assert.assertEquals(le.get(0), cls.range(0, 1).get(0));

        cls.push(le.get(1));
        Assert.assertEquals(le.get(0), cls.range(0, 2).get(0));
        Assert.assertEquals(le.get(1), cls.range(0, 2).get(1));

        cls.push(le.get(2));
        Assert.assertEquals(le.get(0), cls.range(0, 3).get(0));
        Assert.assertEquals(le.get(1), cls.range(0, 3).get(1));
        Assert.assertEquals(le.get(2), cls.range(0, 3).get(2));

        Assert.assertEquals(le.get(1), cls.range(1, 2).get(0));
        Assert.assertEquals(le.get(2), cls.range(1, 2).get(1));
        Assert.assertEquals(le.get(2), cls.range(2, 1).get(0));

        cls.push(le.get(3));
        Assert.assertEquals(le.get(1), cls.range(0, 3).get(0));
        Assert.assertEquals(le.get(2), cls.range(0, 3).get(1));
        Assert.assertEquals(le.get(3), cls.range(0, 3).get(2));

        Assert.assertEquals(le.get(2), cls.range(1, 2).get(0));
        Assert.assertEquals(le.get(3), cls.range(1, 2).get(1));
        Assert.assertEquals(le.get(3), cls.range(2, 1).get(0));

        cls.push(le.get(4));
        Assert.assertEquals(le.get(2), cls.range(0, 3).get(0));
        Assert.assertEquals(le.get(3), cls.range(0, 3).get(1));
        Assert.assertEquals(le.get(4), cls.range(0, 3).get(2));

        Assert.assertEquals(le.get(3), cls.range(1, 2).get(0));
        Assert.assertEquals(le.get(4), cls.range(1, 2).get(1));
        Assert.assertEquals(le.get(4), cls.range(2, 1).get(0));

        cls.push(le.get(5));
        Assert.assertEquals(le.get(3), cls.range(0, 3).get(0));
        Assert.assertEquals(le.get(4), cls.range(0, 3).get(1));
        Assert.assertEquals(le.get(5), cls.range(0, 3).get(2));

        Assert.assertEquals(le.get(4), cls.range(1, 2).get(0));
        Assert.assertEquals(le.get(5), cls.range(1, 2).get(1));
        Assert.assertEquals(le.get(5), cls.range(2, 1).get(0));

        cls.push(le.get(6));
        Assert.assertEquals(le.get(4), cls.range(0, 3).get(0));
        Assert.assertEquals(le.get(5), cls.range(0, 3).get(1));
        Assert.assertEquals(le.get(6), cls.range(0, 3).get(2));

        Assert.assertEquals(le.get(5), cls.range(1, 2).get(0));
        Assert.assertEquals(le.get(6), cls.range(1, 2).get(1));
        Assert.assertEquals(le.get(6), cls.range(2, 1).get(0));
    }
}
