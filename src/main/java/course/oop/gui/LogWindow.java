package course.oop.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import course.oop.log.LogChangeListener;
import course.oop.log.LogEntry;
import course.oop.log.LogWindowSource;
import course.oop.saving.LoadException;
import course.oop.saving.SaveException;
import course.oop.saving.Saveable;
import course.oop.saving.WindowConfig;
import course.oop.saving.WindowConfigsIO;

public class LogWindow extends JInternalFrame implements LogChangeListener, Saveable {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
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
        wio.save("log_window", wc);
    }

    /**
     * Загружает конфигурацию окна из {@link WindowConfigsIO}
     * и применяет ее к окну. Если не удается загрузить данные об окне,
     * поднимается исключение.
     */
    @Override
    public void load() throws LoadException {
        WindowConfigsIO wio = WindowConfigsIO.getInstance();
        WindowConfig wc = wio.load("log_window");
        setLocation(wc.getLocation());
        setSize(wc.getSize());
        try {
            setIcon(wc.isIcon());
        } catch (PropertyVetoException e) {
            System.err.println("can't icon game_window");
        }
    }
}
