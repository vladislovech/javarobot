package course.oop.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.vecmath.Vector2d;

import course.oop.model.Game;

public class GameVisualizer extends JPanel implements PropertyChangeListener {
    private final Timer m_timer;
    /**
     * Модель игры
     */
    private Game game;

    private Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer(Game game) {
        m_timer = initTimer();
        this.game = game;
        game.addPropertyChangeListener(this);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point p) {
        game.setTarget(new Vector2d(p.x, p.y));
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void onModelUpdateEvent() {
        game.nextState(10);
    }

    private int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        drawRobot(g2d, round(game.getRobot().x), round(game.getRobot().y), atan_ox_and(game.getDirection()));
        drawTarget(g2d, round(game.getTarget().x), round(game.getTarget().y));
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(game.getRobot().x);
        int robotCenterY = round(game.getRobot().y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        Vector2d target = game.getTarget();
        int new_x = round(target.x);
        int new_y = round(target.y);
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, new_x, new_y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, new_x, new_y, 5, 5);
    }

    /**
     * Возвращает угол между осью ox и переданным вектором.
     */
    private double atan_ox_and(Vector2d vector) {
        return Math.atan2(vector.y, vector.x);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("robot_update")) {
            onRedrawEvent();       
        }
    }
}
