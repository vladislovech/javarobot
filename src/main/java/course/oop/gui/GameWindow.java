package course.oop.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import course.oop.saving.Saveable;
import course.oop.saving.SaveableDelegate;
import course.oop.saving.FrameConfig;

public class GameWindow extends JInternalFrame implements Saveable {
    private final GameVisualizer m_visualizer;
    /**
     * Делегирует реализацию интерфейса Saveable этому объекту
     */
    private final SaveableDelegate saveableDelegate;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        saveableDelegate = new SaveableDelegate(this, "game");
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
        return saveableDelegate.getFrameId();
    }

    /**
     * Возвращает свое текущее состояние
     */
    @Override
    public FrameConfig getWindowConfig() {
        return saveableDelegate.getWindowConfig();
    }

    /**
     * Устанавливает параметры окна в соответствии с переданной конфигурацией
     */
    @Override
    public void loadConfig(FrameConfig config) {
        saveableDelegate.loadConfig(config);
    }
}
