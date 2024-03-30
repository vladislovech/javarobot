package course.oop.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import course.oop.model.Game;
import course.oop.saving.Saveable;

public class GameWindow extends JInternalFrame implements Saveable {
    private final GameVisualizer m_visualizer;

    public GameWindow(Game game) {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer(game);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    /**
     * Возвращает свой уникальный идентификатор
     */
    @Override
    public String getFrameId() {
        return "game";
    }
}
