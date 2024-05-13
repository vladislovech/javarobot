package gui.model.CommandsList;

import gui.Properties;
import gui.model.Cells.BacteriaCellEntity;

import java.awt.*;

public enum Actions {
    POISON_REACTION(1, false),
    WALL_REACTION(2, false),
    BACTERIA_REACTION(3, false),
    FOOD_REACTION(4, false),
    EMPTY_CELL_REACTION(5, false),
    FS_POISON_REACTION(1, true),
    FS_WALL_REACTION(2, true),
    FS_BACTERIA_REACTION(3, true),
    FS_FOOD_REACTION(4, true),
    FS_EMPTY_CELL_REACTION(5, true),
    ROTATE_REACTION(1, false),
    BRAIN_JUMP_REACTION(0, false);

    private final int shiftValue;
    private final boolean fullStop;
    Actions(int shiftValue, boolean flag) {
        this.shiftValue = shiftValue;
        this.fullStop = flag;
    }

    public int getShiftValue() {
        return shiftValue;
    }
    public boolean isFullStop() {
        return fullStop;
    }
}
