package gui.model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CellEntity implements Entity {
    /**
     * Описывает универсальную клетку
     */
    private Point coords;
    public CellEntity(Point p) {
        this.coords = p;
    }

    @Override
    public Point getCoords() {
        return coords;
    }
}
