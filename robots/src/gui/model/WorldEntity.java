package gui.model;

public class WorldEntity implements Entity {
    private final int width; // кол-во клеток
    private final int height;
    private int cellSize = 40;
    CellEntity[][] matrix;
    public WorldEntity(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new CellEntity[width][height];
        fillMatrix();
    }
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getCellSize() {return cellSize;}
    public void setCellSize(int size) {cellSize = size;}
    public CellEntity[][] getMatrix() {return matrix;}
    public void fillMatrix() {
        matrix[0][3] = new BacteriaCellEntity();
        matrix[0][5] = new FoodCellEntity();
        matrix[1][5] = new EmptyCellEntity();
        matrix[1][6] = new BacteriaCellEntity();
        matrix[3][0] = new EmptyCellEntity();
        matrix[3][4] = new EmptyCellEntity();
        matrix[5][7] = new BacteriaCellEntity();
        matrix[6][6] = new EmptyCellEntity();
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
