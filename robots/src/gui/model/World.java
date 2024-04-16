package gui.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final int cellCountWidth; // кол-во клеток в ширину
    private final int cellCountHeight; // кол-во клеток в высоту
    private final int gameWindowWidth; // ширина игрового поля в пикселях
    private final int gameWindowHeight; // высота игрового поля в пикселях
    private int cellSize;
    CellEntity[][] matrix;
    private final List<Entity> entities = new ArrayList<>();
    private WorldContext context;
    public World(int cellCountWidth, int cellCountHeight, int gw_width, int gw_height) {
        context = new WorldContext(this);

        this.cellCountWidth = cellCountWidth;
        this.cellCountHeight = cellCountHeight;
        this.gameWindowWidth = gw_width;
        this.gameWindowHeight = gw_height;
        cellSize = gw_width / cellCountWidth - 2;

        this.matrix = new CellEntity[cellCountWidth][cellCountHeight];
        spawnEntities();
        fillMatrix();
    }

    public void updateModel() {
        for (Entity entity : entities) {
            entity.update(context);
        }
    }
    public void updateContext() {
        context = new WorldContext(this);
    }
    public List<Entity> getEntities() {
        return entities;
    }

    public Entity getEntityOnCoords(Point p) {
        for (Entity entity : entities) {
            if (entity instanceof CellEntity && entity.getCoords() == p) {
                return entity;
            }
        }
        return null;
    }

    public void spawnEntities() {
        entities.add(new BacteriaCellEntity(new Point(0, 0), cellSize));
        entities.add(new BacteriaCellEntity(new Point(1, 0), cellSize));
        entities.add(new WallCellEntity(new Point(2, 0), cellSize));
        entities.add(new WallCellEntity(new Point(0, 1), cellSize));
        entities.add(new WallCellEntity(new Point(1, 1), cellSize));
        entities.add(new WallCellEntity(new Point(2, 1), cellSize));
        entities.add(new BacteriaCellEntity(new Point(3, 3), cellSize));
        entities.add(new FoodCellEntity(new Point(4, 3), cellSize));
        entities.add(new WallCellEntity(new Point(1, 5), cellSize));
        entities.add(new PoisonCellEntity(new Point(2, 6), cellSize));
    }
    public void fillMatrix() {
        matrix = new CellEntity[cellCountWidth][cellCountHeight];
        for (Entity entity: entities) {
            if (entity instanceof CellEntity) {
                Point coords = entity.getCoords();
                matrix[coords.x][coords.y] = (CellEntity)entity;
            }
        }
    }
    public int getCellCountWidth() {return cellCountWidth;}
    public int getCellCountHeight() {return cellCountHeight;}
    public int getWidth() {return gameWindowWidth;}
    public int getHeight() {return gameWindowHeight;}
    public int getCellSize() {return cellSize;}
    public void setCellSize(int size) {cellSize = size;}
}
