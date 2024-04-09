package gui.model;

public abstract class CellEntity implements Entity {
    /**
     * Описывает универсальную клетку
     */
    private CellType type;
    public CellEntity(CellType type) {this.type = type;}
    public CellType getType() {return type;}
}
