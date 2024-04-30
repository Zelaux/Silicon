package ru.vladislav117.silicon.shop;

import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.file.SiFile;

/**
 * Класс-контейнер для магазинов.
 */
public final class SiShops {
    public static final SiFile directory = Silicon.getDirectory().getChild("shops");
    public static final SiContentList<SiShop> all = new SiContentList<>();
    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        directory.mkdirs();
    }
}
