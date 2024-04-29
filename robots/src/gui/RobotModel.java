package gui;

import java.awt.Point;
import java.util.Observable;
import static java.lang.Math.abs;


public class RobotModel extends Observable{

    protected volatile double m_robotPositionX = 100;
    protected volatile double m_robotPositionY = 100;
    protected volatile double m_robotDirection = 0;

    protected volatile int m_targetPositionX = 150;
    protected volatile int m_targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001*3;


    protected void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x*2;
        m_targetPositionY = p.y*2;
    }
    static int round(double value)
    {
        return (int)(value + 0.5);
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

    protected void onModelUpdateEvent()
    {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;
        if (abs(angleToTarget - m_robotDirection) < Math.PI) {
            if (angleToTarget > m_robotDirection) {
                angularVelocity = maxAngularVelocity;
            }
            else if (angleToTarget < m_robotDirection) {
                angularVelocity = -maxAngularVelocity;
            }
        }
        else {
            if (angleToTarget > m_robotDirection) {
                angularVelocity = -maxAngularVelocity;
            }
            else if (angleToTarget < m_robotDirection) {
                angularVelocity = maxAngularVelocity;
            }
        }

        moveRobot(velocity, angularVelocity, 10);
        setChanged();
        notifyObservers();
    }


    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
    velocity = applyLimits(velocity, 0, maxVelocity);
    angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
    double newX = m_robotPositionX + velocity / angularVelocity *
            (Math.sin(m_robotDirection + angularVelocity * duration) -
                    Math.sin(m_robotDirection));

    if (!Double.isFinite(newX)) {
        newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
    }
    double newY = m_robotPositionY - velocity / angularVelocity *
            (Math.cos(m_robotDirection + angularVelocity * duration) -
                    Math.cos(m_robotDirection));
    if (!Double.isFinite(newY)) {
        newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
    }

    
    if (newX < 0) {
        newX = 0; 
        m_robotDirection = Math.PI - m_robotDirection; 
    }
    if (newY < 0) {
        newY = 0; 
        m_robotDirection = -m_robotDirection; 
    }


    m_robotPositionX = newX;
    m_robotPositionY = newY;
    m_robotDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
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
    public double getX() {
        return m_robotPositionX;
    }
    public double getY() {
        return m_robotPositionY;
    }
}
