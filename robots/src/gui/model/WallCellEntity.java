package gui.model;

import java.awt.*;

public class WallCellEntity extends CellEntity {
    /**
     * Описывает стену
     */
    public WallCellEntity(Point p, int cellSize) {
        super(p, Color.DARK_GRAY, cellSize);
    }

    @Override
    public void update(WorldContext context) {

    }
}
