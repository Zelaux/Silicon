package ru.vladislav117.silicon.icon;

import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;

/**
 * Класс-контейнер для иконок.
 */
public final class SiIcons {
    public static final SiContentList<SiIcon> all = new SiContentList<>();

    public static SiIcon unknown;

    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            unknown = new SiIcon("unknown");
        });

        loaders.addSecondaryLoader(() -> {

        });
    }
}
