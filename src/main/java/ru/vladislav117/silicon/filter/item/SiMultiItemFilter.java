package ru.vladislav117.silicon.filter.item;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Составной фильтр предметов.
 */
public class SiMultiItemFilter extends SiItemFilter {
    protected ArrayList<SiItemFilter> filters = new ArrayList<>();

    /**
     * Создание нового составного фильтра предметов.
     *
     * @param filters Фильтры
     */
    public SiMultiItemFilter(ArrayList<SiItemFilter> filters) {
        this.filters.addAll(filters);
    }

    /**
     * Создание нового составного фильтра предметов.
     *
     * @param filters Фильтры
     */
    public SiMultiItemFilter(SiItemFilter[] filters) {
        this.filters.addAll(List.of(filters));
    }

    /**
     * Создание нового составного фильтра предметов.
     *
     * @param filter Фильтр
     */
    public SiMultiItemFilter(SiItemFilter filter) {
        filters.add(filter);
    }

    /**
     * Создание нового составного фильтра предметов.
     */
    public SiMultiItemFilter() {
    }

    /**
     * Добавление фильтра в составной фильтр.
     *
     * @param filter Фильтр
     * @return Этот же составной фильтр.
     */
    public SiMultiItemFilter addFilter(SiItemFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean testItemStack(ItemStack itemStack) {
        for (SiItemFilter filter : filters) {
            if (filter.isSuitable(itemStack)) return true;
        }
        return false;
    }
}
