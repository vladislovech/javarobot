package gui;

public class Properties {
    public final static int BRAIN_SIZE = 64;
    public final static int MAX_COMMAND_COUNT = 15;
    public final static int INITIAL_BACTERIA_HEALTH = 45;
    private static int CELL_COUNT_WIDTH = 24;
    private static int CELL_COUNT_HEIGHT = 20;
    private static int CELL_SIZE = 30;
    private static int GRID_STROKE = 2;
    public static int getBRAIN_SIZE() {return BRAIN_SIZE;}
    public static int getMAX_COMMAND_COUNT() {return MAX_COMMAND_COUNT;}
    public static int getBACTERIA_HEALTH() {return INITIAL_BACTERIA_HEALTH;}
    public static int getCELL_COUNT_WIDTH() {return CELL_COUNT_WIDTH;}
    public static int getCELL_COUNT_HEIGHT() {return CELL_COUNT_HEIGHT;}
    public static int getCELL_SIZE() {return CELL_SIZE;}
    public static int getGRID_STROKE() {return GRID_STROKE;}
}
