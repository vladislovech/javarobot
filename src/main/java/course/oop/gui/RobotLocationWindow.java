package course.oop.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.vecmath.Vector2d;

import course.oop.model.GameModel;
import course.oop.model.GameModelParams;
import course.oop.saving.Saveable;

/**
 * Окно, выводящее текущие координаты робота
 */
public class RobotLocationWindow extends JInternalFrame implements Saveable, PropertyChangeListener {
    /** Координата x робота */
    JLabel xCoord;
    /** Координата y робота */
    JLabel yCoord;

    /**
     * Формат вывода координат робота
     */
    private DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Создает окно, подписывается на изменения переданной модели.
     */
    public RobotLocationWindow(GameModel gameModel) {
        super("Internal Frame", true, true, true, true);
        gameModel.addPropertyChangeListener(this);
        setSize(300, 200);
        xCoord = new JLabel("x");
        yCoord = new JLabel("y");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(xCoord);
        panel.add(yCoord);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("model_update")) {
            Map<GameModelParams,Vector2d> gameState = (Map<GameModelParams, Vector2d>) event.getNewValue();
            updateCoords(gameState.get(GameModelParams.ROBOT));
        }
    }

    @Override
    public String getFrameId() {
        return "robot_location";
    }

    /**
     * Обновляет текстовые поля gui новыми координатами робота
     */
    private void updateCoords(Vector2d robot) {
        SwingUtilities.invokeLater(() -> {
            xCoord.setText("x: " + df.format(robot.x));
            yCoord.setText("y: " + df.format(robot.y));
        });
    }
}
