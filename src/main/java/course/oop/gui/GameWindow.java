package course.oop.gui;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import course.oop.saving.LoadException;
import course.oop.saving.SaveException;
import course.oop.saving.Saveable;
import course.oop.saving.WindowConfig;
import course.oop.saving.WindowConfigsIO;

public class GameWindow extends JInternalFrame implements Saveable {
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameIconified(InternalFrameEvent event) {
                try {
                    save();
                } catch (SaveException | LoadException e) {
                    System.err.println("can't save game window.");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Сохраняет текущую конфигурацию окна в {@link WindowConfigsIO}
     */
    @Override
    public void save() throws SaveException, LoadException {
        WindowConfigsIO wio = WindowConfigsIO.getInstance();
        WindowConfig wc = new WindowConfig(
                getSize(),
                getLocation(),
                isIcon());
        wio.save("game_window", wc);
    }

    /**
     * Загружает конфигурацию окна из {@link WindowConfigsIO}
     * и применяет ее к окну. Если не удается загрузить данные об окне,
     * поднимается исключение.
     */
    @Override
    public void load() throws LoadException {
        WindowConfigsIO wio = WindowConfigsIO.getInstance();
        WindowConfig wc = wio.load("game_window");
        setLocation(wc.getLocation());
        setSize(wc.getSize());
        try {
            setIcon(wc.isIcon());
        } catch (PropertyVetoException e) {
            System.err.println("can't icon game_window");
        }
    }
}
