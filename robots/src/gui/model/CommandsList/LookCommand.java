package gui.model.CommandsList;

import gui.model.Cells.*;
import gui.model.Cells.Directions;
import gui.model.WorldContext;

import java.awt.*;

public class LookCommand implements Command {
    @Override
    public Reactions execute(BacteriaCellEntity cell, int commandCode, WorldContext context) {
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(commandCode);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = context.getEntityOnCoords(neighbour_coords);

        if (neighbour instanceof PoisonCellEntity) {
            return Reactions.POISON_REACTION;
        }
        else if (neighbour instanceof WallCellEntity) {
            return Reactions.WALL_REACTION;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return Reactions.BACTERIA_REACTION;
        }
        else if (neighbour instanceof FoodCellEntity) {
            return Reactions.FOOD_REACTION;
        }
        else {
            return Reactions.EMPTY_CELL_REACTION;
        }
    }
}
