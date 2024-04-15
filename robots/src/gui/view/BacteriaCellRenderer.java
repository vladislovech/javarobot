package gui.view;

import gui.model.BacteriaCellEntity;
import gui.model.CellEntity;

import java.awt.*;

public class BacteriaCellRenderer implements EntityRenderer<BacteriaCellEntity> {
    public void render(BacteriaCellEntity entity, Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;

        drawCell(g, entity);
    }

    private void drawCell(Graphics2D g, BacteriaCellEntity entity) {
        g.setColor(entity.getColor());
        Point coords = entity.getCoords();
        int cellSize = entity.getCellSize();
        g.fillRect(cellSize * coords.x + coords.x + 1, cellSize * coords.y + coords.y + 1, cellSize, cellSize);
    }
}
