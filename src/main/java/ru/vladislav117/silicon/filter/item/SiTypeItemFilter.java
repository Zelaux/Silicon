package ru.vladislav117.silicon.filter.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;

/**
 * Фильтр предметов по типу.
 * <p>
 * ВАЖНО! Фильтр учитывает наличие кастомного типа предмета.
 */
public class SiTypeItemFilter extends SiItemFilter {
    protected Material material = null;
    protected SiItemType itemType = null;

    /**
     * Создание фильтра предметов по типу.
     *
     * @param material Материал
     */
    public SiTypeItemFilter(Material material) {
        this.material = material;
    }

    /**
     * Создание фильтра предметов по типу.
     *
     * @param itemType Кастомный тип
     */
    public SiTypeItemFilter(SiItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean testItemStack(ItemStack itemStack) {
        SiItemType type = new SiItemStack(itemStack).getItemType();
        if (material != null) {
            return type.isUnknown() && itemStack.getType().equals(material);
        } else if (itemType != null) {
            return type.equals(itemType);
        }
        return false;
    }
}
