package gui.model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CellEntity implements Entity {
    /**
     * Описывает шаблонную клетку
     */
    private Color color;
    private Point coords;
    public CellEntity(Point p, Color c) {
        this.coords = p;
        this.color = c;
    }

    @Override
    public Point getCoords() {
        return coords;
    }
    public Color getColor() {return color;}
}
