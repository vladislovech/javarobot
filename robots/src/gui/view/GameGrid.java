package gui.view;

import gui.model.World;

import java.awt.*;

public class GameGrid {
    private final int cellCountWidth;
    private final int cellCountHeight;
    private final int gw_width;
    private final int gw_height;
    private final int cellSize;
    public GameGrid(World world) {
        this.cellCountWidth = world.getCellCountWidth();
        this.cellCountHeight = world.getCellCountHeight();
        this.gw_width = world.getWidth();
        this.gw_height = world.getHeight();
        this.cellSize = world.getCellSize();
        System.out.println(cellCountWidth);
        System.out.println(cellCountHeight);
        System.out.println(gw_width);
        System.out.println(gw_height);
        System.out.println(cellSize);
    }
    public void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= cellCountWidth; i++) { // вертикальные линии
            g.drawLine(cellSize * i + i, 0, cellSize * i + i, cellSize * cellCountHeight + cellCountHeight);
        }
        for (int i = 0; i <= cellCountHeight; i++) { // горизонтальные линии
            g.drawLine(0, cellSize * i + i, cellSize * cellCountWidth + cellCountWidth, cellSize * i + i);
        }
    }
}
