package gui;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class RobotCoordinatesWindow extends JInternalFrame implements Observer {
    private final JLabel coordinatesLabel;
    public RobotCoordinatesWindow(RobotModel model) {
        super("Координаты робота");
        model.addObserver(this);
        coordinatesLabel = new JLabel("X: " + model.getX() + ", Y: " + model.getY());
        this.add(coordinatesLabel);
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RobotModel) {
            RobotModel model = (RobotModel) o;
            coordinatesLabel.setText("X: " + model.getX() + ", Y: " + model.getY());
        }
    }
}
