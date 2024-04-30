package ru.vladislav117.silicon.text.style;

import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.type.SiCloneable;

/**
 * Класс, реализации которого представляют собой стиль текста.
 */
public class SiStyle implements SiStyleLike, SiCloneable {
    @Nullable
    protected SiColor color = null;
    @Nullable
    protected Boolean bold = null;
    @Nullable
    protected Boolean italic = null;
    @Nullable
    protected Boolean underlined = null;
    @Nullable
    protected Boolean strikethrough = null;
    @Nullable
    protected Boolean obfuscated = null;

    /**
     * Создание нового стиля текста.
     *
     * @param color         Цвет текста или null, если не задан
     * @param bold          Свойство текста "полужирный" или null, если не задано
     * @param italic        Свойство текста "курсив" или null, если не задано
     * @param underlined    Свойство текста "подчёркнутый" или null, если не задано
     * @param strikethrough Свойство текста "зачёркнутый" или null, если не задано
     * @param obfuscated    Свойство текста "запутанный" или null, если не задано
     */
    public SiStyle(@Nullable SiColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated) {
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
    }

    /**
     * Создание нового стиля текста.
     *
     * @param color Цвет текста или null, если не задан
     */
    public SiStyle(@Nullable SiColor color) {
        this.color = color;
    }

    /**
     * Создание нового стиля текста.
     *
     * @param bold          Свойство текста "полужирный" или null, если не задано
     * @param italic        Свойство текста "курсив" или null, если не задано
     * @param underlined    Свойство текста "подчёркнутый" или null, если не задано
     * @param strikethrough Свойство текста "зачёркнутый" или null, если не задано
     * @param obfuscated    Свойство текста "запутанный" или null, если не задано
     */
    public SiStyle(@Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated) {
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
    }

    /**
     * Копирование существующего стиля текста.
     *
     * @param style Стиль, который будет скопирован
     */
    public SiStyle(SiStyle style) {
        if (style.color != null) color = style.color.clone();
        bold = style.bold;
        italic = style.italic;
        underlined = style.underlined;
        strikethrough = style.strikethrough;
        obfuscated = style.obfuscated;
    }

    @Override
    public SiStyle clone() {
        return new SiStyle(this);
    }

    /**
     * Создание нового стиля текста без параметров.
     */
    public SiStyle() {
    }

    /**
     * Создание нового стиля текста без параметров.
     */
    public static SiStyle empty() {
        return new SiStyle();
    }

    /**
     * Получение цвета текста.
     *
     * @return Цвет текста или null, если не задан.
     */
    @Nullable
    public SiColor getColor() {
        return color;
    }

    /**
     * Установка цвета текста.
     *
     * @param color Цвет текста или null, если не задан
     * @return Этот же стиль.
     */
    public SiStyle setColor(@Nullable SiColor color) {
        this.color = color;
        return this;
    }

    /**
     * Получение свойства текста "полужирный".
     *
     * @return Свойство текста "полужирный" или null, если не задано.
     */
    @Nullable
    public Boolean isBold() {
        return bold;
    }

    /**
     * Установка свойства текста "полужирный".
     *
     * @param bold Свойство текста "полужирный" или null, если не задано
     * @return Этот же стиль.
     */
    public SiStyle setBold(@Nullable Boolean bold) {
        this.bold = bold;
        return this;
    }

    /**
     * Получение свойства текста "курсив".
     *
     * @return Свойство текста "курсив" или null, если не задано.
     */
    @Nullable
    public Boolean isItalic() {
        return italic;
    }

    /**
     * Установка свойства текста "курсив".
     *
     * @param italic Свойство текста "курсив" или null, если не задано
     * @return Этот же стиль.
     */
    public SiStyle setItalic(@Nullable Boolean italic) {
        this.italic = italic;
        return this;
    }

    /**
     * Получение свойства текста "подчёркнутый".
     *
     * @return Свойство текста "подчёркнутый" или null, если не задано.
     */
    @Nullable
    public Boolean isUnderlined() {
        return underlined;
    }


    /**
     * Установка свойства текста "подчёркнутый".
     *
     * @param underlined Свойство текста "подчёркнутый" или null, если не задано
     * @return Этот же стиль.
     */
    public SiStyle setUnderlined(@Nullable Boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    /**
     * Получение свойства текста "зачёркнутый".
     *
     * @return Свойство текста "зачёркнутый" или null, если не задано.
     */
    @Nullable
    public Boolean isStrikethrough() {
        return strikethrough;
    }

    /**
     * Установка свойства текста "зачёркнутый".
     *
     * @param strikethrough Свойство текста "зачёркнутый" или null, если не задано
     * @return Этот же стиль.
     */
    public SiStyle setStrikethrough(@Nullable Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    /**
     * Получение свойства текста "запутанный".
     *
     * @return Свойство текста "запутанный" или null, если не задано.
     */
    @Nullable
    public Boolean isObfuscated() {
        return obfuscated;
    }

    /**
     * Установка свойства текста "запутанный".
     *
     * @param obfuscated Свойство текста "запутанный" или null, если не задано
     * @return Этот же стиль.
     */
    public SiStyle setObfuscated(@Nullable Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    @Override
    public String toString() {
        return "SiStyle{" +
                "color=" + color +
                ", bold=" + bold +
                ", italic=" + italic +
                ", underlined=" + underlined +
                ", strikethrough=" + strikethrough +
                ", obfuscated=" + obfuscated +
                '}';
    }

    @Override
    public Style toStyle() {
        Style style = Style.empty();
        if (color != null) style.color(color.toTextColor());
        if (bold != null) style.decoration(TextDecoration.BOLD, bold);
        if (italic != null) style.decoration(TextDecoration.ITALIC, italic);
        if (underlined != null) style.decoration(TextDecoration.UNDERLINED, underlined);
        if (strikethrough != null) style.decoration(TextDecoration.STRIKETHROUGH, strikethrough);
        if (obfuscated != null) style.decoration(TextDecoration.OBFUSCATED, obfuscated);
        return style;
    }
}
