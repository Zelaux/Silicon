package ru.vladislav117.silicon.liquid;

import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.style.SiStyleLike;

/**
 * Отображение жидкости.
 */
public class SiLiquidDisplay {
    protected SiTextLike displayName;
    protected SiColor color;

    /**
     * Создание нового отображения жидкости.
     *
     * @param displayName      Имя
     * @param displayNameStyle Стиль имени
     * @param color            Цвет жидкости
     */
    public SiLiquidDisplay(SiText displayName, SiStyleLike displayNameStyle, SiColor color) {
        this.displayName = displayName.clone().setStyle(displayNameStyle);
        this.color = color;
    }

    /**
     * Создание нового отображения жидкости.
     *
     * @param displayName Имя
     * @param color       Цвет жидкости
     */
    public SiLiquidDisplay(SiTextLike displayName, SiColor color) {
        this.displayName = displayName;
        this.color = color;
    }

    /**
     * Создание нового отображения жидкости.
     *
     * @param displayName      Имя
     * @param displayNameStyle Стиль имени
     * @param color            Цвет жидкости
     */
    public SiLiquidDisplay(String displayName, SiStyleLike displayNameStyle, SiColor color) {
        this.displayName = SiText.string(displayName, displayNameStyle);
        this.color = color;
    }

    /**
     * Создание нового отображения жидкости.
     *
     * @param displayName Имя
     * @param color       Цвет имени и жидкости
     */
    public SiLiquidDisplay(String displayName, SiColor color) {
        this.displayName = SiText.string(displayName, color);
        this.color = color;
    }

    /**
     * Получение отображаемого имени жидкости.
     *
     * @return Отображаемое имя жидкости.
     */
    public SiTextLike getDisplayName() {
        return displayName;
    }

    /**
     * Получение цвета жидкости.
     *
     * @return Цвет жидкости.
     */
    public SiColor getColor() {
        return color;
    }
}
