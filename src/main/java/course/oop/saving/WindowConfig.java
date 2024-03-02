package course.oop.saving;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

/**
 * Класс-сущность, хранящий данные, которые окно сохраняет и загружает
 */
public class WindowConfig implements Serializable {
    /**
     * Размер окна
     */
    private Dimension size;
    /**
     * Расположение окна
     */
    private Point location;
    /**
     * Было ли окно свернуто
     */
    private boolean isIcon;

    public WindowConfig(Dimension size, Point location, boolean isIcon) {
        this.size = size;
        this.location = location;
        this.isIcon = isIcon;
    }

    /**
     * Конструктор копирования
     */
    public WindowConfig(WindowConfig orig) {
        this.size = new Dimension(orig.size);
        this.location = new Point(orig.location);
        this.isIcon = orig.isIcon;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isIcon() {
        return isIcon;
    }

    public void setIcon(boolean isIcon) {
        this.isIcon = isIcon;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + (isIcon ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WindowConfig other = (WindowConfig) obj;
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (isIcon != other.isIcon)
            return false;
        return true;
    }

    public String toStringFormatted() {
        return "WindowConfig [size=" + size + ", location=" + location + ", isIcon=" + isIcon + "]";
    }
}
