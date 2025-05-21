package gui;

import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.*;
import log.Logger;

public class MenuBarGenerator {

    private final MainApplicationFrame frame;
    private final LocalizationManager localizationManager;
    private final ThemeManager themeManager;

    public MenuBarGenerator(MainApplicationFrame frame, LocalizationManager localizationManager,
                            ThemeManager themeManager) {
        this.frame = frame;
        this.localizationManager = localizationManager;
        this.themeManager = themeManager;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createThemeMenu());
        menuBar.add(createTestMenu());
        menuBar.add(createFileMenu());
        menuBar.add(createLanguageMenu());

        return menuBar;
    }

    private JMenu createThemeMenu() {
        JMenu themeMenu = new JMenu(localizationManager.getString(LocalizationKeys.MENU_THEME));
        themeMenu.setMnemonic(KeyEvent.VK_T);
        themeMenu.getAccessibleContext().setAccessibleDescription(
                localizationManager.getString(LocalizationKeys.MENU_THEME_DESCRIPTION));

        JMenuItem lightTheme = new JMenuItem(localizationManager.getString(LocalizationKeys.THEME_LIGHT));
        lightTheme.addActionListener(e -> {
            themeManager.previewTheme("light", frame);
            frame.updateAllUI();
        });
        themeMenu.add(lightTheme);

        JMenuItem darkTheme = new JMenuItem(localizationManager.getString(LocalizationKeys.THEME_DARK));
        darkTheme.addActionListener(e -> {
            themeManager.previewTheme("dark", frame);
            frame.updateAllUI();
        });
        themeMenu.add(darkTheme);

        JMenuItem contrastTheme = new JMenuItem(localizationManager.getString(LocalizationKeys.THEME_CONTRAST));
        contrastTheme.addActionListener(e -> {
            themeManager.previewTheme("contrast", frame);
            frame.updateAllUI();
        });
        themeMenu.add(contrastTheme);

        return themeMenu;
    }

    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(localizationManager.getString(LocalizationKeys.MENU_LOOK_AND_FEEL));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                localizationManager.getString(LocalizationKeys.MENU_LOOK_AND_FEEL_DESCRIPTION));

        JMenuItem systemLookAndFeel = new JMenuItem(localizationManager.getString(LocalizationKeys.MENU_SYSTEM_THEME),
                KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.updateAllUI();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(localizationManager.getString(LocalizationKeys.MENU_CROSSPLATFORM_THEME),
                KeyEvent.VK_U);
        crossplatformLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.updateAllUI();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = new JMenu(localizationManager.getString(LocalizationKeys.MENU_TESTS));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                localizationManager.getString(LocalizationKeys.MENU_TESTS_DESCRIPTION));

        JMenuItem addLogMessageItem = new JMenuItem(localizationManager.getString(LocalizationKeys.MENU_ADD_LOG_MESSAGE),
                KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(localizationManager.getString(LocalizationKeys.LOG_NEW_MESSAGE));
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu(localizationManager.getString(LocalizationKeys.MENU_FILE));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem exitItem = new JMenuItem(localizationManager.getString(LocalizationKeys.MENU_EXIT), KeyEvent.VK_Q);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener((event) -> {
            frame.confirmAndExit();
        });
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu createLanguageMenu() {
        JMenu languageMenu = new JMenu(localizationManager.getString(LocalizationKeys.MENU_LANGUAGE));
        languageMenu.setMnemonic(KeyEvent.VK_L);

        JMenuItem russianItem = new JMenuItem(localizationManager.getString(LocalizationKeys.LANGUAGE_RUSSIAN));
        russianItem.addActionListener(e -> {
            localizationManager.setLocale(new Locale("ru", "RU"));
            updateAllUI();
        });
        languageMenu.add(russianItem);

        JMenuItem englishItem = new JMenuItem(localizationManager.getString(LocalizationKeys.LANGUAGE_ENGLISH));
        englishItem.addActionListener(e -> {
            localizationManager.setLocale(Locale.US);
            updateAllUI();
        });
        languageMenu.add(englishItem);

        return languageMenu;
    }

    private void updateAllUI() {
        frame.updateAllUI();
    }
}
