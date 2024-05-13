package gui.model.CommandsList;

import gui.model.Cells.BacteriaCellEntity;
import gui.model.WorldContext;

public class BrainJumpCommand implements Command {
    @Override
    public Actions execute(BacteriaCellEntity cell, int commandCode, WorldContext context) {
        return Actions.BRAIN_JUMP_REACTION;
    }
}
