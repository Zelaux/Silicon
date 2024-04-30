package ru.vladislav117.silicon.achievement;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;

import java.util.Iterator;
import java.util.Locale;

/**
 * Класс-контейнер для ачивок.
 */
public final class SiAchievements {
    public static final SiContentList<SiAchievement> all = new SiContentList<>();

    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
        });

        loaders.addSecondaryLoader(() -> {
            Iterator<Advancement> advancementIterator = Bukkit.advancementIterator();
            while (advancementIterator.hasNext()) {
                while (advancementIterator.hasNext()) {
                    Advancement advancement = advancementIterator.next();
                    if (advancement.getKey().getNamespace().equals(Silicon.getPlugin().getName().toLowerCase(Locale.ROOT))) {
                        Bukkit.getUnsafe().removeAdvancement(advancement.getKey());
                    }
                }
            }
            Bukkit.reloadData();
            for (SiAchievement achievement : all.getAll()) {
                if (achievement.getParent() == null) achievement.buildTree();
            }
        });
    }
}
