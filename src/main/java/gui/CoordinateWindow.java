package gui;

import gui.game.GameModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        GameModel model = (GameModel) evt.getSource();
        String newLabelText = "targetPositionX = " + model.getTargetPositionX()+ "\n" +
                "targetPositionY = " + model.getTargetPositionY() + "\n" +
                "robotPositionX = " + model.getRobotPositionX() + "\n" +
                "robotPositionY = " + model.getRobotPositionY() + "\n" +
                "robotDirection = " + model.getRobotDirection();
        text.setText(newLabelText);
    }
}
