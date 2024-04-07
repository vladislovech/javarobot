package org.robotgame.GameController;

import org.robotgame.GameController.GameVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Minimap extends JPanel {
    private final Timer m_timer = initTimer();
    private GameVisualizer gameVisualizer;
    private Image backgroundImage;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public Minimap(GameVisualizer gameVisualizer) {
        this.gameVisualizer = gameVisualizer;

        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/map/map.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 7);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 5);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateScreenSize();
                repaint();
            }
        });

        setDoubleBuffered(true);
        setFocusable(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void onModelUpdateEvent() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        double x = gameVisualizer.getRobot().getPositionX();
        double y = gameVisualizer.getRobot().getPositionY();

        drawRobot(g2d, x, y);

    }


    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 5, centerY - diam2 / 5, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, double x, double y){
        // Определение масштабирования координат робота
        double scale = (double) getWidth() / gameVisualizer.getCameraMap().getMapSizeX();

        // Преобразование координат робота с учетом масштабирования
        int robotCenterX = (int) (x * scale);
        int robotCenterY = (int) (y * scale);

        // Нарисовать робота на миникарте
        g.setColor(Color.GREEN);
        fillOval(g, robotCenterX, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 5, 5);
    }

    private void updateScreenSize() {
        // Добавьте обновление размера экрана здесь, если необходимо
    }
}
