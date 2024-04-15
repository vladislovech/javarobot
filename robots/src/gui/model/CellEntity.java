package gui.model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CellEntity implements Entity {
    /**
     * Описывает шаблонную клетку
     */
    private Color color;
    private Point coords;
    private int cellSize;
    public CellEntity(Point p, Color c, int cs) {
        this.coords = p;
        this.color = c;
        this.cellSize = cs;
    }

    @Override
    public Point getCoords() {
        return coords;
    }
    public Color getColor() {return color;}
    public int getCellSize() {return cellSize;}
}
