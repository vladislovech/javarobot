package org.robotgame.gui.buldingInteralFrame;

import org.junit.jupiter.api.Test;
import org.robotgame.GameController.GameVisualizer;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameVisualizerTest {
    GameVisualizer visualizer = new GameVisualizer(500, 500);

    public GameVisualizerTest(){
        visualizer.setSize(500, 500);
    }

    @Test
    public void testSetTargetPosition() {

        visualizer.setTargetPosition(200, 200);
        Point p = visualizer.getTargetPoint();
        assertEquals(new Point(200, 200), p);

        visualizer.setTargetPosition(-100, -100);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(0, 0), p);

        visualizer.setTargetPosition(1000, 1000);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(1000, 1000), p);

        visualizer.setTargetPosition(-100, 100);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(0, 100), p);

        visualizer.setTargetPosition(500, 500);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(500, 500), p);

        visualizer.setTargetPosition(100, -100);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(100, 0), p);

        visualizer.setTargetPosition(0, 0);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(0, 0), p);
    }
}