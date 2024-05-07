package gui.model.Cells;

import gui.model.Directions;
import gui.model.WorldContext;

import java.awt.*;
import java.util.Random;

public class BacteriaCellEntity extends CellEntity {
    /**
     * Описывает бактерию
     */
    private final int BRAIN_SIZE = 64;
    private final int MAX_COMMAND_COUNT = 1;
    private int[] brain;
    private int nextCommand = 0;
    private int health = 10;
    private Directions cellDirection;
    private int eyeSize = 12;

    public BacteriaCellEntity(Point p, int cellSize, int gridStroke) {
        super(p, Color.BLUE, cellSize, gridStroke);
        brain = new int[BRAIN_SIZE];
        setCellDirection();
        fillBrain();
    }

    private void fillBrain() {
        int minValue = 0;
        int maxValue = 32; // 63
        for (int i = 0; i < BRAIN_SIZE; i++) {
            brain[i] = minValue + (int) (Math.random() * (maxValue - minValue)); // рандомное значение
        }
    }

    @Override
    public void update(WorldContext context) {
        // Сдвиг: яд - 1, стена - 2, бактерия - 3, еда - 4, пусто - 5
        int commandShift;
        int commandCount = 0;
        while (commandCount <= MAX_COMMAND_COUNT) {
            int command = brain[nextCommand];
            setCellDirection(command);
            if (command < 8) {
                commandShift = context.moveCell(this, command);
                //commandShift = context.completeCommand(this, command);
                commandCount = MAX_COMMAND_COUNT;
            }
            else if (command < 16) {
                commandShift = context.catchCell(this, command);
                commandCount = MAX_COMMAND_COUNT;
            }
            else if (command < 24) {
                commandShift = context.look(this, command);
            }
            else if (command < 32) {
                rotateCell(command);
                commandShift = 1;
            }
            else commandShift = command;
            commandCount++;

            nextCommand = (nextCommand + commandShift) % BRAIN_SIZE;

            decreaseHealth(1);
            if (health == 0) {
                context.killCell(this);
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

    public void setCellDirection(){
        cellDirection = Directions.values()[new Random().nextInt(Directions.values().length)];
    }

    private void setCellDirection(int command) {
        cellDirection = cellDirection.getStandardDirection(command);
    }

    private void rotateCell(int command){
        cellDirection = cellDirection.getStandardDirection(command-23);
    }

    public int getEyeSize() {
        return eyeSize;
    }

    public Directions getCellDirection() {
        return cellDirection;
    }
}
