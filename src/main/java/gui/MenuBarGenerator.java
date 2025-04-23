package gui;

import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.*;
import log.Logger;

public class MenuBarGenerator {

    private final MainApplicationFrame frame;

    public MenuBarGenerator(MainApplicationFrame frame) {
        this.frame = frame;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createTestMenu());
        menuBar.add(createFileMenu());
        menuBar.add(createLanguageMenu());

        return menuBar;
    }

    private JMenu createLookAndFeelMenu() {
        LocalizationManager lm = LocalizationManager.getInstance();
        JMenu lookAndFeelMenu = new JMenu(lm.getString("menu.look_and_feel"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                lm.getString("menu.look_and_feel.description"));

        JMenuItem systemLookAndFeel = new JMenuItem(lm.getString("menu.system_theme"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(lm.getString("menu.crossplatform_theme"), KeyEvent.VK_U);
        crossplatformLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        LocalizationManager lm = LocalizationManager.getInstance();
        JMenu testMenu = new JMenu(lm.getString("menu.tests"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                lm.getString("menu.tests.description"));

        JMenuItem addLogMessageItem = new JMenuItem(lm.getString("menu.add_log_message"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(lm.getString("log.new_message"));
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    private JMenu createFileMenu() {
        LocalizationManager lm = LocalizationManager.getInstance();
        JMenu fileMenu = new JMenu(lm.getString("menu.file"));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem exitItem = new JMenuItem(lm.getString("menu.exit"), KeyEvent.VK_Q);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener((event) -> {
            frame.confirmAndExit();
        });
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu createLanguageMenu() {
        LocalizationManager lm = LocalizationManager.getInstance();
        JMenu languageMenu = new JMenu(lm.getString("menu.language"));
        languageMenu.setMnemonic(KeyEvent.VK_L);

        JMenuItem russianItem = new JMenuItem(lm.getString("language.russian"));
        russianItem.addActionListener(e -> {
            LocalizationManager.getInstance().setLocale(new Locale("ru", "RU"));
            updateAllUI();
        });
        languageMenu.add(russianItem);

        JMenuItem englishItem = new JMenuItem(lm.getString("language.english"));
        englishItem.addActionListener(e -> {
            LocalizationManager.getInstance().setLocale(Locale.US);
            updateAllUI();
        });
        languageMenu.add(englishItem);

        return languageMenu;
    }

    private void updateAllUI() {
        frame.updateAllUI();
    }
}
