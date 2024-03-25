package course.oop.saving;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

/**
 * Неизменяемый класс, хранящий пару целых чисел.
 */
public class Pair implements Serializable {
    private final int first;
    private final int second;

    /**
     * Пустой конструктор
     */
    public Pair() {
        first = 0;
        second = 0;
    }

    /**
     * Стандартный конструктор
     */
    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Конструктор из java.awt.Dimension
     */
    public Pair(Dimension dimension) {
        this.first = dimension.width;
        this.second = dimension.height;
    }

    /**
     * Конструктор из java.awt.Point
     */
    public Pair(Point point) {
        this.first = point.x;
        this.second = point.y;
    }

    /**
     * Конструктор копирования
     */
    public Pair(Pair other) {
        this.first = other.first;
        this.second = other.second;
    }

    /**
     * геттер
     */
    public int getFirst() {
        return first;
    }

    /**
     * геттер
     */
    public int getSecond() {
        return second;
    }

    /**
     * Возвращает представление данных объекта в виде java.awt.Dimension
     */
    public Dimension toDimension() {
        return new Dimension(first, second);
    }

    /**
     * Возвращает представление данных объекта в виде java.awt.Point
     */
    public Point toPoint() {
        return new Point(first, second);
    }

    /**
     * Возвращает строковое представление объекта
     */
    @Override
    public String toString() {
        return "Pair [first=" + first + ", second=" + second + "]";
    }

    /**
     * Возвращает хеш объекта
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + first;
        result = prime * result + second;
        return result;
    }

    /**
     * Проверяет объект на равенство с другим
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (first != other.first)
            return false;
        if (second != other.second)
            return false;
        return true;
    }
}
