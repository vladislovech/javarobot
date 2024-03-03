package course.oop.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import course.oop.log.Logger;
import course.oop.saving.LoadException;
import course.oop.saving.SaveException;
import course.oop.saving.Saveable;
import course.oop.saving.WindowConfig;
import course.oop.saving.WindowConfigsIO;

/**
 * Класс главного окна программы
 */
public class MainApplicationFrame extends JFrame implements Saveable {
    private final JDesktopPane desktopPane = new JDesktopPane();

    /**
     * Ссылка на дочернее окно. (формально, из за {@code desktopPane} - дочернее
     * дочернее)
     */
    private final LogWindow logWindow;
    /**
     * Ссылка на дочернее окно. (формально, из за {@code desktopPane} - дочернее
     * дочернее)
     */
    private final GameWindow gameWindow;

    /**
     * Создает главное окно программы
     */
    public MainApplicationFrame() {
        // Make the big window be indented 50 pixels from each edge
        // of the screen.

        try {
            load();
        } catch (LoadException e) {
            int inset = 50;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        }

        setContentPane(desktopPane);

        logWindow = createLogWindow();
        addWindow(logWindow);

        gameWindow = createGameWindow();
        addWindow(gameWindow);

        setJMenuBar(new MainMenuBar(this));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveWindowStates();
                startExitDialog();
            }
        });
    }

    /**
     * Устанавливает системный внещний вид для главного окна.
     */
    public void setSystemLookAndFeel() {
        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    /**
     * Устанавливает универсальный внещний вид для главного окна.
     */
    public void setCrossPlatformLookAndFeel() {
        setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }

    /**
     * Устанавливает внешний вид для главного окна на основе предоставленного класса
     * внешнего вида.
     * Вызывает UIManager.setLookAndFeel(className) и
     * SwingUtilities.updateComponentTreeUI(this).
     *
     * @param className Класс внешнего вида, который следует установить.
     * @see UIManager#setLookAndFeel
     */
    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    /**
     * Выполняет процедуру выхода из программы.
     */
    private void startExitDialog() {
        int userChoice = JOptionPane.showConfirmDialog(
                this,
                "Вы уверены?",
                "Выйти",
                JOptionPane.YES_NO_OPTION);
        if (userChoice == JOptionPane.YES_OPTION)
            setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Добавляет переданное окно в это (главное)
     * 
     * @param frame окно
     */
    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Создает окно лога
     */
    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        try {
            logWindow.load();
        } catch (LoadException e) {
            logWindow.setLocation(0, 0);
            logWindow.setSize(300, 800);
        }
        Logger.debug("Протокол работает");
        return logWindow;
    }

    /**
     * Создает игровое окно
     */
    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        try {
            gameWindow.load();
        } catch (LoadException e) {
            gameWindow.setLocation(300, 0);
            gameWindow.setSize(800, 800);
        }
        return gameWindow;
    }

    /**
     * Сохраняет состояния дочерних окон и главного окна с записью в файл.
     */
    private void saveWindowStates() {
        try {
            gameWindow.save();
        } catch (SaveException | LoadException e) {
            System.err.println("can't save state of game_window");
        }

        try {
            logWindow.save();
        } catch (SaveException | LoadException e) {
            System.err.println("can't save state of log_window");
        }

        try {
            save();
        } catch (SaveException | LoadException e) {
            System.err.println("can't save state of log_window");
        }

        try {
            WindowConfigsIO.getInstance().flush();
        } catch (IOException | LoadException e) {
            System.err.println("can't write states to file.");
        }
    }

    /**
     * Сохраняет текущую конфигурацию окна в {@link WindowConfigsIO}
     */
    @Override
    public void save() throws SaveException, LoadException {
        WindowConfigsIO wio = WindowConfigsIO.getInstance();
        WindowConfig wc = new WindowConfig(
                getSize(),
                getLocation(),
                false); // контролирует уже ос
        wio.save("main_window", wc);
    }

    /**
     * Загружает конфигурацию окна из {@link WindowConfigsIO}
     * и применяет ее к окну. Если не удается загрузить данные об окне,
     * поднимается исключение.
     */
    @Override
    public void load() throws LoadException {
        WindowConfigsIO wio = WindowConfigsIO.getInstance();
        WindowConfig wc = wio.load("main_window");
        setLocation(wc.getLocation());
        setSize(wc.getSize());
    }
}
