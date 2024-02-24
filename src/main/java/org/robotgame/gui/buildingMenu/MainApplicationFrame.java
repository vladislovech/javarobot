package org.robotgame.gui.buildingMenu;

import org.robotgame.gui.GameWindow;
import org.robotgame.gui.LogWindow;
import org.robotgame.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private LogWindow logWindow = createLogWindow();
    private GameWindow gameWindow = createGameWindow();

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        setContentPane(desktopPane);

        addWindow(logWindow);
        addWindow(gameWindow);

        setJMenuBar(MenuBarBuilder.buildMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        logWindow.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
                closeLogWindow();
            }
        });
        gameWindow.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
                closeGameWindow();
            }
        });
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        logWindow.pack();
        Logger.debug("Лог работает");
        return logWindow;
    }

    protected GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        return gameWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public void closeLogWindow() {
        logWindow.dispose();
    }

    public void closeGameWindow() {
        gameWindow.dispose();
    }
}
