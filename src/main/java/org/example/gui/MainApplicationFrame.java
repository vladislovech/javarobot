package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;


import log.Logger;

import static gui.MenuBar.getLocaleString;



public class MainApplicationFrame extends JFrame
{
    static ResourceBundle rb;

    private final JDesktopPane desktopPane = new JDesktopPane();

    private String profileID;
    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);


        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = { getLocaleString("yes", Locale.getDefault()), getLocaleString("no", Locale.getDefault()) };
                int n = JOptionPane
                        .showOptionDialog(e.getWindow(), getLocaleString("closeWindow", Locale.getDefault()),
                                getLocaleString("accept", Locale.getDefault()), JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    e.getWindow().setVisible(false);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
        });

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        MenuBar menuBar = new MenuBar(this);
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        selectingProfile();
    }

    private void selectingProfile(){
        boolean flagNewProfile = false;
        while(profileID == null) {
            int response = MenuStart.whatProfile();
//            if (response == 0) {
//                profileID = MenuStart.selectingProfile();
//            } else
            if (response == 1){
                flagNewProfile = true;
                profileID = MenuStart.createProfile();
            }
            else {
                System.exit(0);
            }
        }
//        if (!flagNewProfile) {
//            setStates();
//        }
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(getLocaleString("protocolWorks", Locale.getDefault()));
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }


    public JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(MenuBar.createLookAndFeelMenu());
        menuBar.add(MenuBar.createTestMenu());
        menuBar.add(MenuBar.createLanguage());
        menuBar.add(MenuBar.createExitButton());
        return menuBar;
    }

    public void changeLocale(Locale newLocale) {
        if (!(newLocale.equals(Locale.getDefault()))){
            Locale.setDefault(newLocale);

            setJMenuBar(generateMenuBar());
            setContentPane(desktopPane);
            updateWindowsLocale();
        }
        else{
            Locale.setDefault(newLocale);
        }
    }

    private void updateWindowsLocale() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        for (JInternalFrame frame : frames) {
            if (frame instanceof inter) {
                ((inter) frame).updateWindow();
            }
        }
    }


}
