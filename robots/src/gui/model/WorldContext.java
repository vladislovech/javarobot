package gui.model;

import gui.model.Cells.*;
import java.awt.*;

public class WorldContext {
    private final World world;
    public WorldContext(World world) {
        this.world = world;
    }
    public CellEntity getEntityOnCoords(Point p) {
        return world.getEntityOnCoords(p);
    }

    public int moveCell(BacteriaCellEntity cell, int command) {
        Point oldCoords = cell.getCoords();
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(command);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = getEntityOnCoords(neighbour_coords);
        if (neighbour == null) {
            if (neighbour_coords.x > -1 && neighbour_coords.x < getCellCountWidth() &&
                    neighbour_coords.y > -1 && neighbour_coords.y < getCellCountHeight()) {
                world.moveBacteriaToCoords(oldCoords, neighbour_coords);
                cell.moveTo(relativeCoords);
                return 5; // пустая клетка
            }
            else {
                return 2; // граница мира (стена)
            }
        }
        else if (neighbour instanceof PoisonCellEntity) {
            world.eatPoison(cell);
            return 1;
        }
        else if (neighbour instanceof WallCellEntity) {
            return 2;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return 3;
        }
        else if (neighbour instanceof FoodCellEntity) {
            int healSize = world.eatFood((FoodCellEntity) neighbour);
            cell.increaseHealth(healSize);
            world.moveBacteriaToCoords(oldCoords, neighbour_coords);
            cell.moveTo(relativeCoords);
            return 4;
        }
        else return 0;
    }

    public int catchCell(BacteriaCellEntity cell, int command){
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(command);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = getEntityOnCoords(neighbour_coords);
        if (neighbour == null) {
            if (neighbour_coords.x > -1 && neighbour_coords.x < getCellCountWidth() &&
                    neighbour_coords.y > -1 && neighbour_coords.y < getCellCountHeight()) {
                return 5; // пустая клетка
            }
            else {
                return 2; // граница мира (стена)
            }
        }
        else if (neighbour instanceof PoisonCellEntity) {
            world.curePoison((PoisonCellEntity) neighbour);
            return 1;
        }
        else if (neighbour instanceof WallCellEntity) {
            return 2;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return 3;
        }
        else if (neighbour instanceof FoodCellEntity) {
            int healSize = world.eatFood((FoodCellEntity) neighbour);
            cell.increaseHealth(healSize);
            return 4;
        }
        else return 0;
    }
    public int look(BacteriaCellEntity cell, int command){
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(command);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = getEntityOnCoords(neighbour_coords);
        if (neighbour == null) {
            if (neighbour_coords.x > -1 && neighbour_coords.x < getCellCountWidth() &&
                    neighbour_coords.y > -1 && neighbour_coords.y < getCellCountHeight()) {
                return 5; // пустая клетка
            }
            else {
                return 2; // граница мира (стена)
            }
        }
        else if (neighbour instanceof PoisonCellEntity) {
            return 1;
        }
        else if (neighbour instanceof WallCellEntity) {
            return 2;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return 3;
        }
        else if (neighbour instanceof FoodCellEntity) {
            return 4;
        }
        else return 0;
    }

    public int completeCommand(BacteriaCellEntity cell, int command){
        Commands commandType = Commands.getType(command);
        System.out.println(commandType);
        Point oldCoords = cell.getCoords();
        Directions cellDirection = cell.getCellDirection();
        Point relativeCoords = cellDirection.getRelativeCoords(command);
        Point neighbour_coords = cell.getNeighbourCoords(relativeCoords);
        CellEntity neighbour = getEntityOnCoords(neighbour_coords);
        if (neighbour == null) {
            if (neighbour_coords.x > -1 && neighbour_coords.x < getCellCountWidth() &&
                    neighbour_coords.y > -1 && neighbour_coords.y < getCellCountHeight()) {
                world.moveBacteriaToCoords(oldCoords, neighbour_coords);
                cell.moveTo(relativeCoords);
                return 5; // пустая клетка
            }
            else {
                return 2; // граница мира (стена)
            }
        }
        else if (neighbour instanceof PoisonCellEntity) {
            world.eatPoison(cell);
            return 1;
        }
        else if (neighbour instanceof WallCellEntity) {
            return 2;
        }
        else if (neighbour instanceof BacteriaCellEntity) {
            return 3;
        }
        else if (neighbour instanceof FoodCellEntity) {
            int healSize = world.eatFood((FoodCellEntity) neighbour);
            cell.increaseHealth(healSize);
            world.moveBacteriaToCoords(oldCoords, neighbour_coords);
            cell.moveTo(relativeCoords);
            return 4;
        }
        else return 0;
    }
    public void killCell(CellEntity cell) {
        world.killCell(cell);
    }
    public int getCellCountWidth() {return world.getCellCountWidth();}
    public int getCellCountHeight() {return world.getCellCountHeight();}
}
