package ru.vladislav117.silicon.text.structure;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextContainer;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.SiTextString;
import ru.vladislav117.silicon.text.style.SiStyleLike;

import java.util.ArrayList;

/**
 * Многострочный текст, ограниченный максимальным количеством символов в строке.
 */
public class SiLinedText {
    public static final int defaultMaximalLineLength = 42;

    protected String text;
    protected int maximalLineLength = defaultMaximalLineLength;
    @Nullable
    protected SiStyleLike style = null;

    protected SiTextContainer completeText;
    protected ArrayList<SiTextLike> completeTextParts;
    protected ArrayList<Component> components;

    /**
     * Создание нового многострочного текста.
     *
     * @param text              Текст, который будет разделён на строки
     * @param maximalLineLength Максимальное количество символов в строке
     * @param style             Стиль текста или null, если не задано
     */
    public SiLinedText(String text, int maximalLineLength, @Nullable SiStyleLike style) {
        this.text = text;
        this.maximalLineLength = maximalLineLength;
        this.style = style;
        build();
    }

    /**
     * Создание нового многострочного текста.
     *
     * @param text              Текст, который будет разделён на строки
     * @param maximalLineLength Максимальное количество символов в строке
     */
    public SiLinedText(String text, int maximalLineLength) {
        this.text = text;
        this.maximalLineLength = maximalLineLength;
        build();
    }

    /**
     * Создание нового многострочного текста.
     * Будет использована максимальная длина по умолчанию (значение defaultMaximalLineLength).
     *
     * @param text  Текст, который будет разделён на строки
     * @param style Стиль текста или null, если не задано
     */
    public SiLinedText(String text, @Nullable SiStyleLike style) {
        this.text = text;
        this.style = style;
        build();
    }

    /**
     * Создание нового многострочного текста.
     * Будет использована максимальная длина по умолчанию (значение defaultMaximalLineLength).
     *
     * @param text Текст, который будет разделён на строки
     */
    public SiLinedText(String text) {
        this.text = text;
        build();
    }

    /**
     * Разделение текста на строки.
     */
    protected void build() {
        ArrayList<String> strings = new ArrayList<>();
        String[] parts = text.split(" ");
        int currentStringLength = 0;
        for (String part : parts) {
            if (currentStringLength == 0) {
                strings.add(part);
                currentStringLength = part.length();
                continue;
            }
            if (currentStringLength + part.length() + 1 <= maximalLineLength) {
                strings.set(strings.size() - 1, strings.get(strings.size() - 1) + " " + part);
                currentStringLength += part.length() + 1;
                continue;
            }
            strings.add(part);
            currentStringLength = part.length();
        }

        completeText = new SiTextContainer();
        completeTextParts = new ArrayList<>();
        components = new ArrayList<>();
        for (String string : strings) {
            SiText line = new SiTextString(string).setStyle(style);
            completeText.addText(line).addNewLine();
            completeTextParts.add(line);
            components.add(line.toComponent());
        }
    }

    /**
     * Получение полного текста.
     *
     * @return Полный текст.
     */
    public String getText() {
        return text;
    }

    /**
     * Установка полного текста.
     *
     * @param rawText Текст, который будет разделён на строки
     * @return Этот же текст.
     */
    public SiLinedText setText(String rawText) {
        this.text = rawText;
        build();
        return this;
    }

    /**
     * Получение максимального количества символов в строке.
     *
     * @return Максимальное количество символов в строке.
     */
    public int getMaximalLineLength() {
        return maximalLineLength;
    }

    /**
     * Установка максимального количества символов в строке.
     *
     * @param maximalLineLength Максимальное количество символов в строке
     * @return Этот же текст.
     */
    public SiLinedText setMaximalLineLength(int maximalLineLength) {
        this.maximalLineLength = maximalLineLength;
        build();
        return this;
    }

    /**
     * Получение стиля текста.
     *
     * @return Стиль текста или null, если не задан.
     */
    @Nullable
    public SiStyleLike getStyle() {
        return style;
    }

    /**
     * Установка стиля текста.
     *
     * @param style Стиль текста или null, если не задан
     * @return Этот же текст.
     */
    public SiLinedText setStyle(@Nullable SiStyleLike style) {
        this.style = style;
        build();
        return this;
    }

    /**
     * Получение контейнера со строками текста заданной длины.
     * <p>
     * ВАЖНО! Если вам нужно установить это в описание предмета,
     * то правильнее будет использовать метод getComponents(), так как компоненты не требуют обработки.
     * <p>
     * ВАЖНО! Не стоит использовать этот метод для установки описания,
     * так как предметы *почему-то* не воспринимают переносы строк.
     * Используйте getComponents() или getCompleteTextParts().
     *
     * @return Контейнер строк текста.
     */
    public SiTextContainer getCompleteText() {
        return completeText;
    }

    /**
     * Получение строк текста.
     *
     * @return Строки текста.
     */
    public ArrayList<SiTextLike> getCompleteTextParts() {
        return completeTextParts;
    }

    /**
     * Получение списка компонентов со строками текста заданной длины.
     *
     * @return Список компонентов со строками текста.
     */
    public ArrayList<Component> getComponents() {
        return components;
    }
}
