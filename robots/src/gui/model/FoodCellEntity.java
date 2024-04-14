package gui.model;

import java.awt.*;

public class FoodCellEntity extends CellEntity{
    /**
     * Описывает еду
     */
    public FoodCellEntity(Point p) {
        super(p, Color.ORANGE);
    }

    @Override
    public void update(WorldContext context) {

    }
}
