package ru.vladislav117.silicon.text.pattern.bar;

import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.text.SiText;

/**
 * Стили для шаблона "полоса".
 */
public final class SiBarPatternStyles {
    /**
     * Стили с вертикальными линиями в квадратных скобках ("[||||||||||]").
     */
    public static final class VerticalLineWithSquareBrackets {
        public static final SiBarPatternStyle whiteGrayWithWhiteBorder = new SiBarPatternStyle(SiText.string("["), "|", SiPalette.Interface.white, SiPalette.Interface.gray, SiText.string("]"));
        public static final SiBarPatternStyle greenGrayWithGoldBorder = new SiBarPatternStyle(SiText.string("[", SiPalette.Interface.gold), "|", SiPalette.Interface.green, SiPalette.Interface.gray, SiText.string("]", SiPalette.Interface.gold));
    }
}
