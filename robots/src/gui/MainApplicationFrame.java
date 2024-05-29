package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    public static ResourceBundle messages;
    private final ConcurrentHashMap<String, MessageFormat> messageFormatCache = new ConcurrentHashMap<>();
    private final GameWindow gameWindow;

    public MainApplicationFrame() {
        // Set default locale
        changeLocale(Locale.getDefault());

        // Make the big window be indented 50 pixels from each edge of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
        addWindow(gameWindow.m_visualizer.robotCoordinatesWindow);
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //WindowStateManager.loadWindowStateFromFile(desktopPane);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = new JMenu(getMessage("menu.view"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem(getMessage("menu.view.system"), KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem(getMessage("menu.view.crossplatform"), KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu(getMessage("menu.tests"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem(getMessage("menu.tests.addLogMessage"), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }

        JMenuItem exitItem = new JMenuItem(getMessage("menu.exit"), KeyEvent.VK_E);
        exitItem.addActionListener((event) -> {
            WindowStateManager.saveWindowStateToFile(desktopPane);
            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.put("OptionPane.cancelButtonText", "Извиняюсь!");
            int result = JOptionPane.showConfirmDialog(this, getMessage("menu.exit.confirm"), getMessage("menu.exit"),
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        JMenu languageMenu = new JMenu("Язык");
        {
            JMenuItem russianItem = new JMenuItem("Русский", KeyEvent.VK_R);
            russianItem.addActionListener((event) -> {
                changeLocale(Locale.forLanguageTag("ru-RU"));
                updateUI();
            });
            languageMenu.add(russianItem);
        }
        {
            JMenuItem englishItem = new JMenuItem("Английский", KeyEvent.VK_E);
            englishItem.addActionListener((event) -> {
                changeLocale(Locale.forLanguageTag("en-US"));
                updateUI();
            });
            languageMenu.add(englishItem);
        }

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitItem);
        menuBar.add(languageMenu);
        return menuBar;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    private void changeLocale(Locale locale) {
        Locale.setDefault(locale);
        try {
            messages = ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            Logger.error("Resource bundle not found for locale: " + locale);
        }
    }

    private void updateUI() {
        setJMenuBar(generateMenuBar());
        gameWindow.updateTitle();
        SwingUtilities.updateComponentTreeUI(this);
    }

    private String getMessage(String key) {
        String pattern = messages.getString(key);
        MessageFormat messageFormat = messageFormatCache.computeIfAbsent(pattern, MessageFormat::new);
        return messageFormat.format(new Object[]{});
    }
}

