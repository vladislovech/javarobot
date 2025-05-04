package gui;

import java.awt.Frame;
import java.util.Locale;

import javax.swing.SwingUtilities;

public class RobotsProgram
{
    public static void main(String[] args) {
      Locale defaultLocale = new Locale("ru", "RU");
      LocalizationManager localizationManager = new LocalizationManager(defaultLocale);

      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame(localizationManager);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
    }}
