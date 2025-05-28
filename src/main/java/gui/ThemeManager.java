package gui;

import log.Logger;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class ThemeManager {
    private static final String THEME_PREF_KEY = "selected_theme";
    private final Preferences prefs;

    public ThemeManager() {
        this.prefs = Preferences.userNodeForPackage(ThemeManager.class);
    }

    public enum Theme {
        LIGHT("light", "Темная"),
        DARK("dark", "Светлая"),
        CONTRAST("contrast", "Контрастная");

        private final String id;
        private final String displayName;

        Theme(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public void applyTheme(Theme theme) {
        if (theme == null) return;

        try {
            applyThemeSettings(theme);
            prefs.put(THEME_PREF_KEY, theme.getId());
            updateAllUI();
        } catch (Exception e) {
            Logger.error("Ошибка применения темы: " + e.getMessage());
        }
    }

    public void previewTheme(Theme theme, Component parent) {
        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(parent),
                "Просмотр темы: " + theme.getDisplayName(),
                true
        );

        // Настройки цветов
        ThemeColors colors = getThemeColors(theme);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(colors.background);

        addDemoComponent(new JLabel("Пример текста"), panel, colors);

        JButton apply = createActionButton("Применить", colors, e -> {
            applyTheme(theme);
            dialog.dispose();
        });

        JButton cancel = createActionButton("Отмена", colors, e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(apply);
        buttonPanel.add(cancel);
        buttonPanel.setBackground(colors.background);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        applyThemeToDialog(dialog, colors);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private void applyThemeSettings(Theme theme) {
        ThemeColors colors = getThemeColors(theme);

        UIManager.put("nimbusBase", colors.base);
        UIManager.put("control", colors.control);
        UIManager.put("text", colors.foreground);
        UIManager.put("nimbusSelection", colors.selection);
        UIManager.put("nimbusSelectionBackground", colors.selection);
        UIManager.put("nimbusLightBackground", colors.background);
    }

    private void applyThemeToDialog(JDialog dialog, ThemeColors colors) {
        dialog.getContentPane().setBackground(colors.background);
        for (Component comp : dialog.getContentPane().getComponents()) {
            comp.setBackground(colors.background);
            if (comp instanceof JComponent) {
                ((JComponent) comp).setForeground(colors.foreground);
            }
        }
    }

    private ThemeColors getThemeColors(Theme theme) {
        switch (theme) {
            case DARK:
                return new ThemeColors(
                        new Color(60, 63, 65),  // background
                        new Color(187, 187, 187), // foreground
                        new Color(43, 43, 43),    // base
                        new Color(60, 63, 65),    // control
                        new Color(65, 113, 156)   // selection
                );
            case CONTRAST:
                return new ThemeColors(
                        Color.BLACK,
                        Color.YELLOW,
                        Color.BLACK,
                        Color.BLACK,
                        Color.RED
                );
            default:
                return new ThemeColors(
                        new Color(240, 240, 240),
                        Color.BLACK,
                        new Color(200, 200, 200),
                        new Color(240, 240, 240),
                        new Color(57, 105, 138)
                );
        }
    }

    private String getThemeDisplayName(String themeName) {
        switch (themeName) {
            case "dark": return "Темная";
            case "contrast": return "Контрастная";
            default: return "Светлая";
        }
    }

    private void addDemoComponent(JComponent comp, JPanel panel, ThemeColors colors) {
        comp.setForeground(colors.foreground);
        comp.setBackground(colors.control);
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (comp instanceof AbstractButton) {
            ((AbstractButton) comp).setContentAreaFilled(false);
            ((AbstractButton) comp).setOpaque(true);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(comp);
    }

    private JButton createActionButton(String text, ThemeColors colors, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setForeground(colors.foreground);
        button.setBackground(colors.selection);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addActionListener(action);
        return button;
    }

    public Theme getSavedTheme() {
        String themeId = prefs.get(THEME_PREF_KEY, Theme.LIGHT.getId());
        return Theme.valueOf(themeId.toUpperCase());
    }

    private void updateAllUI() {
        for (Frame frame : Frame.getFrames()) {
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    private static class ThemeColors {
        final Color background;
        final Color foreground;
        final Color base;
        final Color control;
        final Color selection;

        ThemeColors(Color background, Color foreground, Color base, Color control, Color selection) {
            this.background = background;
            this.foreground = foreground;
            this.base = base;
            this.control = control;
            this.selection = selection;
        }
    }
}