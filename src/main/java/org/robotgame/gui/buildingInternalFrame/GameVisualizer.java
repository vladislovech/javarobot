package org.robotgame.gui.buildingInternalFrame;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameVisualizer extends JPanel
{
    private final Timer m_timer = initTimer();

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile int m_targetPositionX = 100;
    private volatile int m_targetPositionY = 100;
    private volatile int m_targetPositionXold = 0;
    private volatile int m_targetPositionYold = 0;

    private static final double maxVelocity = 0.06;
    private static final double maxAngularVelocity = 0.01;
    public GameVisualizer()
    {
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 7);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 5);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getX(), e.getY());
                repaint();
            }
        });
        setDoubleBuffered(true);
        setFocusable(true);
    }

    protected void setTargetPosition(int x, int y)
    {
       if (this.getWidth() != 0 && this.getHeight() != 0)
       {
           x = (int) applyLimits(x, 0, getWidth());
           y = (int) applyLimits(y, 0, getHeight());
       }
        if (distance(x, y, m_targetPositionX, m_targetPositionY) < 5)
            return;

        m_targetPositionXold = m_targetPositionX;
        m_targetPositionYold = m_targetPositionY;

        m_targetPositionX = x;
        m_targetPositionY = y;
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected void onModelUpdateEvent() {
        setTargetPosition(m_targetPositionX, m_targetPositionY);

        double distance = distance(m_targetPositionX, m_targetPositionY, m_robotPositionX, m_robotPositionY);
        if (distance <  1) {
            return;
        }

        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);

        double angleDifference;
        if (Math.abs(angleToTarget - m_robotDirection) > Math.exp(-17))
        {
            angleDifference = angleToTarget - m_robotDirection;
        } else {
            angleDifference = Math.exp(-16);
        }

        if (Math.abs(angleDifference) > Math.PI) {
            angleDifference -= Math.signum(angleDifference) * 2 * Math.PI;
        }
        double angularVelocity = maxAngularVelocity * angleDifference;

        moveRobot(velocity, angularVelocity, 5, distance);
    }



    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration, double distance) {
        double diffTargets = distance(m_targetPositionX, m_targetPositionY, m_targetPositionXold, m_targetPositionYold);

        if ((diffTargets < 30 && distance <=3))
        {
            angularVelocity = applyLimits(angularVelocity*5, -maxAngularVelocity * 5, maxAngularVelocity * 5);
        }
        else if (diffTargets < 30)
        {
            angularVelocity = applyLimits(angularVelocity*3, -maxAngularVelocity * 3, maxAngularVelocity * 3);
        }
        else
        {
            angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        }
        velocity = applyLimits(velocity, 0, maxVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = applyLimits(newX, 0, this.getWidth());
        m_robotPositionY = applyLimits(newY, 0, this.getHeight());

        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }


    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    private static int round(double value)
    {
        return (int)(value + 0.5);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(m_robotPositionX), round(m_robotPositionY), m_robotDirection);
        drawTarget(g2d, m_targetPositionX, m_targetPositionY);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = round(m_robotPositionX);
        int robotCenterY = round(m_robotPositionY);

        AffineTransform originalTransform = g.getTransform();

        g.rotate(direction, robotCenterX, robotCenterY);

        g.setColor(Color.CYAN);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);

        g.setTransform(originalTransform);
    }

    private void drawTarget(Graphics2D g, int x, int y)
    {

        g.setColor(Color.RED);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
    public Point getTargetPoint(){
        return new Point(m_targetPositionX, m_targetPositionY);
    }
    public Point getRobotPoint(){
            return new Point((int) m_robotPositionX, (int) m_robotPositionY);
    }
    public void setRobotPosition(int x, int y){
        if (this.getWidth() != 0 && this.getHeight() != 0)
        {
            x = (int) applyLimits(x, 0, getWidth());
            y = (int) applyLimits(y, 0, getHeight());
        }

        m_robotPositionX = x;
        m_robotPositionY = y;
    }
}
