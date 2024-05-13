package gui.model.CommandsList;

import gui.model.Cells.*;
import gui.model.Directions;
import gui.model.WorldContext;

import java.awt.*;

public class LookCommand implements Command {
    @Override
    public Actions execute(BacteriaCellEntity cell, int commandCode, WorldContext context) {
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(commandCode);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = context.getEntityOnCoords(neighbour_coords);

        if (neighbour instanceof PoisonCellEntity) {
            return Actions.POISON_REACTION;
        }
        else if (neighbour instanceof WallCellEntity) {
            return Actions.WALL_REACTION;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return Actions.BACTERIA_REACTION;
        }
        else if (neighbour instanceof FoodCellEntity) {
            return Actions.FOOD_REACTION;
        }
        else {
            return Actions.EMPTY_CELL_REACTION;
        }
    }
}
