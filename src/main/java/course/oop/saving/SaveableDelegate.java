package course.oop.saving;

import java.awt.Component;
import java.beans.PropertyVetoException;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 * Класс, которому можно делегировать выполнение интерфейса Saveable.
 * Работает с JFrame и JInternalFrame
 */
public class SaveableDelegate implements Saveable {
    /**
     * Объект, к которому привязан делегат.
     */
    private final Component c;
    /**
     * Уникальный идентификатор окна, которое делегирует реализацию
     * интерфейса Saveable этому классу.
     */
    private String id;

    public SaveableDelegate(Component c, String id) throws IllegalArgumentException {
        if (c instanceof JFrame) {
            this.c = c;
            this.id = id;
        } else if (c instanceof JInternalFrame) {
            this.c = c;
            this.id = id;
        } else {
            throw new IllegalArgumentException("Класс не поддерживает окна переданного типа.");
        }
    }

    /**
     * Возвращает состояние окна, которое нужно сохранить
     */
    @Override
    public FrameConfig getWindowConfig() {
        if (c instanceof JFrame) {
            JFrame jf = (JFrame) c;
            return new FrameConfig(jf.getSize(), jf.getLocation(), false);
        } else if (c instanceof JInternalFrame) {
            JInternalFrame jif = (JInternalFrame) c;
            return new FrameConfig(jif.getSize(), jif.getLocation(), jif.isIcon());
        } else {
            throw new RuntimeException("Не удалось преобразовать тип у %s, хотя должно.".formatted(id));
        }
    }

    /**
     * Возвращает уникальный идентификатор окна
     */
    @Override
    public String getFrameId() {
        return id;
    }

    /**
     * Обновляет свое состояние, беря данные из параметра
     */
    @Override
    public void loadConfig(FrameConfig config) {
        if (c instanceof JFrame) {
            JFrame jf = (JFrame) c;
            jf.setSize(config.getSize().toDimension());
            jf.setLocation(config.getLocation().toPoint());
            return;
        } else if (c instanceof JInternalFrame) {
            JInternalFrame jif = (JInternalFrame) c;
            jif.setSize(config.getSize().toDimension());
            jif.setLocation(config.getLocation().toPoint());
            try {
                jif.setIcon(config.isIcon());
            } catch (PropertyVetoException e) {
                System.err.println("Не удалось свернуть окно %s".formatted(id));
            }
            return;
        }
        throw new RuntimeException("Не удалось преобразовать тип у, хотя должно.".formatted(id));
    }
}
