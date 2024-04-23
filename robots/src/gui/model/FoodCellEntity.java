package gui.model;

import java.awt.*;

public class FoodCellEntity extends CellEntity{
    /**
     * Описывает еду
     */
    public FoodCellEntity(Point p, int cellSize, int gridStroke) {
        super(p, Color.GREEN, cellSize, gridStroke);
    }

    @Override
    public void update(WorldContext context) {

    }
}
