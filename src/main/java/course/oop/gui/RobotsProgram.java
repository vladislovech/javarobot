package course.oop.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import course.oop.locale.UserLocaleManager;

public class RobotsProgram {
  public static void main(String[] args) {
    try {
      UIManager.put("OptionPane.yesButtonText", UserLocaleManager.getCurrentBundle().getString("option_pane_yes"));
      UIManager.put("OptionPane.noButtonText", UserLocaleManager.getCurrentBundle().getString("option_pane_no"));
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(() -> {
      MainApplicationFrame frame = new MainApplicationFrame();
      frame.setVisible(true);
    });
  }
}