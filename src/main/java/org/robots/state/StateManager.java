package org.robots.state;

import org.robots.gui.*;

import java.beans.PropertyVetoException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StateManager implements WindowState{
    private final MainApplicationFrame mainFrame;
    private final GameWindow gameWindow;
    private final LogWindow logWindow;

    public StateManager(MainApplicationFrame mainFrame, GameWindow gameWindow, LogWindow logWindow){
        this.mainFrame = mainFrame;
        this.gameWindow = gameWindow;
        this.logWindow = logWindow;
    }

    public void saveState() {
        Map<String, String> state = new HashMap<>();

        state.put("mainFrameX", Integer.toString(mainFrame.getX()));
        state.put("mainFrameY", Integer.toString(mainFrame.getY()));
        state.put("mainFrameWidth", Integer.toString(mainFrame.getWidth()));
        state.put("mainFrameHeight", Integer.toString(mainFrame.getHeight()));

        state.put("gameWindowX", Integer.toString(gameWindow.getX()));
        state.put("gameWindowY", Integer.toString(gameWindow.getY()));
        state.put("gameWindowWidth", Integer.toString(gameWindow.getWidth()));
        state.put("gameWindowHeight", Integer.toString(gameWindow.getHeight()));
        state.put("gameWindowIsIcon", Boolean.toString(gameWindow.isIcon()));

        state.put("logWindowX", Integer.toString(logWindow.getX()));
        state.put("logWindowY", Integer.toString(logWindow.getY()));
        state.put("logWindowWidth", Integer.toString(logWindow.getWidth()));
        state.put("logWindowHeight", Integer.toString(logWindow.getHeight()));
        state.put("logWindowIsIcon", Boolean.toString(logWindow.isIcon()));

        String stateFilePath = System.getProperty("user.home") + File.separator + "state.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(stateFilePath))){
            oos.writeObject(state);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void restoreState(){
        String stateFilePath = System.getProperty("user.home") + File.separator + "state.dat";
        Map<String, String> state = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(stateFilePath))){
            state = (Map<String, String>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        if (state != null){
            mainFrame.setBounds(
                    Integer.parseInt(state.get("mainFrameX")),
                    Integer.parseInt(state.get("mainFrameY")),
                    Integer.parseInt(state.get("mainFrameWidth")),
                    Integer.parseInt(state.get("mainFrameHeight"))
            );

            gameWindow.setBounds(
                    Integer.parseInt(state.get("gameWindowX")),
                    Integer.parseInt(state.get("gameWindowY")),
                    Integer.parseInt(state.get("gameWindowWidth")),
                    Integer.parseInt(state.get("gameWindowHeight"))
            );

            logWindow.setBounds(
                    Integer.parseInt(state.get("logWindowX")),
                    Integer.parseInt(state.get("logWindowY")),
                    Integer.parseInt(state.get("logWindowWidth")),
                    Integer.parseInt(state.get("logWindowHeight"))
            );

            try{
                gameWindow.setIcon(Boolean.parseBoolean(state.get("gameWindowIsIcon")));
            }
            catch (PropertyVetoException e){}

            try{
                logWindow.setIcon(Boolean.parseBoolean(state.get("logWindowIsIcon")));
            }
            catch (PropertyVetoException e){}
        }
    }
}