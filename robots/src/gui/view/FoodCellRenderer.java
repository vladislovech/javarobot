package gui.view;

import gui.model.BacteriaCellEntity;
import gui.model.CellEntity;
import gui.model.FoodCellEntity;

import java.awt.*;

public class FoodCellRenderer implements EntityRenderer<FoodCellEntity> {
    @Override
    public void render(FoodCellEntity entity, Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;

        drawCell(g, entity);
    }

    private void drawCell(Graphics2D g, FoodCellEntity entity) {
        g.setColor(entity.getColor());
        Point coords = entity.getCoords();
        int cellSize = entity.getCellSize();
        int gridStroke = entity.getGridStroke();
        g.fillRect(cellSize * coords.x + (coords.x + 1) * gridStroke, cellSize * coords.y + (coords.y + 1) * gridStroke, cellSize, cellSize);
    }
}
