package gui;

import log.Logger;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class ThemeManager {
    private static final String THEME_PREF_KEY = "selected_theme";
    private final Preferences prefs;
    private final LocalizationManager localizationManager;

    public ThemeManager(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
        this.prefs = Preferences.userNodeForPackage(ThemeManager.class);
    }

    public void applyTheme(String themeName) {
        if (themeName == null) return;

        try {
            applyThemeSettings(themeName);
            prefs.put(THEME_PREF_KEY, themeName);
            updateAllUI();
        } catch (Exception e) {
            Logger.error("Ошибка применения темы: " + e.getMessage());
        }
    }

    public void previewTheme(String themeName, Component parent) {
        if (themeName == null || parent == null) return;

        JDialog previewDialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(parent),
                localizationManager.getString(LocalizationKeys.THEME_PREVIEW_TITLE),
                true
        );

        previewDialog.setLayout(new BorderLayout());
        JLabel message = new JLabel(localizationManager.getString(LocalizationKeys.THEME_PREVIEW_MESSAGE));
        previewDialog.add(message, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton applyButton = new JButton(localizationManager.getString(LocalizationKeys.THEME_APPLY));
        JButton cancelButton = new JButton(localizationManager.getString(LocalizationKeys.THEME_CANCEL));

        applyButton.addActionListener(e -> {
            applyTheme(themeName);
            previewDialog.dispose();
        });
        cancelButton.addActionListener(e -> previewDialog.dispose());

        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);
        previewDialog.add(buttonPanel, BorderLayout.SOUTH);

        applyThemeToDialog(themeName, previewDialog);
        previewDialog.pack();
        previewDialog.setLocationRelativeTo(parent);
        previewDialog.setVisible(true);
    }

    private void applyThemeSettings(String themeName) {
        switch (themeName) {
            case "dark":
                setDarkThemeSettings();
                break;
            case "contrast":
                setContrastThemeSettings();
                break;
            default:
                setLightThemeSettings();
        }
    }

    private void applyThemeToDialog(String themeName, JDialog dialog) {
        ThemeSettings settings = getThemeSettings(themeName);
        dialog.getContentPane().setBackground(settings.background);
        dialog.setBackground(settings.background);

        for (Component comp : dialog.getContentPane().getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(settings.foreground);
            }
        }
    }

    private ThemeSettings getThemeSettings(String themeName) {
        switch (themeName) {
            case "dark":
                return new ThemeSettings(
                        new Color(60, 63, 65),
                        new Color(187, 187, 187)
                );
            case "contrast":
                return new ThemeSettings(
                        Color.WHITE,
                        Color.BLACK
                );
            default:
                return new ThemeSettings(
                        new Color(240, 240, 240),
                        new Color(50, 50, 50)
                );
        }
    }

    private void setLightThemeSettings() {
        UIManager.put("nimbusBase", new Color(200, 200, 200));
        UIManager.put("nimbusLightBackground", new Color(240, 240, 240));
        UIManager.put("control", new Color(240, 240, 240));
        UIManager.put("text", new Color(50, 50, 50));
        UIManager.put("nimbusSelection", new Color(57, 105, 138));
        UIManager.put("nimbusSelectionBackground", new Color(57, 105, 138));
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
    }

    private void setDarkThemeSettings() {
        UIManager.put("control", new Color(60, 63, 65));
        UIManager.put("nimbusBase", new Color(43, 43, 43));
        UIManager.put("nimbusLightBackground", new Color(60, 63, 65));
        UIManager.put("text", new Color(187, 187, 187));
        UIManager.put("nimbusSelection", new Color(65, 113, 156));
        UIManager.put("nimbusSelectionBackground", new Color(65, 113, 156));
    }

    private void setContrastThemeSettings() {
        UIManager.put("control", Color.WHITE);
        UIManager.put("nimbusBase", Color.BLACK);
        UIManager.put("nimbusLightBackground", Color.WHITE);
        UIManager.put("text", Color.BLACK);
        UIManager.put("nimbusSelection", Color.CYAN);
        UIManager.put("nimbusSelectionBackground", Color.CYAN);
    }

    public String getSavedTheme() {
        return prefs.get(THEME_PREF_KEY, "light");
    }

    private void updateAllUI() {
        for (Frame frame : Frame.getFrames()) {
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    private static class ThemeSettings {
        final Color background;
        final Color foreground;

        ThemeSettings(Color background, Color foreground) {
            this.background = background;
            this.foreground = foreground;
        }
    }
}