package gui.model;

import java.awt.*;

public class BacteriaCellEntity extends CellEntity {
    /**
     * Описывает бактерию
     */
    private final int BRAIN_SIZE = 64;
    int[] brain;
    public BacteriaCellEntity(Point p) {
        super(p, Color.BLUE);
        brain = new int[BRAIN_SIZE];
        fillBrain();
    }

    @Override
    public void update(WorldContext context) {

    }
    private void fillBrain() {
        for (int i = 0; i < BRAIN_SIZE; i++) {
            brain[i] = 2;
        }
    }
}
