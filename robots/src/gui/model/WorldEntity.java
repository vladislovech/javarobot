package gui.model;

public class WorldEntity implements Entity {
    private final int width; // кол-во клеток
    private final int height;
    CellEntity[][] matrix;
    int cellSize = 40;
    public WorldEntity(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new CellEntity[width][height];
        fillMatrix();
    }
    public int getWidth() {return width;};
    public int getHeight() {return height;};
    public void fillMatrix() {
        matrix[0][3] = new CellEntity();
        matrix[0][5] = new CellEntity();
        matrix[1][5] = new CellEntity();
        matrix[1][6] = new CellEntity();
        matrix[3][0] = new CellEntity();
        matrix[3][4] = new CellEntity();
        matrix[5][7] = new CellEntity();
        matrix[6][6] = new CellEntity();
    }
    public void printMatrix() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                System.out.println(matrix[w][h]);
            }
        }
    }

    @Override
    public void update() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                matrix[w][h].update();
            }
        }
    }
}
