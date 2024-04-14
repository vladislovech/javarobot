package gui.view;

import gui.model.BacteriaCellEntity;
import gui.model.CellEntity;

import java.awt.*;

public class BacteriaCellRenderer implements EntityRenderer<BacteriaCellEntity> {
    @Override
    public void render(BacteriaCellEntity entity, Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;

        drawCell(g, entity);
    }

    private void drawCell(Graphics2D g, BacteriaCellEntity entity) {
        g.setColor(entity.getColor());
        // g.fillRect();
    }
}
