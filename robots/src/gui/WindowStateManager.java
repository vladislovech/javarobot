package gui;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Properties;

public class WindowStateManager {
    private static final String PROPERTIES_FILE = "windowState.properties";

    static void saveWindowStateToFile(JDesktopPane desktopPane) {
        Properties prop = new Properties();
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            saveFrameState(frame, prop);
        }

        try (FileOutputStream fos = new FileOutputStream(PROPERTIES_FILE)) {
            prop.store(fos, "Window States");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void loadWindowStateFromFile(JDesktopPane desktopPane) {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            prop.load(fis);
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                restoreFrameState(frame,prop);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static void restoreFrameState(JInternalFrame frame, Properties prop) {
        int x = Integer.parseInt(prop.getProperty(frame.getTitle() + ".x", "0"));
        int y = Integer.parseInt(prop.getProperty(frame.getTitle() + ".y", "0"));
        int width = Integer.parseInt(prop.getProperty(frame.getTitle() + ".width", "0"));
        int height = Integer.parseInt(prop.getProperty(frame.getTitle() + ".height", "0"));
        boolean isIcon = Boolean.parseBoolean(prop.getProperty(frame.getTitle() + ".isIcon", "false"));

        frame.setBounds(x, y, width, height);
        try {
            if (isIcon) {
                frame.setIcon(true);
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    private static void saveFrameState(JInternalFrame frame, Properties prop) {
        prop.setProperty(frame.getTitle() + ".x", String.valueOf(frame.getX()));
        prop.setProperty(frame.getTitle() + ".y", String.valueOf(frame.getY()));
        prop.setProperty(frame.getTitle() + ".width", String.valueOf(frame.getWidth()));
        prop.setProperty(frame.getTitle() + ".height", String.valueOf(frame.getHeight()));
        prop.setProperty(frame.getTitle() + ".isIcon", String.valueOf(frame.isIcon()));
    }
}
