package gui.game;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class.
 * Regularly updates model.
 */
public class GameController {
    private final GameModel m_model;
    private final Timer m_timer = new Timer("events generator", true);

    public GameController(GameModel model) {
        m_model = model;
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                m_model.updateRobot();
            }
        }, 0, 10);
    }

    /**
     * Method to send new point to the model
     * Allows view to affect the model
     *
     * @param point - new point
     */
    public void setTargetPosition(Point point) {
        m_model.setTargetPosition(point);
    }
}
