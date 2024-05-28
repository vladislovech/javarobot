package gui;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Tree {
    private double x;
    private double y;
    private int health;
    private static final int MAX_HEALTH = 100;

    public Tree(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = MAX_HEALTH;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isRobotOnTree(double robotX, double robotY) {
        double distance = Math.sqrt(Math.pow(robotX - x, 2) + Math.pow(robotY - y, 2));
        return distance < 30;
    }

    public void paint(Graphics2D g, Image treeImage, Image trunkImage) {
        Image imageToDraw = (health > 0) ? treeImage : trunkImage;
        g.drawImage(imageToDraw, (int)x, (int)y, 32, 32, null);
    }
}
