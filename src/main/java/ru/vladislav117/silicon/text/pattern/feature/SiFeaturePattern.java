package ru.vladislav117.silicon.text.pattern.feature;

import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.SiTextPattern;

/**
 * Шаблон "свойство". Идея: два слова - свойство и значение разделены каким-либо разделителем.
 */
public class SiFeaturePattern implements SiTextPattern {
    protected SiTextLike feature;
    protected SiTextLike value;
    protected SiFeaturePatternStyle style = SiFeaturePatternStyles.Colon.greenYellow;

    /**
     * Создание шаблона "свойство".
     *
     * @param feature Название свойства
     * @param value   Значение свойства
     * @param style   Стиль шаблона
     */
    public SiFeaturePattern(SiTextLike feature, SiTextLike value, SiFeaturePatternStyle style) {
        this.feature = feature;
        this.value = value;
        this.style = style;
    }

    /**
     * Создание шаблона "свойство". Будет применён стиль "белое двоеточие".
     *
     * @param feature Название свойства
     * @param value   Значение свойства
     */
    public SiFeaturePattern(SiTextLike feature, SiTextLike value) {
        this.feature = feature;
        this.value = value;
    }

    /**
     * Создание шаблона "свойство".
     *
     * @param feature Название свойства
     * @param value   Значение свойства
     * @param style   Стиль шаблона
     */
    public SiFeaturePattern(String feature, Object value, SiFeaturePatternStyle style) {
        this.feature = SiText.string(feature);
        this.value = SiText.string(value);
        this.style = style;
    }

    /**
     * Создание шаблона "свойство". Будет применён стиль "белое двоеточие".
     *
     * @param feature Название свойства
     * @param value   Значение свойства
     */
    public SiFeaturePattern(String feature, Object value) {
        this.feature = SiText.string(feature);
        this.value = SiText.string(value);
    }

    /**
     * Создание шаблона "свойство". Будет применён стиль "белое двоеточие".
     *
     * @param feature Название свойства
     * @param value   Значение свойства
     */
    public SiFeaturePattern(String feature, SiTextLike value) {
        this.feature = SiText.string(feature);
        this.value = value;
    }

    /**
     * Получение названия свойства.
     *
     * @return Название свойства.
     */
    public SiTextLike getFeature() {
        return feature;
    }

    /**
     * Установка названия свойства.
     *
     * @param feature Название свойства
     * @return Этот же шаблон.
     */
    public SiFeaturePattern setFeature(SiTextLike feature) {
        this.feature = feature;
        return this;
    }

    /**
     * Получение значения свойства.
     *
     * @return Значение свойства.
     */
    public SiTextLike getValue() {
        return value;
    }

    /**
     * Установка значения свойства.
     *
     * @param value Значение свойства
     * @return Этот же шаблон.
     */
    public SiFeaturePattern setValue(SiTextLike value) {
        this.value = value;
        return this;
    }

    /**
     * Получение стиля шаблона.
     *
     * @return Стиль шаблона.
     */
    public SiFeaturePatternStyle getStyle() {
        return style;
    }

    /**
     * Установка стиля шаблона.
     *
     * @param style Стиль шаблона
     * @return Этот же шаблон.
     */
    public SiFeaturePattern setStyle(SiFeaturePatternStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public SiText build() {
        return SiText.container().addText(feature, style.getFeatureStyle()).addText(style.getSplitter()).addText(value, style.getValueStyle());
    }

    @Override
    public String toString() {
        return "SiFeaturePattern{" +
                "feature=" + feature +
                ", value=" + value +
                ", style=" + style +
                '}';
    }
}
