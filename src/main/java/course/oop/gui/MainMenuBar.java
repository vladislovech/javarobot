package course.oop.gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import course.oop.locale.UserLocale;
import course.oop.locale.UserLocaleManager;
import course.oop.log.Logger;

/**
 * Главная строка меню программы.
 * Добавляется к окну {@link MainApplicationFrame}
 */
public class MainMenuBar extends JMenuBar {
    private final MainApplicationFrame mainFrame;
    private ResourceBundle bundle;

    /**
     * Создает меню для переданного главного окна
     */
    public MainMenuBar(MainApplicationFrame mainFrame) {
        this.mainFrame = mainFrame;
        bundle = UserLocaleManager.getCurrentBundle();

        add(createViewModeMenu());
        add(createTestsMenu());
        add(createSettingMenu());
        add(createQuitMenu());
    }

    /**
     * Создает пункт меню с режимами отображения.
     */
    private JMenu createViewModeMenu() {
        JMenu ret = new JMenu(bundle.getString("view_mode_menu"));
        ret.setMnemonic(KeyEvent.VK_V);
        ret.getAccessibleContext().setAccessibleDescription(
                bundle.getString("view_mode_menu.accessible_description"));

        JMenuItem systemSchemeMenuItem = new JMenuItem(bundle.getString("system_scheme"), KeyEvent.VK_S);
        systemSchemeMenuItem.addActionListener((event) -> {
            mainFrame.setSystemLookAndFeel();
            this.invalidate();
        });
        ret.add(systemSchemeMenuItem);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(bundle.getString("universal_scheme"), KeyEvent.VK_U);
        crossplatformLookAndFeel.addActionListener((event) -> {
            mainFrame.setCrossPlatformLookAndFeel();
            this.invalidate();
        });
        ret.add(crossplatformLookAndFeel);
        return ret;
    }

    /**
     * Создает пункт меню с тестированием программы.
     */
    private JMenu createTestsMenu() {
        JMenu ret = new JMenu(bundle.getString("test_menu"));
        ret.setMnemonic(KeyEvent.VK_T);
        ret.getAccessibleContext().setAccessibleDescription(
                bundle.getString("test_menu.accessible_description"));
        JMenuItem addLogMessageItem = new JMenuItem(bundle.getString("log_message"), KeyEvent.VK_M);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(bundle.getString("log.new_string"));
        });
        ret.add(addLogMessageItem);
        return ret;
    }

    private JMenu createSettingMenu() {
        JMenu ret = new JMenu(bundle.getString("settings_menu"));
        ret.setMnemonic(KeyEvent.VK_I);
        ret.getAccessibleContext().setAccessibleDescription(
                bundle.getString("settings_menu.accessible_description"));
        JMenuItem changeLanguageItem = new JMenuItem(bundle.getString("settings_menu.change_lang"), KeyEvent.VK_M);
        changeLanguageItem.addActionListener((event) -> {
            SelectLanguageDialog ld = new SelectLanguageDialog(mainFrame);
            UserLocale result = ld.getResult();
            if (result != null) {
                UserLocaleManager.setUserLocale(result);
                JOptionPane.showMessageDialog(mainFrame, "ok");
            }
            mainFrame.repaint();
        });
        ret.add(changeLanguageItem);
        return ret;
    }

    /**
     * Создает пункт меню c функцией выхода из программы.
     */
    private JMenu createQuitMenu() {
        JMenu ret = new JMenu(bundle.getString("quit_menu"));
        ret.setMnemonic(KeyEvent.VK_Q);
        ret.getAccessibleContext().setAccessibleDescription(
                bundle.getString("quit_menu.accessible_description"));
        JMenuItem quitItem = new JMenuItem(bundle.getString("quit_menu"), KeyEvent.VK_Q);
        quitItem.addActionListener((event) -> {
            WindowEvent closeEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        });
        ret.add(quitItem);
        return ret;
    }
}
