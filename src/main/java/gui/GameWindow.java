package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements StatefulWindow
{
    private final LocalizationManager localizationManager;
    private final GameVisualizer m_visualizer;

    public GameWindow(LocalizationManager localizationManager) {
        super(localizationManager.getString(LocalizationKeys.GAME_WINDOW_TITLE),
                true,
                true,
                true,
                true);
        this.localizationManager = localizationManager;
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void updateTitle() {
        setTitle(localizationManager.getString(LocalizationKeys.GAME_WINDOW_TITLE));
    }
    
    @Override
    public String getWindowId() {
        return "game_window";
    }

    @Override
    public JInternalFrame getWindow() {
        return this;
    }
}
