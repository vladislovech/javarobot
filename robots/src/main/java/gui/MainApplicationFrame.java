package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.prefs.Preferences;
import log.Logger;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String PREF_KEY_WINDOW_TYPE = "windowType_";
    private static final String PREF_NODE = "MyAppPrefs";
    private static final String PREF_KEY_WINDOW_COUNT = "windowCount";
    private static final String PREF_KEY_WINDOW_X = "windowX_";
    private static final String PREF_KEY_WINDOW_Y = "windowY_";
    private static final String PREF_KEY_WINDOW_WIDTH = "windowWidth_";
    private static final String PREF_KEY_WINDOW_HEIGHT = "windowHeight_";


    Locale locale = new Locale("ru", "RU");
    ResourceBundle bundle = ResourceBundle.getBundle("recources", locale);

    @Override
    public void dispose() {
        super.dispose();
        saveState();
    }


    private void saveState() {
        WindowStatePersistence.saveWindowState(desktopPane);
    }

    private void restoreState() {
        WindowStatePersistence.restoreWindowState(desktopPane);
    }


    public static void initializeUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Остальной код инициализации UI
        MainApplicationFrame frame = new MainApplicationFrame();
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        frame.setContentPane(frame.desktopPane);
        frame.setJMenuBar(frame.generateMenuBar());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.restoreState();
        frame.setVisible(true);
        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    private GameWindow createGameWindow() {
        int gameWindowWidth = 400;
        int gameWindowHeight = 400;

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(gameWindowWidth, gameWindowHeight);
        return gameWindow;
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuItem createMenuItem(String label, int mnemonic, String acceleratorKey, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        if (acceleratorKey != null && !acceleratorKey.isEmpty()) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(acceleratorKey));
        }
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    private JMenu createMenu(String label, int mnemonic) {
        JMenu menu = new JMenu(label);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu documentMenu = createMenu(bundle.getString("menu"), KeyEvent.VK_D);

        documentMenu.add(createMenuItem(bundle.getString("new_game_field"), KeyEvent.VK_N, "alt N", (event) -> addWindow(createGameWindow())));
        documentMenu.add(createMenuItem(bundle.getString("log_window"), KeyEvent.VK_L, "alt L", (event) -> addWindow(new LogWindow(Logger.getDefaultLogSource()))));
        documentMenu.add(createMenuItem(bundle.getString("exit"), KeyEvent.VK_Q, "alt Q", (event) -> confirmExit()));

        menuBar.add(documentMenu);

        JMenu viewMenu = createMenu(bundle.getString("display_mode"), KeyEvent.VK_V);
        viewMenu.add(createMenuItem(bundle.getString("system_diagram"), KeyEvent.VK_S, "ctrl S", (event) -> setLookAndFeel(UIManager.getSystemLookAndFeelClassName())));
        viewMenu.add(createMenuItem(bundle.getString("universal_scheme"), KeyEvent.VK_U, "ctrl U", (event) -> setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())));
        menuBar.add(viewMenu);

        JMenu testMenu = createMenu(bundle.getString("tests"), KeyEvent.VK_T);
        testMenu.add(createMenuItem(bundle.getString("message_in_the_log"), KeyEvent.VK_M, "alt M", (event) -> Logger.debug(bundle.getString("new_str"))));
        menuBar.add(testMenu);

        return menuBar;
    }


    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmExit() {
        String message = bundle.getString("exit?");
        UIManager.put("OptionPane.yesButtonText", bundle.getString("yes_button_text"));
        UIManager.put("OptionPane.noButtonText", bundle.getString("no_button_text"));
        int confirmation = JOptionPane.showConfirmDialog(this, message, bundle.getString("exit-yes"), JOptionPane.YES_NO_OPTION);
        saveState();
        if (confirmation == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

}