package ru.vladislav117.silicon.item.category;

import org.bukkit.Material;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.item.SiItemTypes;
import ru.vladislav117.silicon.text.SiText;

/**
 * Класс-контейнер для категорий предметов.
 */
public class SiItemCategories {
    public static final SiContentList<SiItemCategory> all = new SiContentList<>();

    public static SiItemCategory everything;

    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            everything = new SiItemCategory("everything", SiPalette.Interface.white, SiText.string("Все предметы")).setDisplays(false).setItemStack(Material.CHEST);
        });

        loaders.addSecondaryLoader(() -> {
            for (SiItemType itemType : SiItemTypes.all.getAll()) {
                for (SiItemCategory category : itemType.getCategories()) category.addItemType(itemType);
            }
        });
    }
}
