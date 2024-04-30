package ru.vladislav117.silicon.materialReplacer;

import org.bukkit.Material;
import ru.vladislav117.silicon.item.SiItemType;

/**
 * Заменитель материала.
 */
public class SiMaterialReplacerItemType extends SiItemType {
    /**
     * Создание нового типа предмета.
     *
     * @param name     Имя типа предмета
     * @param material Материал
     */
    public SiMaterialReplacerItemType(String name, Material material) {
        super(name, material);
        SiMaterialsReplacers.add(this);
    }
}
