package gui.model;

public interface Entity {
    void update(ModelContext context);

    int getX();
    int getY();
}
