package course.oop.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.vecmath.Vector2d;

import course.oop.locale.UserLocaleManager;
import course.oop.model.GameModel;
import course.oop.model.GameModelEvents;
import course.oop.saving.Saveable;

/**
 * Окно, выводящее текущие координаты робота
 */
public class RobotLocationWindow extends JInternalFrame implements Saveable, PropertyChangeListener {
    /** Координата x робота */
    private JLabel xCoord;
    /** Координата y робота */
    private JLabel yCoord;

    /**
     * Формат вывода координат робота
     */
    private DecimalFormat doubleFormatter = new DecimalFormat("#.##");

    /**
     * Создает окно, подписывается на изменения переданной модели.
     */
    public RobotLocationWindow(GameModel gameModel) {
        super(UserLocaleManager.getCurrentBundle().getString("robot_location_window_name"), true, true, true, true);
        gameModel.addPropertyChangeListener(this);

        setLocation(800, 0);
        setSize(300, 100);

        xCoord = new JLabel("x");
        yCoord = new JLabel("y");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(xCoord);
        panel.add(yCoord);

        setLayout(new BorderLayout());
        add(new JLabel(UserLocaleManager.getCurrentBundle().getString("robot_location_window_name"), SwingConstants.CENTER), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(GameModelEvents.UPDATE.toString())) {
            GameModel gameModel = (GameModel) event.getNewValue();
            updateModelView(gameModel);
        }
    }

    @Override
    public String getFrameId() {
        return "robot_location";
    }

    /**
     * Обновляет текстовые поля gui координатами робота у указанной модели
     */
    private void updateModelView(GameModel gameModel) {
        Vector2d robot = gameModel.getRobot();
        SwingUtilities.invokeLater(() -> {
            xCoord.setText("x: " + doubleFormatter.format(robot.x));
            yCoord.setText("y: " + doubleFormatter.format(robot.y));
        });
    }
}
