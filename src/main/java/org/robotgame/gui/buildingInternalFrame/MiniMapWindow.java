package org.robotgame.gui.buildingInternalFrame;

import org.robotgame.GameController.Minimap;
import org.robotgame.GameController.GameVisualizer;
import org.robotgame.gui.LocalizationManager;

import javax.swing.*;
import java.awt.*;

public class MiniMapWindow extends AbstractWindow{
    private final Minimap m_minimap;
    public MiniMapWindow(AbstractWindow gameWindow) {
        super(LocalizationManager.getString("minimap.title"), false, true, false, false);
        m_minimap = new Minimap(gameWindow.get_visualizer());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_minimap, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    protected void closeWindow() {
    }

    @Override
    public void updateLabels() {
        setTitle(LocalizationManager.getString("minimap.title"));
        revalidate();
        repaint();
    }

    @Override
    public GameVisualizer get_visualizer() {
        return null;
    }
}
