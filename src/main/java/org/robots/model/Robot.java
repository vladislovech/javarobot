package org.robots.model;

import java.util.Observable;

public class Robot extends Observable{
    private volatile double robotPositionX;
    private volatile double robotPositionY;
    private volatile double robotDirection = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;
    public static final String robotMoved = "robot moved";

    public Robot(int x, int y){
        super();
        robotPositionX = x;
        robotPositionY = y;
    }

    public String getInfo(){
        return String.format("Positon: (%f, %f) \n Direction: %f",
                robotPositionX, robotPositionY, robotDirection);
    }

    public double getRobotPositionX(){
        return robotPositionX;
    }

    public double getRobotPositionY(){
        return robotPositionY;
    }

    public double getRobotDirection(){
        return robotDirection;
    }

    public void update(double targetPositionX, double targetPositionY){
        double distance = distance(targetPositionX, targetPositionY,
                robotPositionX, robotPositionY);
        if (distance < 0.5) {
            return;
        }

        double velocity = maxVelocity;
        double angleToTarget = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        double angularVelocity = 0;

        if (angleToTarget > robotDirection) {
            angularVelocity = maxAngularVelocity;
        }

        if (angleToTarget < robotDirection) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
        setChanged();
        notifyObservers(robotMoved);
        clearChanged();
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2*Math.PI;
        }

        while (angle >= 2*Math.PI) {
            angle -= 2*Math.PI;
        }

        return angle;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection  + angularVelocity * duration) -
                        Math.sin(robotDirection));

        if (!Double.isFinite(newX)) {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }

        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection  + angularVelocity * duration) -
                        Math.cos(robotDirection));

        if (!Double.isFinite(newY)) {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }

        robotPositionX = newX;
        robotPositionY = newY;
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;

        if (value > max)
            return max;

        return value;
    }

}
