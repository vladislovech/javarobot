package gui;

import java.text.MessageFormat;

public class FormattingBenchmark {
    public static void main(String[] args) {
        String pattern = "хэллоу, {0}! Сегодня {1}, температура {2}°C";
        Object[] arguments = {"Владимир", "понедельник", 200};

        int testCount = 100000;

        // Formatter
        long formatterStart = System.currentTimeMillis();
        testFormatter(pattern, arguments, testCount);
        long formatterTime = System.currentTimeMillis() - formatterStart;

        // MessageFormat без кэша
        long messageFormatStart = System.currentTimeMillis();
        testMessageFormat(pattern, arguments, testCount);
        long messageFormatTime = System.currentTimeMillis() - messageFormatStart;

        // MessageFormat с кэшем
        long cachedStart = System.currentTimeMillis();
        testCachedMessageFormat(pattern, arguments, testCount);
        long cachedTime = System.currentTimeMillis() - cachedStart;

        // результаты
        System.out.println("Результаты тестирования (мс):");
        System.out.println("1. Formatter: " + formatterTime);
        System.out.println("2. MessageFormat без кэша: " + messageFormatTime);
        System.out.println("3. MessageFormat с кэшем: " + cachedTime);
    }

    private static void testFormatter(String pattern, Object[] args, int count) {
        String formatterPattern = "Привет, %s! Сегодня %s, температура %d°C";
        for (int i = 0; i < count; i++) {
            String result = String.format(formatterPattern, args);
        }
    }

    private static void testMessageFormat(String pattern, Object[] args, int count) {
        for (int i = 0; i < count; i++) {
            MessageFormat mf = new MessageFormat(pattern);
            String result = mf.format(args);
        }
    }

    private static void testCachedMessageFormat(String pattern, Object[] args, int count) {
        MessageFormat mf = new MessageFormat(pattern);
        for (int i = 0; i < count; i++) {
            String result = mf.format(args);
        }
    }
}
