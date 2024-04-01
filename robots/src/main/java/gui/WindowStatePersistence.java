package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class WindowStatePersistence {
    private static final String PREF_KEY_WINDOW_TYPE = "windowType_";
    private static final String PREF_NODE = "MyAppPrefs";
    private static final String PREF_KEY_WINDOW_COUNT = "windowCount";
    private static final String PREF_KEY_WINDOW_X = "windowX_";
    private static final String PREF_KEY_WINDOW_Y = "windowY_";
    private static final String PREF_KEY_WINDOW_WIDTH = "windowWidth_";
    private static final String PREF_KEY_WINDOW_HEIGHT = "windowHeight_";

    public static void saveWindowState(JDesktopPane desktopPane) {
        Preferences prefs = Preferences.userRoot().node(PREF_NODE);
        prefs.putInt(PREF_KEY_WINDOW_COUNT, desktopPane.getAllFrames().length);
        for (int i = 0; i < desktopPane.getAllFrames().length; i++) {
            JInternalFrame frame = desktopPane.getAllFrames()[i];
            prefs.putInt(PREF_KEY_WINDOW_X + i, frame.getX());
            prefs.putInt(PREF_KEY_WINDOW_Y + i, frame.getY());
            prefs.putInt(PREF_KEY_WINDOW_WIDTH + i, frame.getWidth());
            prefs.putInt(PREF_KEY_WINDOW_HEIGHT + i, frame.getHeight());
            if (frame instanceof GameWindow) {
                prefs.put(PREF_KEY_WINDOW_TYPE + i, "GameWindow");
            } else if (frame instanceof LogWindow) {
                prefs.put(PREF_KEY_WINDOW_TYPE + i, "LogWindow");
            }
        }
    }

    public static void restoreWindowState(JDesktopPane desktopPane) {
        Preferences prefs = Preferences.userRoot().node(PREF_NODE);
        int windowCount = prefs.getInt(PREF_KEY_WINDOW_COUNT, 0);
        for (int i = 0; i < windowCount; i++) {
            int x = prefs.getInt(PREF_KEY_WINDOW_X + i, 0);
            int y = prefs.getInt(PREF_KEY_WINDOW_Y + i, 0);
            int width = prefs.getInt(PREF_KEY_WINDOW_WIDTH + i, 0);
            int height = prefs.getInt(PREF_KEY_WINDOW_HEIGHT + i, 0);
            String windowType = prefs.get(PREF_KEY_WINDOW_TYPE + i, "");
            JInternalFrame frame = null;
            if ("GameWindow".equals(windowType)) {
                frame = new GameWindow();
            } else if ("LogWindow".equals(windowType)) {
                frame = new LogWindow(Logger.getDefaultLogSource());
            }
            if (frame != null) {
                frame.setBounds(x, y, width, height);
                desktopPane.add(frame);
                frame.setVisible(true);
            }
        }
    }
}
