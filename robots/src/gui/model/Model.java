package gui.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final List<Entity> entities = new ArrayList<>();
    private final ModelContext context;
    public Model(RobotEntity r) {
        entities.add(r);
        context = new ModelContext(this);
    }

    public void updateModel() {
        for (Entity entity : entities) {
            entity.update(context);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Entity getEntityOnCoords(int x, int y) {
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }
        return null;
    }
}
