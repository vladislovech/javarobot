package gui.model.CommandsList;

import gui.model.Cells.*;
import gui.model.Directions;
import gui.model.WorldContext;

import java.awt.*;

public class MoveCellCommand implements Command {
    @Override
    public Actions execute(BacteriaCellEntity cell, int commandCode, WorldContext context) {
        Point oldCoords = cell.getCoords();
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(commandCode);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = context.getEntityOnCoords(neighbour_coords);

        if (neighbour instanceof PoisonCellEntity) {
            context.eatPoison(cell);
            return Actions.FS_POISON_REACTION;
        }
        else if (neighbour instanceof WallCellEntity) {
            return Actions.FS_WALL_REACTION;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return Actions.FS_BACTERIA_REACTION;
        }
        else if (neighbour instanceof FoodCellEntity) {
            int healSize = context.eatFood((FoodCellEntity) neighbour);
            cell.increaseHealth(healSize);
            context.moveBacteriaToCoords(oldCoords, neighbour_coords);
            cell.moveTo(relativeCoords);
            return Actions.FS_FOOD_REACTION;
        }
        else {
            if (neighbour_coords.x > -1 && neighbour_coords.x < context.getCellCountWidth() &&
                    neighbour_coords.y > -1 && neighbour_coords.y < context.getCellCountHeight()) {
                context.moveBacteriaToCoords(oldCoords, neighbour_coords);
                cell.moveTo(relativeCoords);
                return Actions.FS_EMPTY_CELL_REACTION; // пустая клетка
            }
            else {
                return Actions.FS_WALL_REACTION; // граница мира (стена)
            }
        }
    }
}
