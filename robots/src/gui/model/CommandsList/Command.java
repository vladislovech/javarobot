package gui.model.CommandsList;

import gui.model.Cells.BacteriaCellEntity;
import gui.model.WorldContext;

public interface Command {
    public Actions execute(BacteriaCellEntity cell, int commandCode, WorldContext context);
}
