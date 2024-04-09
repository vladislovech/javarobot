package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Window that displays robot coordinates
 */
public class CoordinateWindow extends JInternalFrame implements PropertyChangeListener {
    private final TextArea text = new TextArea();
    public CoordinateWindow(){
        super("Координаты", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(text, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(200, 400);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameEngine source = (GameEngine) evt.getSource();
        String newLabelText = "targetPositionX = " + source.getTargetPositionX()+ "\n" +
                "targetPositionY = " + source.getTargetPositionY() + "\n" +
                "robotPositionX = " + source.getRobotPositionX() + "\n" +
                "robotPositionY = " + source.getRobotPositionY() + "\n" +
                "robotDirection = " + source.getRobotDirection();
        text.setText(newLabelText);
    }
}
