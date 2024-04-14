package gui.model;

import java.awt.*;

public class WorldContext {
    private final World world;

    public WorldContext(World world) {
        this.world = world;
    }
    public Entity getEntityOnCoords(Point p) {
        return world.getEntityOnCoords(p);
    }
}
