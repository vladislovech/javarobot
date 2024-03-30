package course.oop.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.vecmath.Vector2d;

import course.oop.saving.Saveable;

/**
 * Окно, выводящее текущие координаты робота
 */
public class RobotLocationWindow extends JInternalFrame implements Saveable, PropertyChangeListener {
    /** Координата x робота */
    JLabel xCoord;
    /** Координата y робота */
    JLabel yCoord;

    private DecimalFormat df = new DecimalFormat("#.##");

    public RobotLocationWindow() {
        super("Internal Frame", true, true, true, true);
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
        if (event.getPropertyName().equals("robot_update")) {
            updateCoords((Vector2d) event.getNewValue());
        }
    }

    @Override
    public String getFrameId() {
        return "robot_location";
    }

    /**
     * Обновляет текстовые поля новыми координатами робота
     */
    private void updateCoords(Vector2d vector) {
        SwingUtilities.invokeLater(() -> {
            xCoord.setText("x: " + df.format(vector.x));
            yCoord.setText("y: " + df.format(vector.y));
        });
    }
}
