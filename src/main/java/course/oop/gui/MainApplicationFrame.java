package course.oop.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import course.oop.log.Logger;
import course.oop.saving.Saveable;
import course.oop.saving.FrameConfig;

/**
 * Класс главного окна программы
 */
public class MainApplicationFrame extends JFrame implements Saveable {
    private final JDesktopPane desktopPane;

    /**
     * Создает главное окно программы
     */
    public MainApplicationFrame() {
        desktopPane = new JDesktopPane();
        // Make the big window be indented 50 pixels from each edge
        // of the screen.

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        addWindow(createLogWindow());
        addWindow(createGameWindow());

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
     * Добавляет переданное окно в это (главное).
     * формально в {@code desktopPane} - контейнер внутри этого окна.
     * 
     * @param frame окно
     */
    private void addWindow(JInternalFrame frame) {
        SwingUtilities.invokeLater(() -> {
            desktopPane.add(frame);
            frame.setVisible(true);
        });
    }

    /**
     * Создает окно лога
     */
    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(0, 0);
        logWindow.setSize(300, 500);
        Logger.debug("Протокол работает");
        return logWindow;
    }

    /**
     * Создает игровое окно
     */
    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setLocation(300, 0);
        gameWindow.setSize(500, 500);
        return gameWindow;
    }

    /**
     * Сохраняет состояния дочерних окон и главного окна с записью в файл.
     */
    private void saveWindowStates() {
        // TODO
    }

    /**
     * Возвращает свой уникальный идентификатор
     */
    @Override
    public String getFrameId() {
        return "main";
    }

    /**
     * Возвращает свое текущее состояние
     */
    @Override
    public FrameConfig getWindowConfig() {
        return new FrameConfig(getSize(), getLocation(), false);
    }
}
