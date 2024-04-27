package gui.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {
    private final int cellCountWidth; // кол-во клеток в ширину
    private final int cellCountHeight; // кол-во клеток в высоту
    private final int gameWindowWidth; // ширина игрового поля в пикселях
    private final int gameWindowHeight; // высота игрового поля в пикселях
    private int cellSize;
    private int gridStroke;
    private CellEntity[][] matrix;
    private final List<Entity> entities = new ArrayList<>();
    private HashMap<Point, Entity> entityMap = new HashMap<>();
    private WorldContext context;
    public World(int gw_width, int gw_height, int cellCountWidth, int cellCountHeight, int gridStroke) {
        context = new WorldContext(this);

        this.gameWindowWidth = gw_width;
        this.gameWindowHeight = gw_height;
        this.cellCountWidth = cellCountWidth;
        this.cellCountHeight = cellCountHeight;
        this.gridStroke = gridStroke;

        cellSize = (gw_width - (cellCountWidth + 1) * gridStroke) / cellCountWidth;

        //this.matrix = new CellEntity[cellCountWidth][cellCountHeight];
        spawnEntities();
        fillMatrix();
    }

    public void updateWorld() {
        for (Entity entity : entities) {
            entity.update(context);
        }
    }
    public List<Entity> getEntities() {
        return entities;
    }

    public Entity getEntityOnCoords(Point p) {
        /*for (Entity entity : entities) {
            if ((entity instanceof CellEntity) && (entity.getCoords().equals(p))) {
                return entity;
            }
        }
        return null;*/
        if (entityMap.containsKey(p)) {
            return entityMap.get(p);
        }
        return null;
    }

    public void spawnEntities() {
        entities.add(new BacteriaCellEntity(new Point(0, 0), cellSize, gridStroke));
        entities.add(new BacteriaCellEntity(new Point(1, 0), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(2, 0), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(0, 2), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(1, 2), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(2, 1), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(2, 2), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(3, 1), cellSize, gridStroke));
        entities.add(new BacteriaCellEntity(new Point(3, 3), cellSize, gridStroke));
        entities.add(new BacteriaCellEntity(new Point(3, 4), cellSize, gridStroke));
        entities.add(new BacteriaCellEntity(new Point(4, 4), cellSize, gridStroke));
        entities.add(new FoodCellEntity(new Point(4, 3), cellSize, gridStroke));
        entities.add(new WallCellEntity(new Point(1, 5), cellSize, gridStroke));
        entities.add(new PoisonCellEntity(new Point(2, 6), cellSize, gridStroke));
    }
    public void fillMatrix() {
        /*matrix = new CellEntity[cellCountWidth][cellCountHeight];
        for (Entity entity: entities) {
            if (entity instanceof CellEntity) {
                Point coords = entity.getCoords();
                matrix[coords.x][coords.y] = (CellEntity)entity;
            }
        }*/
        for (Entity entity: entities) {
            if (entity instanceof CellEntity) {
                Point coords = entity.getCoords();
                entityMap.put(coords, entity);
            }
        }
    }
    public void updateEntityCoords(Point oldCoords, Point newCoords) {
        entityMap.put(newCoords, entityMap.get(oldCoords));
        entityMap.remove(oldCoords);
    }
    public int getCellCountWidth() {return cellCountWidth;}
    public int getCellCountHeight() {return cellCountHeight;}
    public int getGameWindowWidth() {return gameWindowWidth;}
    public int getGameWindowHeight() {return gameWindowHeight;}
    public int getCellSize() {return cellSize;}
    public void setCellSize(int size) {cellSize = size;}
    public int getGridStroke() {return gridStroke;}
}
