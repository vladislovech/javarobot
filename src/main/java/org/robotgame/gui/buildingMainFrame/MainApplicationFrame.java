package org.robotgame.gui.buildingMainFrame;

import org.robotgame.gui.buildingInternalFrame.AbstractWindow;
import org.robotgame.gui.buildingInternalFrame.GameWindow;
import org.robotgame.gui.buildingInternalFrame.LogWindow;
import org.robotgame.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private AbstractWindow logWindow = createLogWindow();
    private AbstractWindow gameWindow = createGameWindow();

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        addWindow(logWindow);
        addWindow(gameWindow);

        setJMenuBar(MenuBarBuilder.buildMenuBar());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(MainApplicationFrame.this,
                        "Вы уверены, что хотите закрыть окно?", "Подтверждение", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    protected AbstractWindow createLogWindow() {
        AbstractWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        logWindow.pack();
        Logger.debug("Логи запущены");
        return logWindow;
    }

    protected AbstractWindow createGameWindow() {
        AbstractWindow gameWindow = new GameWindow();
        gameWindow.setSize(500, 500);
        return gameWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
