package gui.model;

import java.awt.*;
import java.util.HashMap;

public class WorldContext {
    private final World world;
    public WorldContext(World world) {
        this.world = world;
    }
    public Entity getEntityOnCoords(Point p) {
        return world.getEntityOnCoords(p);
    }
    private HashMap<Integer, Point> steps = new HashMap<>();
    {
        steps.put(0, new Point(-1, -1));
        steps.put(1, new Point(0, -1));
        steps.put(2, new Point(1, -1));
        steps.put(3, new Point(1, 0));
        steps.put(4, new Point(1, 1));
        steps.put(5, new Point(0, 1));
        steps.put(6, new Point(-1, 1));
        steps.put(7, new Point(-1, 0));
    }
    public void moveCell(CellEntity c, int nextOperation) {
        Point oldCoords = c.getCoords();
        Point neighbour_coords = c.getNeighbourCoords(steps.get(nextOperation));
        CellEntity neighbour = (CellEntity) getEntityOnCoords(neighbour_coords);
        if (neighbour == null) {
            if (neighbour_coords.x > -1 && neighbour_coords.x < getCellCountWidth() &&
                    neighbour_coords.y > -1 && neighbour_coords.y < getCellCountHeight()) {
                world.updateEntityCoords(oldCoords, neighbour_coords);
                c.moveTo(steps.get(nextOperation));
            }
        }
    }
    public int getCellCountWidth() {return world.getCellCountWidth();}
    public int getCellCountHeight() {return world.getCellCountHeight();}
}
