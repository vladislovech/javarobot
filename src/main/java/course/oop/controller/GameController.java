package course.oop.controller;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.vecmath.Vector2d;

import course.oop.log.Logger;
import course.oop.model.GameModel;

/**
 * Контроллер GameWindow. Переводит модель в новое состояние по таймеру,
 * меняет положение цели в модели.
 */
public class GameController {
    /**
     * Модель, с которой работает контроллер
     */
    private GameModel gameModel;
    /**
     * Таймер, обновляющий модель (переводит ее в следующее состояние)
     */
    private final Timer timer;
    /**
     * Частота обновления модели по таймеру
     */
    private long updateFrequency;

    /**
     * Сохраняет модель, инициализирует таймер, задает частоту обновления.
     */
    public GameController(GameModel gameModel, long updateFrequency) {
        this.gameModel = gameModel;
        timer = new Timer("game model update", true);
        this.updateFrequency = updateFrequency;
    }

    /**
     * Сохраняет модель, задает частоту обновления 10мс
     */
    public GameController(GameModel gameModel) {
        this(gameModel, 10);
    }

    /**
     * Обновляет модель, передавая ей новые координаты цели
     */
    public void targetUpdate(Vector2d target) {
        gameModel.setTarget(target);
    }

    /**
     * Перегруженный targetUpdate
     */
    public void targetUpdate(Point point) {
        targetUpdate(new Vector2d(point.x, point.y));
    }

    /**
     * Запускает периодическое обновление модели по таймеру
     */
    public void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                gameModel.nextState(updateFrequency);
            }
        };
        timer.schedule(timerTask, 0, updateFrequency);
    }
}
