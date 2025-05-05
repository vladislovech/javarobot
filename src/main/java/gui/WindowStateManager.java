package gui;

import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import java.io.*;
import java.util.Properties;

public class WindowStateManager {
    private final Properties properties;
    private final File configFile;

    public WindowStateManager() {
        properties = new Properties();
        String userHome = System.getProperty("user.home");
        configFile = new File(userHome, ".robots_program_config");
        loadConfig();
    }

    private void loadConfig() {
        if (configFile.exists()) {
            try (InputStream input = new FileInputStream(configFile)) {
                properties.load(input);
            } catch (IOException e) {
                System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
            }
        }
    }

    private void saveConfig() {
        try (OutputStream output = new FileOutputStream(configFile)) {
            properties.store(output, "Robots Program Window States");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения конфигурации: " + e.getMessage());
        }
    }

    public void saveWindowState(String windowId, JInternalFrame window) {
        Rectangle bounds = window.getBounds();
        boolean isIcon = window.isIcon();
        boolean isMaximum = window.isMaximum();

        properties.setProperty(windowId + ".x", Integer.toString(bounds.x));
        properties.setProperty(windowId + ".y", Integer.toString(bounds.y));
        properties.setProperty(windowId + ".width", Integer.toString(bounds.width));
        properties.setProperty(windowId + ".height", Integer.toString(bounds.height));
        properties.setProperty(windowId + ".icon", Boolean.toString(isIcon));
        properties.setProperty(windowId + ".maximized", Boolean.toString(isMaximum));

        saveConfig();
    }

    public WindowState loadWindowState(String windowId) {
        String x = properties.getProperty(windowId + ".x");
        String y = properties.getProperty(windowId + ".y");
        String width = properties.getProperty(windowId + ".width");
        String height = properties.getProperty(windowId + ".height");
        String isIcon = properties.getProperty(windowId + ".icon");
        String isMaximized = properties.getProperty(windowId + ".maximized");

        if (x == null || y == null || width == null || height == null) {
            return null;
        }

        try {
            Rectangle bounds = new Rectangle(
                    Integer.parseInt(x),
                    Integer.parseInt(y),
                    Integer.parseInt(width),
                    Integer.parseInt(height)
            );
            return new WindowState(
                    bounds,
                    Boolean.parseBoolean(isIcon),
                    Boolean.parseBoolean(isMaximized)
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static class WindowState {
        private final Rectangle bounds;
        private final boolean isIcon;
        private final boolean isMaximized;

        public WindowState(Rectangle bounds, boolean isIcon, boolean isMaximized) {
            this.bounds = bounds;
            this.isIcon = isIcon;
            this.isMaximized = isMaximized;
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public boolean isIcon() {
            return isIcon;
        }

        public boolean isMaximized() {
            return isMaximized;
        }
    }
}