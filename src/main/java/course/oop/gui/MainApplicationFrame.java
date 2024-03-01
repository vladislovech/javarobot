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

/**
 * Класс главного окна программы
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    /**
     * Создает главное окно программы
     */
    public MainApplicationFrame() {
        // Make the big window be indented 50 pixels from each edge
        // of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow();
        addWindow(gameWindow);

        setJMenuBar(new MainMenuBar(this));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
        logWindow.setLocation(0, 0);
        logWindow.setSize(300, 800);
        Logger.debug("Протокол работает");
        return logWindow;
    }

    /**
     * Создает игровое окно
     */
    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setLocation(300, 0);
        gameWindow.setSize(800, 800);
        return gameWindow;
    }

}
