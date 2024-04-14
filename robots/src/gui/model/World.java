package gui.model;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final int width; // кол-во клеток в ширину
    private final int height; // кол-во клеток в высоту
    private int cellSize = 40;
    CellEntity[][] matrix;
    private final List<Entity> entities = new ArrayList<>();
    private final WorldContext context;
    public World(int width, int height) {
        context = new WorldContext(this);
        this.width = width;
        this.height = height;
        this.matrix = new CellEntity[width][height];
        fillMatrix();
    }

    public void updateModel() {
        for (Entity entity : entities) {
            entity.update(context);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Entity getEntityOnCoords(int x, int y) {
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }
        return null;
    }

    public void fillMatrix() {

    }
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getCellSize() {return cellSize;}
    public void setCellSize(int size) {cellSize = size;}
    public void printMatrix() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                System.out.println(matrix[w][h]);
            }
        }
    }
}
