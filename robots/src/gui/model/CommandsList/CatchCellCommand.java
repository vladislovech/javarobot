package gui.model.CommandsList;

import gui.model.Cells.*;
import gui.model.Cells.Directions;
import gui.model.WorldContext;

import java.awt.*;

public class CatchCellCommand implements Command {
    @Override
    public Reactions execute(BacteriaCellEntity cell, int commandCode, WorldContext context) {
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(commandCode);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = context.getEntityOnCoords(neighbour_coords);

        if (neighbour instanceof PoisonCellEntity) {
            context.curePoison((PoisonCellEntity) neighbour);
            return Reactions.FS_POISON_REACTION;
        }
        else if (neighbour instanceof WallCellEntity) {
            return Reactions.FS_WALL_REACTION;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return Reactions.FS_BACTERIA_REACTION;
        }
        else if (neighbour instanceof FoodCellEntity) {
            int healSize = context.eatFood((FoodCellEntity) neighbour);
            cell.increaseHealth(healSize);
            return Reactions.FS_FOOD_REACTION;
        }
        else {
            return Reactions.FS_EMPTY_CELL_REACTION;
        }
    }
}
