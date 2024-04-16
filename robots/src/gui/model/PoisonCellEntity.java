package gui.model;

import java.awt.*;

public class PoisonCellEntity extends CellEntity {
    /**
     * Описывает яд
     */
    public PoisonCellEntity(Point p, int cellSize) {
        super(p, Color.RED, cellSize);
    }

    @Override
    public void update(WorldContext context) {

    }
}
