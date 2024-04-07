package org.robotgame.gui.buildingInternalFrame;

import org.robotgame.gui.LocalizationManager;
import org.robotgame.GameController.GameVisualizer;

import java.awt.*;

import javax.swing.JPanel;

public class GameWindow extends AbstractWindow
{
    private final GameVisualizer m_visualizer;
    public GameWindow(int width, int height) {
        super(LocalizationManager.getString("gameWindow.thePlayingField"), true, true, true, true);
        m_visualizer = new GameVisualizer(width, height);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    protected void closeWindow() {
    }

    public void updateLabels() {
        setTitle(LocalizationManager.getString("gameWindow.thePlayingField"));
        revalidate();
        repaint();
    }
}
