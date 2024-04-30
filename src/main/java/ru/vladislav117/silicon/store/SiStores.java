package ru.vladislav117.silicon.store;

import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.file.SiFile;

/**
 * Класс-контейнер для лавок.
 */
public final class SiStores {
    public static final SiFile directory = Silicon.getDirectory().getChild("stores");
    public static final SiContentList<SiStore> all = new SiContentList<>();
    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        directory.mkdirs();
    }
}
