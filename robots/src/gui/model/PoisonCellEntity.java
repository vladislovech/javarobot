package gui.model;

import java.awt.*;

public class PoisonCellEntity extends CellEntity {
    /**
     * Описывает яд
     */
    public PoisonCellEntity(Point p, int cellSize, int gridStroke) {
        super(p, Color.RED, cellSize, gridStroke);
    }

    @Override
    public void update(WorldContext context) {

    }
}
