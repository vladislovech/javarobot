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
        try {
            // Сохраняем текущий Look and Feel
            LookAndFeel currentLAF = UIManager.getLookAndFeel();

            // Применяем настройки темы
            applyThemeSettings(themeName);

            // Восстанавливаем Look and Feel
            UIManager.setLookAndFeel(currentLAF);

            prefs.put(THEME_PREF_KEY, themeName);
            updateAllUI();
        } catch (Exception e) {
            Logger.error("Ошибка применения темы: " + e.getMessage());
        }
    }

    public void previewTheme(String themeName, Component parent) {
        JDialog previewDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                localizationManager.getString(LocalizationKeys.THEME_PREVIEW_TITLE), true);
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

        // Применяем выбранную тему только к диалогу предпросмотра
        switch (themeName) {
            case "dark":
                applyDarkThemeToDialog(previewDialog);
                break;
            case "contrast":
                applyContrastThemeToDialog(previewDialog);
                break;
            default:
                applyLightThemeToDialog(previewDialog);
        }

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

    public String getSavedTheme() {
        return prefs.get(THEME_PREF_KEY, "light");
    }

    private void updateAllUI() {
        for (Frame frame : Frame.getFrames()) {
            SwingUtilities.updateComponentTreeUI(frame);
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
        Color darkColor = new Color(60, 63, 65);
        Color darkerColor = new Color(43, 43, 43);
        Color textColor = new Color(187, 187, 187);

        UIManager.put("control", darkColor);
        UIManager.put("nimbusBase", darkerColor);
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(150, 150, 150));
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
        UIManager.put("nimbusLightBackground", new Color(60, 63, 65));
        UIManager.put("nimbusOrange", new Color(191, 98, 4));
        UIManager.put("nimbusRed", new Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", Color.WHITE);
        UIManager.put("nimbusSelection", new Color(65, 113, 156));
        UIManager.put("nimbusSelectionBackground", new Color(65, 113, 156));
        UIManager.put("text", textColor);
    }

    private void setContrastThemeSettings() {
        UIManager.put("control", Color.WHITE);
        UIManager.put("nimbusBase", Color.BLACK);
        UIManager.put("nimbusAlertYellow", Color.YELLOW);
        UIManager.put("nimbusDisabledText", Color.GRAY);
        UIManager.put("nimbusFocus", Color.BLUE);
        UIManager.put("nimbusLightBackground", Color.WHITE);
        UIManager.put("nimbusOrange", Color.ORANGE);
        UIManager.put("nimbusRed", Color.RED);
        UIManager.put("nimbusSelectedText", Color.BLACK);
        UIManager.put("nimbusSelection", Color.CYAN);
        UIManager.put("nimbusSelectionBackground", Color.CYAN);
        UIManager.put("text", Color.BLACK);
    }

    private void applyLightThemeToDialog(JDialog dialog) {
        dialog.getContentPane().setBackground(new Color(240, 240, 240));
        dialog.setBackground(new Color(240, 240, 240));
    }

    private void applyDarkThemeToDialog(JDialog dialog) {
        dialog.getContentPane().setBackground(new Color(60, 63, 65));
        dialog.setBackground(new Color(60, 63, 65));
        for (Component comp : dialog.getContentPane().getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(new Color(187, 187, 187));
            }
        }
    }

    private void applyContrastThemeToDialog(JDialog dialog) {
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setBackground(Color.WHITE);
        for (Component comp : dialog.getContentPane().getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.BLACK);
            }
        }
    }

    public void reapplyCurrentTheme() {
        applyTheme(getSavedTheme());
    }
}