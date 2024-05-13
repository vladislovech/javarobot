package gui.model.CommandsList;

public enum Commands {
    MOVE_CELL,
    CATCH_CELL,
    LOOK,
    ROTATE,
    BRAIN_JUMP;

    public static Commands getType(int command){
        if (command < 8) return MOVE_CELL;
        else if (command < 16) return CATCH_CELL;
        else if (command < 24) return LOOK;
        else if (command < 32) return ROTATE;
        else return BRAIN_JUMP;
    }
}
