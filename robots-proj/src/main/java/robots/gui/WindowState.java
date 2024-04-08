package robots.gui;

import java.io.Serializable;
import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.awt.Point;

public class WindowState implements Serializable {
	private final Dimension SIZE;
	private final Point LOC;
	private final boolean SELECTED;
	private Serializable info;


	public WindowState(JInternalFrame window) {
		SIZE = window.getSize();
		LOC = window.getLocation();
		SELECTED = window.isSelected();
		info = null;
	}

	public Point getLocation() {
		return LOC;
	}

	public Dimension getSize() {
		return SIZE;
	}

	public boolean getSelected() {
		return SELECTED;
	}

	public Serializable getInfo() {
		return info;
	}

	public void setInfo(Serializable newInfo) {
		info = newInfo;
	}
}
