package gui.model;

import gui.model.Cells.*;
import gui.model.CommandsList.Commands;

import java.awt.*;

public class WorldContext {
    private final World world;
    public WorldContext(World world) {
        this.world = world;
    }

    /**
     * Возвращает сущность, находящуюся на указанных координатах или null, если там пусто
     * @param p
     * @return CellEntity или null
     */
    public CellEntity getEntityOnCoords(Point p) {
        return world.getEntityOnCoords(p);
    }

    public void moveBacteriaToCoords(Point oldCoords, Point neighbour_coords) {world.moveBacteriaToCoords(oldCoords, neighbour_coords);}
    public void eatPoison(BacteriaCellEntity cell) {world.eatPoison(cell);}
    public void curePoison(PoisonCellEntity poison) {world.curePoison(poison);}
    public int eatFood(FoodCellEntity food) {return world.eatFood(food);}
    public void killCell(CellEntity cell) {
        world.killCell(cell);
    }
    public int getCellCountWidth() {return world.getCellCountWidth();}
    public int getCellCountHeight() {return world.getCellCountHeight();}
}
