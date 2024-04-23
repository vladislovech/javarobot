package course.oop.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import course.oop.log.ConcurrentLogSource;
import course.oop.log.LogEntry;
import course.oop.log.Logger;
import course.oop.saving.Saveable;

public class LogWindow extends JInternalFrame implements Saveable, PropertyChangeListener {
    /**
     * Журнал логов (модель)
     */
    private ConcurrentLogSource logSource;
    private TextArea logContent;

    public LogWindow(ConcurrentLogSource logSource) {
        super("Протокол работы", true, true, true, true);
        setLocation(0, 0);
        setSize(300, 500);
        Logger.debug("Протокол работает.");
        this.logSource = logSource;
        logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }

    /**
     * Возвращает свой уникальный идентификатор
     */
    @Override
    public String getFrameId() {
        return "log";
    }

    /**
     * Обработчик события при обновлении лога
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("log update")) {
            EventQueue.invokeLater(this::updateLogContent);
        }
    }
}
