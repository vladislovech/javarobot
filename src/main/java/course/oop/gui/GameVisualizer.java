package course.oop.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.JPanel;
import javax.vecmath.Vector2d;

import course.oop.controller.GameController;
import course.oop.model.GameModel;
import course.oop.model.GameModelParams;

public class GameVisualizer extends JPanel implements PropertyChangeListener {
    /**
     * координаты робота и цели
     */
    private int robot_x, robot_y, target_x, target_y;

    /**
     * Направление робота через угол относительно оси ox в радианах.
     */
    private double direction;

    public GameVisualizer(GameController gameController, GameModel gameModel) {
        gameModel.addPropertyChangeListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.targetUpdate(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        drawRobot(g2d, robot_x, robot_y, direction);
        drawTarget(g2d, target_x, target_y);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    /**
     * Возвращает угол между осью ox и переданным вектором.
     */
    private double atan_ox_and(Vector2d vector) {
        return Math.atan2(vector.y, vector.x);
    }

    /**
     * Обновляет поля класса на основе переданного состояния игры
     */
    private void updateViewFields(Map<GameModelParams, Vector2d> gameState) {
        Vector2d vector = gameState.get(GameModelParams.ROBOT);
        robot_x = round(vector.x);
        robot_y = round(vector.y);

        vector = gameState.get(GameModelParams.TARGET);
        target_x = round(vector.x);
        target_y = round(vector.y);

        vector = gameState.get(GameModelParams.DIRECTION);
        direction = atan_ox_and(vector);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("model_update")) {
            Map<GameModelParams, Vector2d> gameState = (Map<GameModelParams, Vector2d>) event.getNewValue();
            updateViewFields(gameState);
            onRedrawEvent();
        }
    }
}
