package ru.vladislav117.silicon.economy;

import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;

/**
 * Класс-контейнер для валют.
 */
public final class SiCurrencies {
    public static final SiContentList<SiCurrency> all = new SiContentList<>();
    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
    }
}
