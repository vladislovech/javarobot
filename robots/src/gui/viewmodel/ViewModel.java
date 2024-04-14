package gui.viewmodel;

import gui.model.*;
import gui.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ViewModel extends JPanel {
    private final World world;
    private final GameGrid grid;
    private final int gameWindowWidth;
    private final int gameWindowHeight;
    private Map<Class<?>, EntityRenderer<?>> renderes = Map.of(
            BacteriaCellEntity.class, new BacteriaCellRenderer(),
            FoodCellEntity.class, new FoodCellRenderer()
    );
    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    private final Timer m_timer = initTimer();

    public ViewModel(int gw_width, int gw_height) {
        this.gameWindowWidth = gw_width;
        this.gameWindowHeight = gw_height;

        world = new World(8, 8, gameWindowWidth, gameWindowHeight);
        grid = new GameGrid(world);

        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                updateLogic();
                onRedrawEvent();
            }
        }, 0, 100);

        setDoubleBuffered(true);
    }
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    public void updateLogic() {
        world.updateModel();
    }

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
