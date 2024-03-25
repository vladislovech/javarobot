package gui.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final List<Entity> entities = new ArrayList<>();

//    public Model(TextureContainer container) {
//        entities.add(new TextureEntity(container.getTexture("tex"), new Vector2(0, 0)));
//        entities.add(new TextureEntity(container.getTexture("tex"), new Vector2(100, 100)));
//    }
    public Model(RobotEntity r) {
        entities.add(r);
    }

    public void updateModel() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
