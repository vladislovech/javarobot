package gui.model.CommandsList;

import gui.model.Cells.BacteriaCellEntity;
import gui.model.WorldContext;

public class RotateCellCommand implements Command {
    @Override
    public Actions execute(BacteriaCellEntity cell, int commandCode, WorldContext context) {
        cell.rotateCell(commandCode);
        return Actions.ROTATE_REACTION;
    }
}
