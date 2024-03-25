package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import log.Logger;

import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Properties;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);


        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadWindowStateFromFile();
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }
        JMenuItem exitItem =  new JMenuItem("Выход", KeyEvent.VK_E);
        exitItem.addActionListener((event) -> {
            saveWindowStateToFile();
            UIManager.put("OptionPane.yesButtonText", "Да");;
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.put("OptionPane.cancelButtonText", "Извиняюсь!");
            int result = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите выйти из приложения?", "Выход",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitItem);
        return menuBar;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
    private void saveWindowStateToFile() {
        Properties prop = new Properties();
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            prop.setProperty(frame.getTitle() + ".x", String.valueOf(frame.getX()));
            prop.setProperty(frame.getTitle() + ".y", String.valueOf(frame.getY()));
            prop.setProperty(frame.getTitle() + ".width", String.valueOf(frame.getWidth()));
            prop.setProperty(frame.getTitle() + ".height", String.valueOf(frame.getHeight()));
            prop.setProperty(frame.getTitle() + ".isIcon", String.valueOf(frame.isIcon()));
        }

        try {
            prop.store(new FileOutputStream("windowState.properties"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadWindowStateFromFile() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("windowState.properties"));
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                int x = Integer.parseInt(prop.getProperty(frame.getTitle() + ".x"));
                int y = Integer.parseInt(prop.getProperty(frame.getTitle() + ".y"));
                int width = Integer.parseInt(prop.getProperty(frame.getTitle() + ".width"));
                int height = Integer.parseInt(prop.getProperty(frame.getTitle() + ".height"));
                boolean isIcon = Boolean.parseBoolean(prop.getProperty(frame.getTitle() + ".isIcon"));

                frame.setBounds(x, y, width, height);
                if (isIcon) {
                    frame.setIcon(true);
                }
            }
        } catch (IOException | PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
}

