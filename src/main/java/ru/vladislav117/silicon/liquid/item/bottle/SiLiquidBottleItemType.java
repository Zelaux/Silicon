package ru.vladislav117.silicon.liquid.item.bottle;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.liquid.SiLiquidStack;
import ru.vladislav117.silicon.liquid.SiLiquidTypes;
import ru.vladislav117.silicon.liquid.SiLiquids;
import ru.vladislav117.silicon.liquid.item.SiLiquidContainerItemDisplay;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.style.SiStyleLike;

import java.util.HashMap;

/**
 * Тип предмета "бутылка для жидкостей".
 */
public class SiLiquidBottleItemType extends SiItemType {
    /**
     * Создание новой бутылки для жидкостей.
     *
     * @param name Имя бутылки
     */
    public SiLiquidBottleItemType(String name, SiTextLike displayName) {
        super(name, Material.POTION);
        defaultTags.put("liquid_container_volume", SiLiquids.bottleVolume);
        defaultTags.put("liquid_type", SiLiquidTypes.vacuum.getName());
        defaultTags.put("liquid_volume", SiLiquids.bottleVolume);
        defaultTags.put("liquid_temperature", SiLiquidTypes.vacuum.getDefaultTemperature());
        display = new SiLiquidContainerItemDisplay(displayName);
        interactions = new SiLiquidBottleItemInteractions();
        flags.add(ItemFlag.HIDE_ITEM_SPECIFICS);
    }

    /**
     * Создание новой бутылки для жидкостей.
     *
     * @param name Имя бутылки
     */
    public SiLiquidBottleItemType(String name, String displayName, SiStyleLike displayNameStyle) {
        this(name, SiText.string(displayName, displayNameStyle));
    }

    /**
     * Создание новой бутылки для жидкостей.
     *
     * @param name Имя бутылки
     */
    public SiLiquidBottleItemType(String name, String displayName) {
        this(name, SiText.string(displayName));
    }

    @Override
    public SiItemStack buildItemStack(int amount, HashMap<String, Object> tags) {
        SiItemStack itemStack = super.buildItemStack(amount, tags);
        SiLiquidStack liquidStack = SiLiquidStack.buildFromLiquidContainer(itemStack);
        if (liquidStack == null) return itemStack;
        itemStack.setPotionColor(liquidStack.getLiquidType().getColor());
        return itemStack;
    }
}
