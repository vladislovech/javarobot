package gui.model.Cells;

import gui.model.WorldContext;

import java.awt.*;

public class PoisonCellEntity extends CellEntity {
    /**
     * Описывает яд
     */
    public PoisonCellEntity(Point p) {
        super(p, Color.RED);
    }

    @Override
    public void update(WorldContext context) {}
}
