package gui;

import java.awt.*;
import java.awt.event.WindowListener;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

import static gui.MenuBar.getLocaleString;

public class LogWindow extends inter implements LogChangeListener
{
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) 
    {
        super(getLocaleString("workProtocol", Locale.getDefault()), true, true, true, true);
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
    public void doDefaultCloseAction() {
        m_logSource.unregisterListener(this);
        super.doDefaultCloseAction();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    public void updateWindow() {
        setTitle(getLocaleString("workProtocol", Locale.getDefault()));
    }
}
