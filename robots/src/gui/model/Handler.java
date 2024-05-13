package gui.model;

import gui.model.Cells.BacteriaCellEntity;
import gui.model.CommandsList.*;

public class Handler {
    private final MoveCellCommand moveCell = new MoveCellCommand();
    private final CatchCellCommand catchCell = new CatchCellCommand();
    private final LookCommand look = new LookCommand();
    private final RotateCellCommand rotateCell = new RotateCellCommand();
    private final BrainJumpCommand brainJump = new BrainJumpCommand();

    public Handler() {}
    public Reactions execute(BacteriaCellEntity cell, int command, WorldContext context) {
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
