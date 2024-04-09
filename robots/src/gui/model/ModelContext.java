package gui.model;

public class ModelContext {
    private final Model model;

    public ModelContext(Model model) {
        this.model = model;
    }
    public Entity getEntityOnCoords(int x, int y) {
        return model.getEntityOnCoords(x, y);
    }
}
