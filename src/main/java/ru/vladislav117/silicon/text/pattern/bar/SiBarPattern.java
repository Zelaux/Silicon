package ru.vladislav117.silicon.text.pattern.bar;

import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.pattern.SiTextPattern;

/**
 * Шаблон "полоса".
 */
public class SiBarPattern implements SiTextPattern {
    protected double progress;
    protected int length;
    protected SiBarPatternStyle style = SiBarPatternStyles.VerticalLineWithSquareBrackets.whiteGrayWithWhiteBorder;

    /**
     * Создание шаблона "полоса".
     *
     * @param progress Прогресс от 0 до 1
     * @param length   Длина полосы в символах
     * @param style    Стиль шаблона
     */
    public SiBarPattern(double progress, int length, SiBarPatternStyle style) {
        this.progress = progress;
        this.length = length;
        this.style = style;
    }

    /**
     * Создание шаблона "полоса".
     *
     * @param progress Прогресс от 0 до 1
     * @param length   Длина полосы в символах
     */
    public SiBarPattern(double progress, int length) {
        this.progress = progress;
        this.length = length;
    }

    @Override
    public SiText build() {
        int coloredSymbolsAmount = (int) (progress * length);
        int uncoloredSymbolsAmount = length - coloredSymbolsAmount;
        String coloredSymbols = style.getProgressSymbol().repeat(coloredSymbolsAmount);
        String uncoloredSymbols = style.getProgressSymbol().repeat(uncoloredSymbolsAmount);
        return SiText.container().addText(style.getLeftBorder()).addString(coloredSymbols, style.getCompleteStyle()).addString(uncoloredSymbols, style.getIncompleteStyle()).addText(style.getRightBorder());
    }
}
