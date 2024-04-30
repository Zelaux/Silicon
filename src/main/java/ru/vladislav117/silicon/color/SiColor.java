package ru.vladislav117.silicon.color;

import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import ru.vladislav117.silicon.text.style.SiStyleLike;
import ru.vladislav117.silicon.type.SiCloneable;

/**
 * Класс, экземпляры которого являются RGB цветом и могут быть использованы в качестве стиля текста.
 */
public class SiColor implements SiStyleLike, SiCloneable {
    protected double red = 0;
    protected double green = 0;
    protected double blue = 0;

    /**
     * Создание нового RGB цвета.
     *
     * @param red   Красный компонент цвета в диапазоне [0-1]
     * @param green Зелёный компонент цвета в диапазоне [0-1]
     * @param blue  Синий компонент цвета в диапазоне [0-1]
     */
    public SiColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Копирование существующего цвета.
     *
     * @param color Цвет, который будет скопирован
     */
    public SiColor(SiColor color) {
        red = color.red;
        green = color.green;
        blue = color.blue;
    }

    @Override
    public SiColor clone() {
        return new SiColor(this);
    }

    /**
     * Создание нового RGB цвета, по умолчанию чёрный.
     */
    public SiColor() {
    }

    /**
     * Получение красного компонента цвета.
     *
     * @return Красный компонент цвета в диапазоне [0-1].
     */
    public double getRed() {
        return red;
    }

    /**
     * Установка красного компонента цвета.
     *
     * @param red Красный компонент цвета в диапазоне [0-1]
     * @return Этот же цвет.
     */
    public SiColor setRed(double red) {
        this.red = red;
        return this;
    }

    /**
     * Получение зелёного компонента цвета.
     *
     * @return Зелёный компонент цвета в диапазоне [0-1].
     */
    public double getGreen() {
        return green;
    }

    /**
     * Установка зелёного компонента цвета.
     *
     * @param green Зелёный компонент цвета в диапазоне [0-1]
     * @return Этот же цвет.
     */
    public SiColor setGreen(double green) {
        this.green = green;
        return this;
    }

    /**
     * Получение синего компонента цвета.
     *
     * @return Синий компонент цвета в диапазоне [0-1].
     */
    public double getBlue() {
        return blue;
    }

    /**
     * Установка синего компонента цвета.
     *
     * @param blue Синий компонент цвета в диапазоне [0-1]
     * @return Этот же цвет.
     */
    public SiColor setBlue(double blue) {
        this.blue = blue;
        return this;
    }

    /**
     * Смешивание этого цвета с другим.
     *
     * @param color Цвет, который будет смешан в соотношении 1 к 1
     * @return Этот же цвет.
     */
    public SiColor mix(SiColor color) {
        red = (red + color.red) / 2;
        green = (green + color.green) / 2;
        blue = (blue + color.blue) / 2;
        return this;
    }

    /**
     * Смешивание этого цвета с другим. Будет создан новый цвет, а текущий не изменится.
     *
     * @param color Цвет, который будет смешан в соотношении 1 к 1
     * @return Новый экземпляр цвета.
     */
    public SiColor toMixed(SiColor color) {
        return clone().mix(color);
    }

    /**
     * Преобразование объекта к awt-цвету.
     *
     * @return Awt-цвет.
     */
    public java.awt.Color toAwtColor() {
        return new java.awt.Color((float) red, (float) green, (float) blue);
    }

    /**
     * Преобразование объекта к bukkit-цвету.
     *
     * @return Bukkit-цвет.
     */
    public Color toBukkitColor() {
        return Color.fromRGB((int) (red * 255), (int) (green * 255), (int) (blue * 255));
    }

    /**
     * Преобразование объекта к цвету текста.
     *
     * @return Цвет текста.
     */
    public TextColor toTextColor() {
        return TextColor.color((float) red, (float) green, (float) blue);
    }

    @Override
    public String toString() {
        return "(" + red + ", " + green + ", " + blue + ")";
    }

    @Override
    public Style toStyle() {
        return Style.style(toTextColor());
    }
}
