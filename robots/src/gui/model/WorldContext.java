package gui.model;

public class WorldContext {
    private final World world;

    public WorldContext(World world) {
        this.world = world;
    }
    public Entity getEntityOnCoords(int x, int y) {
        return world.getEntityOnCoords(x, y);
    }
}
