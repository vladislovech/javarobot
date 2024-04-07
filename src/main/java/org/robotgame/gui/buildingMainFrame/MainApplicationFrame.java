package org.robotgame.gui.buildingMainFrame;

import org.robotgame.gui.LocalizationManager;
import org.robotgame.gui.buildingInternalFrame.AbstractWindow;
import org.robotgame.gui.buildingInternalFrame.GameWindow;
import org.robotgame.gui.buildingInternalFrame.LogWindow;
import org.robotgame.gui.buildingInternalFrame.MiniMapWindow;
import org.robotgame.log.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApplicationFrame extends JFrame {
    private static JDesktopPane desktopPane = null;
    AbstractWindow logWindow;
    AbstractWindow gameWindow;
    AbstractWindow minimapWindow;

    public MainApplicationFrame() {
        desktopPane = new JDesktopPane();

        logWindow = createLogWindow();
        gameWindow = createGameWindow();
        minimapWindow = createMiniMapWindow();

        setContentPane(desktopPane);

        addWindow(logWindow);
        addWindow(gameWindow);
        addWindow(minimapWindow);

        setJMenuBar(MenuBarBuilder.buildMenuBar(this));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(MainApplicationFrame.this,
                        LocalizationManager.getString("window.closing.message"),
                        LocalizationManager.getString("confirmation"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    closeAllWindows();
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    protected AbstractWindow createLogWindow() {
        AbstractWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10); // Расположение в верхнем левом углу
        logWindow.setSize(300, 800);
        Logger.debug(LocalizationManager.getString("logger.logsAreRunning"));
        return logWindow;
    }

    protected AbstractWindow createGameWindow() {
        int gameWindowWidth = 600;
        int gameWindowHeight = 600;
        AbstractWindow gameWindow = new GameWindow(gameWindowWidth, gameWindowHeight);
        gameWindow.setSize(gameWindowWidth, gameWindowHeight);
        gameWindow.setLocation(320, 10);
        return gameWindow;
    }

    protected AbstractWindow createMiniMapWindow() {
        int minimapWindowWidth = 200;
        int minimapWindowHeight = 200;
        AbstractWindow minimapWindow = new MiniMapWindow(this.gameWindow);
        minimapWindow.setSize(minimapWindowWidth, minimapWindowHeight);
        minimapWindow.setLocation(930, 10);
        return minimapWindow;
    }


    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void closeAllWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof AbstractWindow) {
                frame.dispose();
            }
        }
    }

    public void updateDesktopPane() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof AbstractWindow) {
                ((AbstractWindow) frame).updateLabels();
            }
        }
        updateMainFrame();
    }

    public void updateMainFrame() {
        setJMenuBar(MenuBarBuilder.buildMenuBar(this));
        desktopPane.revalidate();
        desktopPane.repaint();
    }

    public void restoreAllWindows() {
    }
}
