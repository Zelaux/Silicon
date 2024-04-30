package ru.vladislav117.silicon.liquid;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

/**
 * Тип жидкости.
 */
public class SiLiquidType extends SiContent {
    protected boolean replaceable = false;
    protected double defaultTemperature;
    protected boolean vacuum = false;
    protected SiLiquidDisplay display = null;
    protected SiLiquidInteractions interactions = null;
    protected SiLiquidTransformation lowTemperatureTransformation = new SiLiquidTransformation(-Double.MAX_VALUE, null);
    protected SiLiquidTransformation highTemperatureTransformation = new SiLiquidTransformation(Double.MAX_VALUE, null);

    /**
     * Создание новой жидкости.
     *
     * @param name               Имя жидкости
     * @param defaultTemperature Температура по умолчанию
     */
    public SiLiquidType(String name, double defaultTemperature) {
        super(name);
        this.defaultTemperature = defaultTemperature;
        SiLiquidTypes.all.add(this);
    }

    /**
     * Заменяема ли жидкость. Если заменяема, то контейнер с жидкостью может быть наполнен любой другой жидкостью.
     *
     * @return Заменяема ли жидкость.
     */
    public boolean isReplaceable() {
        return replaceable || vacuum;
    }

    /**
     * Получение температуры по умолчанию.
     *
     * @return Температура по умолчанию.
     */
    public double getDefaultTemperature() {
        return defaultTemperature;
    }

    /**
     * Является ли жидкость вакуумом.
     *
     * @return Является ли жидкость вакуумом.
     */
    public boolean isVacuum() {
        return vacuum;
    }

    /**
     * Получение отображения жидкости.
     *
     * @return Отображение жидкости или null, если не задано.
     */
    @Nullable
    public SiLiquidDisplay getDisplay() {
        return display;
    }

    /**
     * Получение отображаемого имени жидкости.
     *
     * @return Отображаемое имя жидкости.
     */
    public SiTextLike getDisplayName() {
        return display == null ? SiText.string(name) : display.getDisplayName();
    }

    /**
     * Получение цвета жидкости.
     *
     * @return Цвет жидкости.
     */
    public SiColor getColor() {
        return display == null ? SiPalette.Defaults.white : display.getColor();
    }

    /**
     * Получение взаимодействий с жидкостью.
     *
     * @return Взаимодействия с жидкостью или null, если не заданы.
     */
    @Nullable
    public SiLiquidInteractions getInteractions() {
        return interactions;
    }

    /**
     * Получение трансформации при низкой температуре.
     *
     * @return Трансформация при низкой температуре.
     */
    public SiLiquidTransformation getLowTemperatureTransformation() {
        return lowTemperatureTransformation;
    }

    /**
     * Установка трансформации при низкой температуре.
     *
     * @param lowTemperatureTransformation Трансформация при низкой температуре
     * @return Этот же тип жидкости.
     */
    public SiLiquidType setLowTemperatureTransformation(SiLiquidTransformation lowTemperatureTransformation) {
        this.lowTemperatureTransformation = lowTemperatureTransformation;
        return this;
    }

    /**
     * Получение трансформации при высокой температуре.
     *
     * @return Трансформация при высокой температуре.
     */
    public SiLiquidTransformation getHighTemperatureTransformation() {
        return highTemperatureTransformation;
    }

    /**
     * Установка трансформации при высокой температуре.
     *
     * @param highTemperatureTransformation Трансформация при высокой температуре
     * @return Этот же тип жидкости.
     */
    public SiLiquidType setHighTemperatureTransformation(SiLiquidTransformation highTemperatureTransformation) {
        this.highTemperatureTransformation = highTemperatureTransformation;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
