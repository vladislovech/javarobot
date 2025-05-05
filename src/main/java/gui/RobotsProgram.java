package gui;

import java.awt.Frame;
import java.util.Locale;

import javax.swing.*;

public class RobotsProgram
{
    public static void main(String[] args) {
      Locale defaultLocale = new Locale("ru", "RU");
      LocalizationManager localizationManager = new LocalizationManager(defaultLocale);

      try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      } catch (Exception e) {
          e.printStackTrace();
      }
      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame(localizationManager);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
    }}
