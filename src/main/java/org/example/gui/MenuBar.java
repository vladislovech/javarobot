package gui;


import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;



public class MenuBar {

    static MainApplicationFrame mainFrame;

    public MenuBar(MainApplicationFrame mainFrame){
        this.mainFrame = mainFrame;
    }


    protected static JMenu createLookAndFeelMenu() {

        JMenu lookAndFeelMenu = new JMenu(getLocaleString("displayMode", Locale.getDefault()));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                getLocaleString("systemMap", Locale.getDefault()));

        {
            JMenuItem systemLookAndFeel = new JMenuItem(getLocaleString("modeControl", Locale.getDefault()), KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem(getLocaleString("universityMap", Locale.getDefault()), KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        return lookAndFeelMenu;
    }


    protected static JMenu createTestMenu() {
        JMenu testMenu = new JMenu(getLocaleString("test", Locale.getDefault()));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                getLocaleString("testCommand", Locale.getDefault()));

        {
            JMenuItem addLogMessageItem = new JMenuItem(getLocaleString("messageLog", Locale.getDefault()), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug(getLocaleString("newString", Locale.getDefault()));
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }

    protected static JMenu createLanguage() {
        JMenu language = new JMenu(getLocaleString("language", Locale.getDefault()));


        {
            JMenuItem English = new JMenuItem(getLocaleString("englishLang", Locale.getDefault()));
            English.addActionListener((event) -> {
                mainFrame.changeLocale(new Locale("en", "US"));
            });

            JMenuItem Russian = new JMenuItem(getLocaleString("russianLang", Locale.getDefault()));
            Russian.addActionListener((event) -> {
                mainFrame.changeLocale(new Locale("ru", "RU"));
            });
            language.add(English);
            language.add(Russian);
        }

        return language;
    }


    public static String getLocaleString(String key, Locale locale) {
        ResourceBundle rb;
        rb = ResourceBundle.getBundle("resources", locale);
        return rb.getString(key);
    }

    protected static JMenu createExitButton() {
        JMenu menu = new JMenu(getLocaleString("exit", Locale.getDefault()));
        menu.setMnemonic(KeyEvent.VK_T);

        {
            JMenuItem exit1 = new JMenuItem(getLocaleString("exit", Locale.getDefault()), KeyEvent.VK_S);
            exit1.setFocusable(false);
            exit1.addActionListener((event) -> {
                Object[] options = {getLocaleString("yes", Locale.getDefault()), getLocaleString( "no", Locale.getDefault())};
                int n = JOptionPane
                        .showOptionDialog(null , getLocaleString("closeWindow", Locale.getDefault()),
                                getLocaleString("accept", Locale.getDefault()), JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    System.exit(0);
                }
            });
            menu.add(exit1);
        }
        return menu;
    }


    private static void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(mainFrame);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

}

