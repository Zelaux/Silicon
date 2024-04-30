package ru.vladislav117.silicon.area;

import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;

/**
 * Класс-контейнер для типов зон.
 */
public class SiAreaTypes {
    public static final SiContentList<SiAreaType> all = new SiContentList<>();

    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
    }
}
