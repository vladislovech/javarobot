package gui.model;

import java.awt.*;
import java.util.HashMap;

public class BacteriaCellEntity extends CellEntity {
    /**
     * Описывает бактерию
     */
    private final int BRAIN_SIZE = 64;
    private int[] brain;
    private int nextOperation = 0;
    private int health = 10;
    private HashMap<Integer, Point> steps = new HashMap<>();
    {
        steps.put(0, new Point(-1, -1));
        steps.put(1, new Point(0, -1));
        steps.put(2, new Point(1, -1));
        steps.put(3, new Point(1, 0));
        steps.put(4, new Point(1, 1));
        steps.put(5, new Point(0, 1));
        steps.put(6, new Point(-1, 1));
        steps.put(7, new Point(-1, 0));
    }

    public BacteriaCellEntity(Point p, int cellSize, int gridStroke) {
        super(p, Color.BLUE, cellSize, gridStroke);
        brain = new int[BRAIN_SIZE];
        fillBrain();
    }

    private void fillBrain() {
        int minValue = 0;
        int maxValue = 7; // 63
        for (int i = 0; i < BRAIN_SIZE; i++) {
            brain[i] = minValue + (int) (Math.random() * (maxValue - minValue)); // рандомное значение
        }
    }
    @Override
    public void update(WorldContext context) {
        context.updateWorld();
        switch (brain[nextOperation]) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                Point neighbour_coords = this.getNeighbourCoords(steps.get(brain[nextOperation])); //this.getNeighbourCoords?
                CellEntity neighbour = (CellEntity) context.getEntityOnCoords(neighbour_coords);
                if (neighbour == null) {
                    if (neighbour_coords.x > -1 && neighbour_coords.x < context.getCellCountWidth() &&
                            neighbour_coords.y > -1 && neighbour_coords.y < context.getCellCountHeight()) {
                        this.moveTo(steps.get(brain[nextOperation])); //this.moveTo ?
                        //context.updateWorld();
                    }
                }
                nextOperation = (nextOperation + 1) % BRAIN_SIZE;
                break;
        }
    }
}
