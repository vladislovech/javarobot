package gui.view;

import gui.model.RobotEntity;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

public class RobotRenderer extends JPanel implements EntityRenderer<RobotEntity> {
    public void render(RobotEntity robot) {}
    private static int round(double value)
    {
        return (int)(value + 0.5);
    }
    @Override
    public void paint(Graphics g, RobotEntity robot)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        double[] robotPosition = robot.getRobotPosition();
        double m_robotPositionX = robotPosition[0];
        double m_robotPositionY = robotPosition[1];

        int[] targetPosition = robot.getTargetPosition();
        int m_targetPositionX = targetPosition[0];
        int m_targetPositionY = targetPosition[1];

        double m_robotDirection = robot.getRobotDirection();

        drawRobot(g2d, round(m_robotPositionX), round(m_robotPositionY), m_robotDirection, robot);
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

    private void drawRobot(Graphics2D g, int x, int y, double direction, RobotEntity robot)
    {
        double[] robotPosition = robot.getRobotPosition();
        double m_robotPositionX = robotPosition[0];
        double m_robotPositionY = robotPosition[1];
        int robotCenterX = round(m_robotPositionX);
        int robotCenterY = round(m_robotPositionY);
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
}
