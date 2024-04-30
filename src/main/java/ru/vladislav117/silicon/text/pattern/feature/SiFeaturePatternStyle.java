package ru.vladislav117.silicon.text.pattern.feature;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.SiTextPatternStyle;
import ru.vladislav117.silicon.text.style.SiStyleLike;

/**
 * Стиль для шаблона "свойство".
 */
public class SiFeaturePatternStyle implements SiTextPatternStyle {
    @Nullable
    protected SiStyleLike featureStyle = null;
    protected SiTextLike splitter;
    @Nullable
    protected SiStyleLike valueStyle = null;

    /**
     * Создание нового стиля для шаблона "свойство".
     *
     * @param featureStyle Стиль названия свойства или null, если не задан
     * @param splitter     Разделитель свойства и значения
     * @param valueStyle   Стиль значения свойства или null, если не задан
     */
    public SiFeaturePatternStyle(@Nullable SiStyleLike featureStyle, SiTextLike splitter, @Nullable SiStyleLike valueStyle) {
        this.featureStyle = featureStyle;
        this.splitter = splitter;
        this.valueStyle = valueStyle;
    }

    /**
     * Создание нового стиля для шаблона "свойство".
     *
     * @param splitter Разделитель свойства и значения
     */
    public SiFeaturePatternStyle(SiTextLike splitter) {
        this.splitter = splitter;
    }

    /**
     * Получение стиля названия свойства.
     *
     * @return Стиль названия свойства или null, не задан.
     */
    @Nullable
    public SiStyleLike getFeatureStyle() {
        return featureStyle;
    }

    /**
     * Установка значения стиля названия свойства.
     *
     * @param featureStyle Стиль названия свойства или null, не задан
     * @return Этот же стиль.
     */
    public SiFeaturePatternStyle setFeatureStyle(@Nullable SiStyleLike featureStyle) {
        this.featureStyle = featureStyle;
        return this;
    }

    /**
     * Получение разделителя свойства и значения.
     *
     * @return Разделитель свойства и значения.
     */
    public SiTextLike getSplitter() {
        return splitter;
    }

    /**
     * Установка разделителя свойства и значения.
     *
     * @param splitter Разделитель свойства и значения
     * @return Этот же стиль.
     */
    public SiFeaturePatternStyle setSplitter(SiTextLike splitter) {
        this.splitter = splitter;
        return this;
    }

    /**
     * Получение стиля значения свойства.
     *
     * @return Стиль значения свойства или null, не задан.
     */
    @Nullable
    public SiStyleLike getValueStyle() {
        return valueStyle;
    }

    /**
     * Установка значения стиля значения свойства.
     *
     * @param valueStyle Стиль значения свойства или null, не задан
     * @return Этот же стиль.
     */
    public SiFeaturePatternStyle setValueStyle(@Nullable SiStyleLike valueStyle) {
        this.valueStyle = valueStyle;
        return this;
    }

    @Override
    public String toString() {
        return "SiFeaturePatternStyle{" +
                "featureStyle=" + featureStyle +
                ", splitter=" + splitter +
                ", valueStyle=" + valueStyle +
                '}';
    }
}
