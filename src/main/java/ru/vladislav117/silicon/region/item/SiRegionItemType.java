package ru.vladislav117.silicon.region.item;

import org.bukkit.Material;
import ru.vladislav117.silicon.item.SiItemType;

/**
 * Тип предмета с регионом.
 */
public class SiRegionItemType extends SiItemType {
    /**
     * Создание нового типа предмета с регионом.
     *
     * @param name     Имя типа предмета
     * @param material Материал
     */
    public SiRegionItemType(String name, Material material) {
        super(name, material);
        interactions = new SiRegionItemInteractions();
        defaultTags.put("region_size", 1);
    }
}
