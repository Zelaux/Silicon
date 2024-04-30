package ru.vladislav117.silicon.filter.item;

import org.bukkit.inventory.ItemStack;

/**
 * Фильтр предметов, которые могут использоваться как топливо в печи.
 */
public class SiFuelItemFilter extends SiItemFilter {
    @Override
    public boolean testItemStack(ItemStack itemStack) {
        return itemStack.getType().isFuel();
    }
}
