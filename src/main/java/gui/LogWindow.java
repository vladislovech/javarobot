package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, StatefulWindow
{
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private final LocalizationManager localizationManager;

    public LogWindow(LogWindowSource logSource, LocalizationManager localizationManager) {
        super(localizationManager.getString(LocalizationKeys.LOG_WINDOW_TITLE),
                true,
                true,
                true,
                true);
        this.localizationManager = localizationManager;
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

    public void updateTitle() {
        setTitle(localizationManager.getString(LocalizationKeys.LOG_WINDOW_TITLE));
    }

    @Override
    public String getWindowId() {
        return "log_window";
    }

    @Override
    public JInternalFrame getWindow() {
        return this;
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
