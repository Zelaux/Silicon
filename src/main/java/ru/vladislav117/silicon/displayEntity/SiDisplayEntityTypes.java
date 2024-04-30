package ru.vladislav117.silicon.displayEntity;

import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;

/**
 * Класс-контейнер для типов DisplayEntity.
 */
public final class SiDisplayEntityTypes {
    public static final SiContentList<SiDisplayEntityType> all = new SiContentList<>();

    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
        });

        loaders.addSecondaryLoader(() -> {
        });
    }
}
