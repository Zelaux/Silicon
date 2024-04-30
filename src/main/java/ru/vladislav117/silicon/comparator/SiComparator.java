package ru.vladislav117.silicon.comparator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Компаратор для проверки некоторых типов.
 */
// TODO: 06.02.2024 Методы и документация
public class SiComparator {
    /**
     * Проверка, является ли материал воздухом или null.
     *
     * @param material Материал
     * @return Является ли материал воздухом или null.
     */
    public static boolean isAir(@Nullable Material material) {
        if (material == null) return true;
        return material.isAir();
    }

    /**
     * Проверка, является ли предмет воздухом или null.
     *
     * @param itemStack Предмет
     * @return Является ли материал воздухом или null.
     */
    public static boolean isAir(@Nullable ItemStack itemStack) {
        if (itemStack == null) return true;
        return isAir(itemStack.getType());
    }
}
