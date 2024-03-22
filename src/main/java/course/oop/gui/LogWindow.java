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
import course.oop.saving.Saveable;
import course.oop.saving.SaveableDelegate;
import course.oop.saving.FrameConfig;

public class LogWindow extends JInternalFrame implements LogChangeListener, Saveable {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    /**
     * Делегирует реализацию интерфейса Saveable этому объекту
     */
    private final SaveableDelegate saveableDelegate;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        saveableDelegate = new SaveableDelegate(this, "log");
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
