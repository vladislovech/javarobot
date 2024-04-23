package gui.view;

import gui.model.PoisonCellEntity;

import java.awt.*;

public class PoisonCellRenderer implements EntityRenderer<PoisonCellEntity> {
    public void render(PoisonCellEntity entity, Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;

        drawCell(g, entity);
    }

    private void drawCell(Graphics2D g, PoisonCellEntity entity) {
        g.setColor(entity.getColor());
        Point coords = entity.getCoords();
        int cellSize = entity.getCellSize();
        int gridStroke = entity.getGridStroke();
        g.fillRect(cellSize * coords.x + (coords.x + 1) * gridStroke, cellSize * coords.y + (coords.y + 1) * gridStroke, cellSize, cellSize);
    }
}
