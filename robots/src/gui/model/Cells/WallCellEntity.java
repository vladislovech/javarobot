package gui.model.Cells;

import gui.model.WorldContext;

import java.awt.*;

public class WallCellEntity extends CellEntity {
    /**
     * Описывает стену
     */
    public WallCellEntity(Point p) {
        super(p, Color.DARK_GRAY);
    }

    @Override
    public void update(WorldContext context) {}
}
