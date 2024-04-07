package org.robotgame.gui.buildingInternalFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JPanel;

import org.robotgame.GameController.GameVisualizer;
import org.robotgame.gui.LocalizationManager;
import org.robotgame.log.LogChangeListener;
import org.robotgame.log.LogEntry;
import org.robotgame.log.LogWindowSource;

public class LogWindow extends AbstractWindow implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource)
    {
        super(LocalizationManager.getString("logWindow.theProtocolOfWork"), true, true, true, true);
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
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void closeWindow() {
        m_logSource.unregisterListener(this);
    }
    @Override
    public void updateLabels() {
        setTitle(LocalizationManager.getString("logWindow.theProtocolOfWork"));
        revalidate();
        repaint();
    }

    @Override
    public GameVisualizer get_visualizer() {
        return null;
    }
}

