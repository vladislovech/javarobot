package save;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * State manager class
 * Handles saving frame states into buffer,
 * following saving/loading this states on the disk
 * and reconfiguring frames with previously saved states
 */
public class StateManager {
    /**
     * Map with all variables and their values
     */
    private final Map<String, String> stateMap = new HashMap<>();
    /**
     * Path to the config file
     */
    private final String filename = System.getProperty("user.home") + "/RobotData.txt";
    public StateManager(){
        load();
    }

    /**
     * Parse component and insert values into the map
     *
     * @param attribute - class name
     * @param component - map of variable names and their values
     */
    public void saveFrame(String attribute, Component component) {
        Logger.debug(attribute + " memorize trigger");
        Point position = component.getLocation();
        stateMap.put(attribute + ".pos_x", position.x + "");
        stateMap.put(attribute + ".pos_y", position.y + "");

        Dimension size = component.getSize();
        stateMap.put(attribute + ".width", size.width + "");
        stateMap.put(attribute + ".height", size.height + "");
        if (component instanceof JInternalFrame frame)
            stateMap.put(attribute + ".icon", frame.isIcon() + "");
        Logger.debug(attribute + " memorize complete");
    }

    /**
     * Extract values and configure frame with them
     *
     * @param attribute - class name
     * @throws WindowInitException - exception occurred during window initialization
     */
    public void configureFrame(String attribute, Component component) throws WindowInitException {
        Logger.debug(attribute + " dememorization trigger");
        Map<String, String> values = new HashMap<>();
        for (String key : stateMap.keySet()) {
            if (key.startsWith(attribute))
                values.put(key.substring(attribute.length() + 1), stateMap.get(key));
        }
        try {
            component.setBounds(Integer.parseInt(values.get("pos_x")), Integer.parseInt(values.get("pos_y")),
                    Integer.parseInt(values.get("width")),
                    Integer.parseInt(values.get("height")));
            if (component instanceof JInternalFrame frame)
                frame.setIcon(values.get("icon").equals(true + ""));
            Logger.debug(attribute + " dememorization success");
        } catch (Exception e) {
            throw new WindowInitException(attribute + " dememorization failed due to exception with message:\n" + e.getMessage());
        }
    }

    /**
     * Store saved data locally
     */
    public void save() {
        Logger.debug("Store trigger");
        try {
            Path file = Path.of(filename);
            List<String> data = new ArrayList<>();
            for (String key : stateMap.keySet()) {
                data.add(key + "=" + stateMap.get(key));
            }
            Files.write(file, data);
        } catch (IOException e) {
            Logger.debug("Failed to store data due to IO exception with message: \n" + e.getMessage());
        }
    }

    /**
     * Restore locally saved data
     */
    public void load() {
        Logger.debug("Restore trigger");
        try {
            List<String> data = Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8);
            String[] parsedLine;
            for (String line : data) {
                parsedLine = line.split("=");
                stateMap.put(parsedLine[0], parsedLine[1]);
            }
        } catch (IOException e) {
            Logger.debug("Failed to restore data due to IO exception with message: \n" + e.getMessage());
        }
    }
}
