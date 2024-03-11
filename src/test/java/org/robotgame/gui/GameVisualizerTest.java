package org.robotgame.gui;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameVisualizerTest {
    GameVisualizer visualizer = new GameVisualizer();

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
        assertEquals(new Point(1, 1), p);

        visualizer.setTargetPosition(1000, 1000);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(499, 499), p);

        visualizer.setTargetPosition(-100, 100);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(1, 100), p);

        visualizer.setTargetPosition(500, 500);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(499, 499), p);

        visualizer.setTargetPosition(100, -100);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(100, 1), p);

        visualizer.setTargetPosition(0, 0);
        p = visualizer.getTargetPoint();
        assertEquals(new Point(1, 1), p);
    }
    @Test
    public void testMoveRobot() {
        visualizer.onModelUpdateEvent();

        Point robotPositionStart = visualizer.getRobotPoint();
        Point robotPosition = visualizer.getRobotPoint();
        assertEquals(robotPositionStart, robotPosition);

        visualizer.setTargetPosition(50, 50);
        visualizer.onModelUpdateEvent();
        robotPosition = visualizer.getRobotPoint();
        assertNotEquals(robotPositionStart, robotPosition);

        visualizer.setRobotPosition(-100, -100);
        robotPosition = visualizer.getRobotPoint();
        assertEquals(new Point(1, 1), robotPosition);

        visualizer.setRobotPosition(500, 500);
        robotPosition = visualizer.getRobotPoint();
        assertEquals(new Point(499, 499), robotPosition);
    }
    @Test
    public void TestMove(){
        visualizer.setTargetPosition(600, 4);
        visualizer.setRobotPosition(400, 4);
        for (int i=0; i < 4; i++){
            visualizer.onModelUpdateEvent();
        }
        Point robotPosition = visualizer.getRobotPoint();
        assertEquals(new Point(401, 4), robotPosition);

        visualizer.setTargetPosition(200, 200);
        visualizer.setRobotPosition(230, 190);
        for (int i=0; i < 300; i++){
            visualizer.onModelUpdateEvent();
        }
        robotPosition = visualizer.getRobotPoint();
        assertEquals(new Point(200, 200), robotPosition);
    }
}