package ru.vladislav117.silicon.filter.item;

import org.bukkit.inventory.ItemStack;

/**
 * Абстрактный фильтр предметов.
 */
public abstract class SiItemFilter {
    protected boolean inverted = false;

    /**
     * Проверка предмета без учёта инверсии фильтра.
     *
     * @param itemStack Предмет
     * @return Подходит ли предмет.
     */
    public abstract boolean testItemStack(ItemStack itemStack);

    /**
     * Проверка предмета с учётом инверсии фильтра.
     *
     * @param itemStack Предмет
     * @return Подходит ли предмет.
     */
    public boolean isSuitable(ItemStack itemStack) {
        boolean suitable = testItemStack(itemStack);
        if (inverted) return !suitable;
        return suitable;
    }
}
