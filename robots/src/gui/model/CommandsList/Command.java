package gui.model.CommandsList;

import gui.model.Cells.BacteriaCellEntity;
import gui.model.WorldContext;

public interface Command {
    Reactions execute(BacteriaCellEntity cell, int commandCode, WorldContext context);

    record ExecutionResult(int shift, Reactions reaction) {
        
    }
}
