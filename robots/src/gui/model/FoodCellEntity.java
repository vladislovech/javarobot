package gui.model;

import java.awt.*;

public class FoodCellEntity extends CellEntity{
    /**
     * Описывает еду
     */
    public FoodCellEntity(Point p, int cellSize) {
        super(p, Color.ORANGE, cellSize);
    }

    @Override
    public void update(WorldContext context) {

    }
}
