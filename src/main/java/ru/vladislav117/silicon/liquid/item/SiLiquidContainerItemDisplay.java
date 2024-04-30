package ru.vladislav117.silicon.liquid.item;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.item.SiItemDisplay;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.liquid.SiLiquids;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePattern;
import ru.vladislav117.silicon.text.structure.SiLinedText;
import ru.vladislav117.silicon.utils.SiNumberUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Отображение жидкостного контейнера.
 */
public class SiLiquidContainerItemDisplay extends SiItemDisplay.StaticDisplay {
    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(SiTextLike displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(SiTextLike displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(SiTextLike displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(SiTextLike displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и пустого описания.
     *
     * @param displayName Имя
     */
    public SiLiquidContainerItemDisplay(SiTextLike displayName) {
        super(displayName);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(String displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(String displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(String displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и описания.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiLiquidContainerItemDisplay(String displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание новых имени и пустого описания.
     *
     * @param displayName Имя
     */
    public SiLiquidContainerItemDisplay(String displayName) {
        super(displayName);
    }

    @Override
    public SiTextLike getDisplayName(SiItemStack itemStack) {
        SiLiquidContainer liquidContainer = new SiLiquidContainer(itemStack);
        return SiText.container().addText(super.getDisplayName(itemStack)).addString(" [", SiPalette.Interface.gray).addText(liquidContainer.getLiquidStack().getLiquidType().getDisplayName()).addString("]", SiPalette.Interface.gray);
    }

    @Override
    public List<SiTextLike> getDescription(SiItemStack itemStack) {
        SiLiquidContainer liquidContainer = new SiLiquidContainer(itemStack);
        SiFeaturePattern liquidTypeText = new SiFeaturePattern("Содержимое", SiText.container().addText(liquidContainer.getLiquidStack().getLiquidType().getDisplayName()));
        SiFeaturePattern liquidVolumeText = new SiFeaturePattern("Объём", liquidContainer.getLiquidStack().getVolume() + "/" + liquidContainer.getMaxVolume() + " мв");
        SiFeaturePattern liquidTemperatureText = new SiFeaturePattern("Температура", SiNumberUtils.doubleToString2Digits(liquidContainer.getLiquidStack().getTemperature()) + SiLiquids.celsiusDegrees);
        ArrayList<SiTextLike> description = new ArrayList<>(super.getDescription(itemStack));
        description.add(liquidTypeText.build());
        description.add(liquidVolumeText.build());
        description.add(liquidTemperatureText.build());
        return description;
    }

    @Override
    public @Nullable SiColor getColor(SiItemStack itemStack) {
        SiLiquidContainer liquidContainer = new SiLiquidContainer(itemStack);
        return liquidContainer.getLiquidStack().getLiquidType().getColor();
    }
}
