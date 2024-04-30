package ru.vladislav117.silicon.range;

import ru.vladislav117.silicon.type.SiCloneable;

/**
 * Диапазон. Содержит левую и правую границу, а так же информацию, включены ли границы в диапазон.
 */
public class SiRange implements SiCloneable {
    protected double leftBorder = -Double.MAX_VALUE;
    protected double rightBorder = Double.MAX_VALUE;
    protected boolean includesLeftBorder = true;
    protected boolean includesRightBorder = true;

    /**
     * Создание нового диапазона.
     *
     * @param leftBorder          Левая граница
     * @param rightBorder         Правая граница
     * @param includesLeftBorder  Включена ли левая граница в диапазон
     * @param includesRightBorder Включена ли правая граница в диапазон
     */
    public SiRange(double leftBorder, double rightBorder, boolean includesLeftBorder, boolean includesRightBorder) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.includesLeftBorder = includesLeftBorder;
        this.includesRightBorder = includesRightBorder;
    }

    /**
     * Создание нового диапазона. Правая и левая границы включены в диапазон по умолчанию.
     *
     * @param leftBorder  Левая граница
     * @param rightBorder Правая граница
     */
    public SiRange(double leftBorder, double rightBorder) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    /**
     * Копирование существующего диапазона.
     *
     * @param range Диапазон, который будет скопирован
     */
    public SiRange(SiRange range) {
        leftBorder = range.leftBorder;
        rightBorder = range.rightBorder;
        includesLeftBorder = range.includesLeftBorder;
        includesRightBorder = range.includesRightBorder;
    }

    @Override
    public SiRange clone() {
        return new SiRange(this);
    }

    /**
     * Создание нового диапазона. Левая граница равна минимально возможному Double, а правая максимально возможно Double.
     * Правая и левая границы включены в диапазон по умолчанию.
     */
    public SiRange() {
    }

    /**
     * Получение левой границы диапазона.
     *
     * @return Левая граница диапазона.
     */
    public double getLeftBorder() {
        return leftBorder;
    }

    /**
     * Установка левой границы диапазона.
     *
     * @param leftBorder Левая граница диапазона
     * @return Этот же диапазон.
     */
    public SiRange setLeftBorder(double leftBorder) {
        this.leftBorder = leftBorder;
        return this;
    }

    /**
     * Получение правой границы диапазона.
     *
     * @return Правая граница диапазона.
     */
    public double getRightBorder() {
        return rightBorder;
    }

    /**
     * Установка правой границы диапазона.
     *
     * @param rightBorder Правая граница диапазона
     * @return Этот же диапазон.
     */
    public SiRange setRightBorder(double rightBorder) {
        this.rightBorder = rightBorder;
        return this;
    }

    /**
     * Получение включения левой границы в диапазон.
     *
     * @return Включена ли левая граница в диапазон.
     */
    public boolean isIncludesLeftBorder() {
        return includesLeftBorder;
    }

    /**
     * Установка включения левой границы в диапазон.
     *
     * @param includesLeftBorder Включена ли левая граница в диапазон
     * @return Этот же диапазон.
     */
    public SiRange setIncludesLeftBorder(boolean includesLeftBorder) {
        this.includesLeftBorder = includesLeftBorder;
        return this;
    }

    /**
     * Получение включения правой границы в диапазон.
     *
     * @return Включена ли правая граница в диапазон.
     */
    public boolean isIncludesRightBorder() {
        return includesRightBorder;
    }

    /**
     * Установка включения правой границы в диапазон.
     *
     * @param includesRightBorder Включена ли правая граница в диапазон
     * @return Этот же диапазон.
     */
    public SiRange setIncludesRightBorder(boolean includesRightBorder) {
        this.includesRightBorder = includesRightBorder;
        return this;
    }

    /**
     * Проверка нахождения числа в диапазоне.
     *
     * @param number Число
     * @return Находится ли число в диапазоне.
     */
    public boolean isInRange(double number) {
        if (leftBorder < number && number < rightBorder) return true;
        if (number == leftBorder && includesLeftBorder) return true;
        if (number == rightBorder && includesRightBorder) return true;
        return false;
    }

    @Override
    public String toString() {
        return (includesLeftBorder ? "[" : "(") + leftBorder + ", " + rightBorder + (includesRightBorder ? "]" : ")");
    }
}
