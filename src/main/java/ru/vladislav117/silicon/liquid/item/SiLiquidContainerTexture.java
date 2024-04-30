package ru.vladislav117.silicon.liquid.item;

import ru.vladislav117.silicon.item.SiItemTexture;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.liquid.SiLiquidTypes;

import java.util.List;

/**
 * Текстура для жидкостного контейнера.
 */
public class SiLiquidContainerTexture extends SiItemTexture.FeatureTexture {
    /**
     * Создание текстуры предмета.
     *
     * @param itemType Тип предмета
     */
    public SiLiquidContainerTexture(SiItemType itemType) {
        super(itemType, "liquid_type", SiLiquidTypes.vacuum.getName(), List.of(SiLiquidTypes.all.getAllNames()));
    }
}
