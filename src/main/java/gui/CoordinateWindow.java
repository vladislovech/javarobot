package gui;

import gui.game.GameModel;
import log.Logger;
import save.Memorizable;
import save.StateManager;
import save.WindowInitException;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Window that displays robot coordinates
 */
public class CoordinateWindow extends JInternalFrame implements PropertyChangeListener, Memorizable {
    private final TextArea text = new TextArea();
    public CoordinateWindow(StateManager stateManager, GameModel model){
        super("Координаты", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(text, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        try {
            stateManager.configureFrame(getClassname(), this);
        } catch (WindowInitException e) {
            setSize(200, 400);
            Logger.debug(e.getMessage());
        }
        model.addNewListener(this);
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

    @Override
    public String getClassname() {
        return "coordinateWindow";
    }
}
