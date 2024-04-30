package ru.vladislav117.silicon.text.pattern.feature;

import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.text.SiText;

/**
 * Класс-контейнер для стилей шаблона "свойство".
 */
public final class SiFeaturePatternStyles {
    /**
     * Стили с разделителем "тире". Пример: "свойство - значение".
     */
    public static final class Dash {
        public static final SiFeaturePatternStyle white = new SiFeaturePatternStyle(SiPalette.Interface.white, SiText.string(" - "), SiPalette.Interface.white);
    }

    /**
     * Стили с разделителем "двоеточие". Пример: "свойство: значение".
     */
    public static final class Colon {
        public static final SiFeaturePatternStyle white = new SiFeaturePatternStyle(SiPalette.Interface.white, SiText.string(": "), SiPalette.Interface.white);
        public static final SiFeaturePatternStyle greenYellow = new SiFeaturePatternStyle(SiPalette.Interface.green, SiText.string(": "), SiPalette.Interface.yellow);
    }

    /**
     * Стили с разделителем "стрелка". Пример: "свойство -> значение".
     */
    public static final class Arrow {
        public static final SiFeaturePatternStyle white = new SiFeaturePatternStyle(SiPalette.Interface.white, SiText.string(" -> "), SiPalette.Interface.white);
    }
}
