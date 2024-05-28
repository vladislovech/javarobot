package gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import static gui.MenuBar.getLocaleString;

public class GameWindow extends inter
{
    private final GameVisualizer m_visualizer;
    public GameWindow()
    {
        super(getLocaleString("gamePlane", Locale.getDefault()), true, true, true, true);
        setTitle(getLocaleString("gamePlane", Locale.getDefault()));

        try {
            m_visualizer = new GameVisualizer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void updateWindow() {
        setTitle(getLocaleString("gamePlane", Locale.getDefault()));
    }

}
