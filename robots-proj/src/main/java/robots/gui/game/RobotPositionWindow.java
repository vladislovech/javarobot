package robots.gui.game;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import robots.data.DataContainer;
import robots.gui.SaveMerge;

public class RobotPositionWindow extends SaveMerge implements Observer {
	private static final DataContainer DC = DataContainer.getInstance();
	private Robot robot;
	private JTextArea textField;

	public RobotPositionWindow(Robot robot) {
		super(DC.getContentNoException("robot pos/title"), true, true, true, true);
		JPanel panel = new JPanel(new BorderLayout());

		textField = new JTextArea();
		panel.add(textField, BorderLayout.CENTER);

		getContentPane().add(panel);
		pack();

		this.robot = robot;
		robot.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o.equals(robot)) {
			if (arg.equals("robot moved"))
				onRobotMoved();
		}
	}

	private void onRobotMoved() {
		textField.setText(robot.getInfo());
	}
}
