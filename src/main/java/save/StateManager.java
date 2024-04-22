package save;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * State manager class
 * Handles saving frame states into buffer,
 * following saving/loading this states on the disk via {@link StateSaver}
 * and reconfiguring frames with previously saved states
 */
public class StateManager {
    /**
     * Map with all variables and their values
     */
    private final Map<String, String> stateMap;
    private final StateSaver saver = new StateSaver();

    public StateManager() {
        stateMap = saver.load();
    }

    /**
     * Parse component and insert values into the map
     *
     * @param classname - class name
     * @param component - map of variable names and their values
     */
    public void saveFrame(String classname, Component component) {
        Logger.debug(classname + " memorize trigger");
        Point position = component.getLocation();
        stateMap.put(classname + ".pos_x", position.x + "");
        stateMap.put(classname + ".pos_y", position.y + "");

        Dimension size = component.getSize();
        stateMap.put(classname + ".width", size.width + "");
        stateMap.put(classname + ".height", size.height + "");
        if (component instanceof JInternalFrame frame)
            stateMap.put(classname + ".icon", frame.isIcon() + "");
        Logger.debug(classname + " memorize complete");
    }

    /**
     * Extract values and configure frame with them
     *
     * @param classname - class name
     * @throws WindowInitException - exception occurred during window initialization
     */
    public void configureFrame(String classname, Component component) throws WindowInitException {
        Logger.debug(classname + " dememorization trigger");
        Map<String, String> values = new HashMap<>();
        for (String key : stateMap.keySet()) {
            if (key.startsWith(classname))
                values.put(key.substring(classname.length() + 1), stateMap.get(key));
        }
        try {
            component.setBounds(Integer.parseInt(values.get("pos_x")), Integer.parseInt(values.get("pos_y")),
                    Integer.parseInt(values.get("width")),
                    Integer.parseInt(values.get("height")));
            if (component instanceof JInternalFrame frame)
                frame.setIcon(values.get("icon").equals(true + ""));
            Logger.debug(classname + " dememorization success");
        } catch (Exception e) {
            throw new WindowInitException(classname + " dememorization failed due to exception with message:\n" + e.getMessage());
        }
    }

    public void saveState() {
        saver.save(stateMap);
    }
}
