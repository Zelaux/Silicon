package ru.vladislav117.silicon.region.item;

import ru.vladislav117.silicon.item.SiItemDisplay;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.bar.SiBarPattern;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePattern;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Отображение предмета с регионом.
 */
public class SiRegionItemDisplay extends SiItemDisplay.StaticDisplay {
    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(SiTextLike displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(SiTextLike displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(SiTextLike displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(SiTextLike displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     */
    public SiRegionItemDisplay(SiTextLike displayName) {
        super(displayName);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(String displayName, Collection<SiTextLike> description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(String displayName, SiTextLike description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(String displayName, SiLinedText description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     * @param description Описание
     */
    public SiRegionItemDisplay(String displayName, String description) {
        super(displayName, description);
    }

    /**
     * Создание нового отображения предмета с регионом.
     *
     * @param displayName Имя
     */
    public SiRegionItemDisplay(String displayName) {
        super(displayName);
    }

    @Override
    public List<SiTextLike> getDescription(SiItemStack itemStack) {
        int radius = itemStack.getTagsManager().getInteger("region_size", 1);
        ArrayList<SiTextLike> desc = new ArrayList<>();
        desc.add(new SiFeaturePattern("Радиус региона", radius).build());
        desc.add(SiText.string("(Радиус региона - половина стороны квадрата региона)"));
        desc.addAll(super.getDescription(itemStack));
        return desc;
    }
}
