package ru.vladislav117.silicon.weapon.item;

import ru.vladislav117.silicon.item.SiItemDisplay;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.bar.SiBarPattern;
import ru.vladislav117.silicon.text.pattern.bar.SiBarPatternStyle;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePattern;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePatternStyle;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePatternStyles;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Отображение магазина боеприпасов.
 */
public class SiAmmoMagazineItemDisplay extends SiItemDisplay.StaticDisplay {
    protected SiFeaturePatternStyle featurePatternStyle = SiFeaturePatternStyles.Colon.white;
    protected SiBarPatternStyle barPatternStyle = null;
    protected int barPatternLength = 32;

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(SiTextLike displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(SiTextLike displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(SiTextLike displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(SiTextLike displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     */
    public SiAmmoMagazineItemDisplay(SiTextLike displayName) {
        super(displayName);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(String displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(String displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(String displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiAmmoMagazineItemDisplay(String displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     */
    public SiAmmoMagazineItemDisplay(String displayName) {
        super(displayName);
    }

    @Override
    public List<SiTextLike> getDescription(SiItemStack itemStack) {
        Integer ammo = itemStack.getTagsManager().getInteger("ammo");
        Integer maxAmmo = itemStack.getTagsManager().getInteger("max_ammo");
        ArrayList<SiTextLike> description = new ArrayList<>();
        if (barPatternStyle != null && maxAmmo != 0)
            description.add(new SiBarPattern(Math.min((double) ammo / (maxAmmo), 1), barPatternLength, barPatternStyle).build());
        description.add(new SiFeaturePattern("Боеприпасы", ammo + "/" + maxAmmo, featurePatternStyle).build());
        description.addAll(super.getDescription(itemStack));
        return description;
    }
}
