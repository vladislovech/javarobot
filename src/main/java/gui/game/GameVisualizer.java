package gui.game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements PropertyChangeListener
{
    private double robotX = 100;
    private double robotY= 100;
    private double robotDir = 0;
    private int targetX = 150;
    private int targetY = 100;

    private final GameController m_controller;
    public GameVisualizer(GameModel model)
    {
        m_controller = new GameController(model);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                m_controller.setTargetPosition(e.getPoint());
            }
        });
        setDoubleBuffered(true);
        model.addNewListener(this);
    }

    private static int round(double value)
    {
        return (int)(value + 0.5);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawRobot(g2d, round(robotX),
                round(robotY),
                robotDir);
        drawTarget(g2d, targetX, targetY);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int robotCenterX, int robotCenterY, double direction)
    {
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY); 
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameModel model = (GameModel) evt.getSource();
        robotX = model.getRobotPositionX();
        robotY = model.getRobotPositionY();
        robotDir = model.getRobotDirection();
        targetX = model.getTargetPositionX();
        targetY = model.getTargetPositionY();
        EventQueue.invokeLater(this::repaint);
    }
}
