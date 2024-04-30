package ru.vladislav117.silicon.region;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.icon.SiIcon;
import ru.vladislav117.silicon.icon.SiIcons;
import ru.vladislav117.silicon.node.SiNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Класс-контейнер для регионов.
 */
public final class SiRegions {
    public static SiFile directory;
    static HashSet<String> regionsNames = new HashSet<>();
    static HashMap<String, SiRegion> regions = new HashMap<>();
    static HashMap<String, ArrayList<SiRegion>> worldRegions = new HashMap<>();

    /**
     * Добавление региона.
     *
     * @param region Регион.
     */
    public static void addRegion(SiRegion region) {
        String worldName = region.getLocation().getWorld().getName();
        if (!worldRegions.containsKey(worldName)) {
            worldRegions.put(worldName, new ArrayList<>());
        }
        regions.put(region.getName(), region);
        regionsNames.add(region.getName());
        worldRegions.get(worldName).add(region);
    }

    /**
     * Получение имени для региона.
     *
     * @param playerName Изначальное имя игрока
     * @return Незанятое имя региона.
     */
    public static String giveName(String playerName) {
        int i = -1;
        while (true) {
            i += 1;
            if (regionsNames.contains(playerName + i)) continue;
            return playerName + i;
        }
    }

    /**
     * Получение регионов в позиции.
     *
     * @param location Позиция
     * @return Регионы.
     */
    public static SiRegionSet getRegionsAt(Location location) {
        String worldName = location.getWorld().getName();
        if (!worldRegions.containsKey(worldName)) {
            return new SiRegionSet(new ArrayList<>());
        }
        ArrayList<SiRegion> foundRegions = new ArrayList<>();
        ArrayList<SiRegion> allWorldRegions = worldRegions.get(worldName);
        for (SiRegion region : allWorldRegions) {
            if (region.isInRegion(location)) foundRegions.add(region);
        }
        return new SiRegionSet(foundRegions);
    }

    /**
     * Получение регионов в мире.
     *
     * @param world Мир
     * @return Регионы.
     */
    public static SiRegionSet getRegionsAt(World world) {
        String worldName = world.getName();
        if (!worldRegions.containsKey(worldName)) {
            return new SiRegionSet(new ArrayList<>());
        }
        return new SiRegionSet(worldRegions.get(worldName));
    }

    /**
     * Получение региона по имени.
     *
     * @param name Имя региона
     * @return Регион или null.
     */
    @Nullable
    public static SiRegion getRegion(String name) {
        return regions.get(name);
    }

    /**
     * Получение имён всех регионов.
     *
     * @return Имена всех регионов.
     */
    public static HashSet<String> getRegionNames() {
        return regionsNames;
    }

    /**
     * Удаление региона.
     *
     * @param region Регион, который будет удалён.
     */
    public static void remove(SiRegion region) {
        regions.remove(region.getName());
        regionsNames.remove(region.getName());
        worldRegions.get(region.getLocation().getWorld().getName()).remove(region);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        directory = Silicon.getDirectory().getChild("regions").mkdirs();
        SiEvents.addHandler(SiBuiltinEvents.SecondaryLoadEndEvent.class, event -> {
            for (SiNode regionNode : directory.readAllJsonNodeFiles()) {
                SiRegion.load(regionNode);
            }
        });

        SiIcons.loaders.addPrimaryLoader(() -> {
            SiRegion.removeRegionIcon = new SiIcon("remove_region");
        });
    }
}
