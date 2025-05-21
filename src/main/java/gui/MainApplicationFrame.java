package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final LocalizationManager localizationManager;
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowStateManager stateManager = new WindowStateManager();
    private final List<StatefulWindow> statefulWindows = new ArrayList<>();
    private final ThemeManager themeManager;

    public MainApplicationFrame(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
        this.themeManager = new ThemeManager(localizationManager);

        themeManager.applyTheme(themeManager.getSavedTheme());

        setTitle(localizationManager.getString(LocalizationKeys.WINDOW_TITLE));

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow(localizationManager);
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(new MenuBarGenerator(this, localizationManager, themeManager).createMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmAndExit();
            }
        });

        restoreWindowsState();
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), localizationManager);
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(StatefulWindow frame)
    {
        desktopPane.add(frame.getWindow());
        frame.getWindow().setVisible(true);
        statefulWindows.add(frame);
    }

    public void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
            for (StatefulWindow window : statefulWindows) {
                SwingUtilities.updateComponentTreeUI(window.getWindow());
            }
        } catch (Exception e) {
            Logger.error("Ошибка при установке Look and Feel: " + e.getMessage());
        }
    }

    public void confirmAndExit() {
        String[] options = ExitConfirmationOption.getOptions(localizationManager);

        int result = JOptionPane.showOptionDialog(
                this,
                localizationManager.getString(LocalizationKeys.EXIT_CONFIRMATION_MESSAGE),
                localizationManager.getString(LocalizationKeys.EXIT_CONFIRMATION_TITLE),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (result == JOptionPane.YES_OPTION) {
            saveWindowsState();
            System.exit(0);
        }
    }

    public void updateAllUI() {
        setTitle(localizationManager.getString(LocalizationKeys.WINDOW_TITLE));
        SwingUtilities.updateComponentTreeUI(this);
        for (StatefulWindow window : statefulWindows) {
            SwingUtilities.updateComponentTreeUI(window.getWindow());
            if (window instanceof GameWindow) {
                ((GameWindow) window).updateTitle();
            } else if (window instanceof LogWindow) {
                ((LogWindow) window).updateTitle();
            }
        }
        setJMenuBar(new MenuBarGenerator(this, localizationManager, themeManager).createMenuBar());
    }
    
    private void saveWindowsState() {
        for (StatefulWindow window : statefulWindows) {
            stateManager.saveWindowState(window.getWindowId(), window.getWindow());
        }
    }

    private void restoreWindowsState() {
        for (StatefulWindow window : statefulWindows) {
            WindowStateManager.WindowState state = stateManager.loadWindowState(window.getWindowId());
            if (state != null) {
                JInternalFrame frame = window.getWindow();
                frame.setBounds(state.getBounds());
                try {
                    if (state.isIcon()) {
                        frame.setIcon(true);
                    } else if (state.isMaximized()) {
                        frame.setMaximum(true);
                    }
                } catch (Exception e) {
                    Logger.error("Ошибка восстановления состояния окна: " + e.getMessage());
                }
            }
        }
    }
}
