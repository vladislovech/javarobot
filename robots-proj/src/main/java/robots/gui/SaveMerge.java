package robots.gui;

import java.beans.PropertyVetoException;
import java.io.IOException;
import javax.swing.JInternalFrame;
import robots.data.CashReader;
import robots.data.CashWriter;

public abstract class SaveMerge extends JInternalFrame implements IWithStates {
	public SaveMerge(String name, boolean b1, boolean b2, boolean b3, boolean b4) {
		super(name, b1, b2, b3, b4);
	}

	public WindowState getState() {
		return new WindowState(this);
	}

	public void save() {
		try {
			new CashWriter(title).writeObject(this.getState());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() throws IOException {
		try {
			WindowState ws = (WindowState)new CashReader(title).readObject();
			setLocation(ws.getLocation());
			setSize(ws.getSize());
			setSelected(ws.getSelected());
		} catch  (PropertyVetoException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
