package ru.vladislav117.silicon.weapon.item;

import ru.vladislav117.silicon.item.SiItemDisplay;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePattern;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Создание отображения предмета для оружия.
 */
public class SiWeaponItemTypeDisplay extends SiItemDisplay.StaticDisplay {
    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(SiTextLike displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(SiTextLike displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(SiTextLike displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(SiTextLike displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     */
    public SiWeaponItemTypeDisplay(SiTextLike displayName) {
        super(displayName);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(String displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(String displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(String displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     * @param description Описание
     */
    public SiWeaponItemTypeDisplay(String displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание отображения.
     *
     * @param displayName Отображаемое имя
     */
    public SiWeaponItemTypeDisplay(String displayName) {
        super(displayName);
    }

    @Override
    public List<SiTextLike> getDescription(SiItemStack itemStack) {
        ArrayList<SiTextLike> description = new ArrayList<>();
        String weaponOwner = itemStack.getTagsManager().getString("weapon_owner", "");
        if (!weaponOwner.equals("")) {
            description.add(new SiFeaturePattern("Владелец", weaponOwner).build());
        }
        description.addAll(super.getDescription(itemStack));
        return description;
    }
}
