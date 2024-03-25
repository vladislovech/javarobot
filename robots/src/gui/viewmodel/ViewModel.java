package gui.viewmodel;

import gui.model.Model;
import gui.model.RobotEntity;
import gui.view.GameWindow;
import gui.view.LogWindow;
import gui.view.MainApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class ViewModel extends JPanel {
    private final Model model;
    RobotEntity robot = new RobotEntity();
    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    private final Timer m_timer = initTimer();

    public ViewModel() {
        model = new Model(robot);

        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                robot.onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                robot.setTargetPosition(e.getPoint());
                updateLogic();
                repaint();
            }
        });
        setDoubleBuffered(true);
    }
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    public void updateLogic() {
        model.updateModel();
    }
//    public void render() {
//
//    }
}
