package gui;

import javax.swing.JInternalFrame;

public interface StatefulWindow {
    String getWindowId();
    JInternalFrame getWindow();
}
