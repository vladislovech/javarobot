package course.oop.gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import course.oop.log.Logger;

public class MainMenuBar extends JMenuBar {
    private final MainApplicationFrame mainFrame;

    /**
     * Создает меню для переданного главного окна
     */
    public MainMenuBar(MainApplicationFrame mainFrame) {
        this.mainFrame = mainFrame;
        JMenu viewModeMenu = generateViewModeMenu();
        JMenu testMenu = generateTestsMenu();
        JMenu quitMenu = generateQuitMenu();

        add(viewModeMenu);
        add(testMenu);
        add(quitMenu);
    }

    /**
     * Создает пункт меню с режимами отображения.
     */
    private JMenu generateViewModeMenu() {
        JMenu ret = new JMenu("Режим отображения");
        ret.setMnemonic(KeyEvent.VK_V);
        ret.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        JMenuItem systemSchemeMenuItem = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemSchemeMenuItem.addActionListener((event) -> {
            mainFrame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        ret.add(systemSchemeMenuItem);

        JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            mainFrame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        ret.add(crossplatformLookAndFeel);
        return ret;
    }

    /**
     * Создает пункт меню с тестированием программы.
     */
    private JMenu generateTestsMenu() {
        JMenu ret = new JMenu("Тесты");
        ret.setMnemonic(KeyEvent.VK_T);
        ret.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        ret.add(addLogMessageItem);
        return ret;
    }

    /**
     * Создает пункт меню c функцией выхода из программы.
     */
    private JMenu generateQuitMenu() {
        JMenu ret = new JMenu("Выход");
        ret.setMnemonic(KeyEvent.VK_Q);
        ret.getAccessibleContext().setAccessibleDescription(
                "Выход из программы");
        JMenuItem quitItem = new JMenuItem("Выход", KeyEvent.VK_S);
        quitItem.addActionListener((event) -> {
            WindowEvent closeEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        });
        ret.add(quitItem);
        return ret;
    }
}
