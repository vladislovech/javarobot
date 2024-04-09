package gui.game;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private final GameModel m_model;
    private final Timer m_timer = new Timer("events generator", true);

    public GameController(GameModel model){
        m_model = model;
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    protected void onModelUpdateEvent()
    {
        m_model.updateRobot();
    }

    public void setTargetPosition(Point point){
        m_model.setTargetPosition(point);
    }
}
