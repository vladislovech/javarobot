package course.oop.saving;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

/**
 * Неизменяемый класс, хранящий состояние окна:
 * - положение
 * - размер
 * - свернутость
 */
public class FrameConfig implements Serializable {
    /**
     * Размер окна
     */
    private final Pair size;
    /**
     * Расположение окна
     */
    private final Pair location;
    /**
     * Свернутость окна
     */
    private final boolean isIcon;

    /**
     * Типовой конструктор
     */
    public FrameConfig(Pair size, Pair location, boolean isIcon) {
        this.size = size;
        this.location = location;
        this.isIcon = isIcon;
    }

    /**
     * Удобный конструктор при работе с окнами
     */
    public FrameConfig(Dimension size, Point location, boolean isIcon) {
        this(new Pair(size), new Pair(location), isIcon);
    }

    public Pair getSize() {
        return size;
    }

    public Pair getLocation() {
        return location;
    }

    public boolean isIcon() {
        return isIcon;
    }

    /**
     * Возвращает строковое представление объекта
     */
    @Override
    public String toString() {
        return "WindowConfig [size=" + size + ", location=" + location
                + ", isIcon=" + isIcon + "]";
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
        FrameConfig other = (FrameConfig) obj;
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
}
