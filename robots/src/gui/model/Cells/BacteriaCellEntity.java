package gui.model.Cells;

import gui.Properties;
import gui.model.CommandsList.Reactions;
import gui.model.Handler;
import gui.model.WorldContext;

import java.awt.*;
import java.util.Random;

public class BacteriaCellEntity extends CellEntity {
    /**
     * Описывает бактерию
     */
    private final int BRAIN_SIZE = Properties.getBRAIN_SIZE();
    private final int MAX_COMMAND_COUNT = Properties.getMAX_COMMAND_COUNT();
    private int health = Properties.getBACTERIA_HEALTH();
    private int[] brain;
    private int nextCommand = 0;
    private int eyeSize;
    private Directions cellDirection;
    private final Handler handler = new Handler();

    public BacteriaCellEntity(Point p) {
        super(p, Color.blue);
        this.eyeSize = (int)Math.round(Properties.getCELL_SIZE() * 0.3) + ((int)Math.round(Properties.getCELL_SIZE() * 0.3)) % 2;
        brain = new int[BRAIN_SIZE];
        setCellDirection();
        fillBrain();
    }

    /**
     * Заполняет мозг бактерии случайными командами
     */
    private void fillBrain() {
        int minValue = 0;
        int maxValue = BRAIN_SIZE;
        for (int i = 0; i < BRAIN_SIZE; i++) {
            brain[i] = minValue + (int) (Math.random() * (maxValue - minValue));
        }
    }

    @Override
    public void update(WorldContext context) {
        int commandShift;
        int commandCount = 0;

        while (commandCount < MAX_COMMAND_COUNT) {
            int command = brain[nextCommand];
            Reactions reaction = handler.execute(this, command, context);
            commandShift = reaction.getShiftValue();
            if (reaction.isFullStop()) {
                commandCount = MAX_COMMAND_COUNT;
            }
            if (reaction == Reactions.BRAIN_JUMP_REACTION) {
                commandShift = command;
            }

            nextCommand = (nextCommand + commandShift) % BRAIN_SIZE;
            commandCount++;

            decreaseHealth(1);
            if (health == 0) {
                context.killCell(this);
                commandCount = MAX_COMMAND_COUNT;
            }
        }
    }

    public int getHealth() {
        return health;
    }
    public void increaseHealth(int amount) {
        health += amount;
    }
    public void decreaseHealth(int amount) {
        health -= amount;
    }

    public int getEyeSize() {
        return eyeSize;
    }

    /**
     * Устанавливает случайное направление взгляда (используется в конструкторе бактерии)
     */
    private void setCellDirection(){
        cellDirection = Directions.values()[new Random().nextInt(Directions.values().length)];
    }

    /**
     * Используется, если принято решение менять направление клетки перед каждым действием
     * @param command
     */
    private void setCellDirection(int command) {
        cellDirection = cellDirection.getStandardDirection(command);
    }

    /**
     * Поворачивает направление взгляда клетки
     * @param command
     */
    public void rotateCell(int command){
        cellDirection = cellDirection.getStandardDirection(command-23);
    }

    public Directions getCellDirection() {
        return cellDirection;
    }
}
