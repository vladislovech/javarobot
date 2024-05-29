package gui;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {
    public final GameVisualizer m_visualizer = new GameVisualizer();
    //private ResourceBundle messages;

    public GameWindow() {
        super(MainApplicationFrame.messages.getString("game.window.title"), true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void updateTitle() {
        setTitle(MainApplicationFrame.messages.getString("game.window.title"));
    }
}
