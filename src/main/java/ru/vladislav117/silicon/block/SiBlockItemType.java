package ru.vladislav117.silicon.block;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.materialReplacer.SiMaterialReplacerItemType;

/**
 * Тип блока.
 */
public class SiBlockItemType extends SiMaterialReplacerItemType {
    @Nullable
    protected SiBlockUpdater blockUpdater = null;

    /**
     * Создание нового типа предмета.
     *
     * @param name     Имя типа предмета
     * @param material Материал
     */
    public SiBlockItemType(String name, Material material) {
        super(name, material);
    }

    /**
     * Получение функции обновления блока.
     *
     * @return Функция обновления блока или null.
     */
    @Nullable
    public SiBlockUpdater getBlockUpdater() {
        return blockUpdater;
    }

    /**
     * Установить функцию обновления блока.
     *
     * @param blockUpdater Функция обновления блока.
     * @return Этот же тип блока.
     */
    public SiBlockItemType setBlockUpdater(SiBlockUpdater blockUpdater) {
        this.blockUpdater = blockUpdater;
        return this;
    }
}
