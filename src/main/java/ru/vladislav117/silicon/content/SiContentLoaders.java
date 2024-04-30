package ru.vladislav117.silicon.content;

import ru.vladislav117.silicon.function.SiFunction;

import java.util.ArrayList;

/**
 * Загрузчики контента.
 */
public class SiContentLoaders {
    protected ArrayList<SiFunction> primaryLoaders = new ArrayList<>();
    protected ArrayList<SiFunction> secondaryLoaders = new ArrayList<>();

    /**
     * Добавление первичного загрузчика в очередь.
     *
     * @param function Функция-загрузчик
     * @return Этот же загрузчик контента.
     */
    public SiContentLoaders addPrimaryLoader(SiFunction function) {
        primaryLoaders.add(function);
        return this;
    }

    /**
     * Добавление вторичного загрузчика в очередь.
     *
     * @param function Функция-загрузчик
     * @return Этот же загрузчик контента.
     */
    public SiContentLoaders addSecondaryLoader(SiFunction function) {
        secondaryLoaders.add(function);
        return this;
    }

    /**
     * Вызов первичных загрузчиков.
     *
     * @return Этот же загрузчик контента.
     */
    public SiContentLoaders primaryLoad() {
        primaryLoaders.forEach(SiFunction::call);
        return this;
    }

    /**
     * Вызов вторичных загрузчиков.
     *
     * @return Этот же загрузчик контента.
     */
    public SiContentLoaders secondaryLoad() {
        secondaryLoaders.forEach(SiFunction::call);
        return this;
    }
}
