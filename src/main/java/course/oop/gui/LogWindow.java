package course.oop.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import course.oop.log.LogChangeListener;
import course.oop.log.LogEntry;
import course.oop.log.LogJournal;
import course.oop.log.Logger;
import course.oop.saving.Saveable;

public class LogWindow extends JInternalFrame implements Saveable, LogChangeListener{
    /**
     * Журнал логов (модель)
     */
    private LogJournal logSource;
    private TextArea logContent;

    public LogWindow(LogJournal logSource) {
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

    /**
     * Дописывает в окно логов новую (последнюю) запись лога
     */
    private void updateLogContent() {
        StringBuilder content = new StringBuilder(logContent.getText());        
        content.append(logSource.getLastLogEntry().getMessage()).append("\n");
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

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
