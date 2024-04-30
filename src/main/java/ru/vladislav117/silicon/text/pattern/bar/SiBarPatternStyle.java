package ru.vladislav117.silicon.text.pattern.bar;

import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.SiTextPatternStyle;
import ru.vladislav117.silicon.text.style.SiStyleLike;

/**
 * Стиль шаблона "полоса".
 */
public class SiBarPatternStyle implements SiTextPatternStyle {
    protected SiTextLike leftBorder;
    protected String progressSymbol;
    protected SiStyleLike completeStyle;
    protected SiStyleLike incompleteStyle;
    protected SiTextLike rightBorder;

    /**
     * Создание стиля шаблона "полоса".
     *
     * @param leftBorder      Левая граница полосы
     * @param progressSymbol  Символ для прогресса полосы
     * @param completeStyle   Стиль для прогресса
     * @param incompleteStyle Стиль для остатка
     * @param rightBorder     Правая граница полосы
     */
    public SiBarPatternStyle(SiTextLike leftBorder, String progressSymbol, SiStyleLike completeStyle, SiStyleLike incompleteStyle, SiTextLike rightBorder) {
        this.leftBorder = leftBorder;
        this.progressSymbol = progressSymbol;
        this.completeStyle = completeStyle;
        this.incompleteStyle = incompleteStyle;
        this.rightBorder = rightBorder;
    }

    /**
     * Получение левой границы.
     *
     * @return Левая граница.
     */
    public SiTextLike getLeftBorder() {
        return leftBorder;
    }

    /**
     * Получение символа для прогресса полосы.
     *
     * @return Символ для прогресса полосы.
     */
    public String getProgressSymbol() {
        return progressSymbol;
    }

    /**
     * Получение стиля для прогресса.
     *
     * @return Стиль для прогресса.
     */
    public SiStyleLike getCompleteStyle() {
        return completeStyle;
    }

    /**
     * Получение стиля для остатка.
     *
     * @return Стиль для остатка.
     */
    public SiStyleLike getIncompleteStyle() {
        return incompleteStyle;
    }

    /**
     * Получение правой границы.
     *
     * @return Правая граница.
     */
    public SiTextLike getRightBorder() {
        return rightBorder;
    }
}
