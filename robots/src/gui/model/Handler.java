package gui.model;

import gui.model.Cells.BacteriaCellEntity;
import gui.model.CommandsList.*;

public class Handler {
    MoveCellCommand moveCell = new MoveCellCommand();
    CatchCellCommand catchCell = new CatchCellCommand();
    LookCommand look = new LookCommand();
    RotateCellCommand rotateCell = new RotateCellCommand();
    BrainJumpCommand brainJump = new BrainJumpCommand();

    public Handler() {}
    public Actions execute(BacteriaCellEntity cell, int command, WorldContext context) {
        Commands commandType = Commands.getType(command);
        switch (commandType) {
            case MOVE_CELL -> {
                return moveCell.execute(cell, command, context);
            }
            case CATCH_CELL -> {
                return catchCell.execute(cell, command, context);
            }
            case LOOK -> {
                return look.execute(cell, command, context);
            }
            case ROTATE -> {
                return rotateCell.execute(cell, command, context);
            }
            case BRAIN_JUMP -> {
                return brainJump.execute(cell, command, context);
            }
        }
        return null;
    }
}
