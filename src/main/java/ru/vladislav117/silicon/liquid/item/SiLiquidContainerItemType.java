package ru.vladislav117.silicon.liquid.item;

import org.bukkit.Material;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.liquid.SiLiquidTypes;

/**
 * Предмет-контейнер для жидкости.
 */
public class SiLiquidContainerItemType extends SiItemType {
    protected int defaultMaxVolume;

    /**
     * Создание нового типа предмета.
     *
     * @param name     Имя типа предмета
     * @param material Материал
     */
    public SiLiquidContainerItemType(String name, Material material, int defaultMaxVolume) {
        super(name, material);
        usesUuid = true;
        this.defaultMaxVolume = defaultMaxVolume;
        defaultTags.put("liquid_container_volume", defaultMaxVolume);
        defaultTags.put("liquid_type", SiLiquidTypes.vacuum.getName());
        defaultTags.put("liquid_volume", defaultMaxVolume);
        defaultTags.put("liquid_temperature", SiLiquidTypes.vacuum.getDefaultTemperature());
    }
}
