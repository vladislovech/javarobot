package gui.view;

import gui.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class View extends JPanel {
    private final World world;
    private final GameGrid grid;
    public View(World world) {
        this.world = world;
        this.grid = new GameGrid(world);

        setDoubleBuffered(true);
    }

    private final Map<Class<?>, EntityRenderer<?>> renderes = Map.of(
            BacteriaCellEntity.class, new BacteriaCellRenderer(),
            FoodCellEntity.class, new FoodCellRenderer(),
            PoisonCellEntity.class, new PoisonCellRenderer(),
            WallCellEntity.class, new WallCellRenderer()
    );

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        grid.drawGrid(g); // отрисовка игровой сетки
        List<Entity> entities = world.getEntities();
        for (Entity entity : entities) { // отрисовка активных клеток
            EntityRenderer<Entity> entityRenderer = (EntityRenderer<Entity>)renderes.get(entity.getClass());
            entityRenderer.render(entity, g);
        }
    }
}
