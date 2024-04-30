package ru.vladislav117.silicon.area;

import ru.vladislav117.silicon.ticker.SiServerLoadTicker;

import java.util.ArrayList;

/**
 * Класс-контейнер для зон.
 */
public class SiAreas {
    static ArrayList<SiArea> areasToAdd = new ArrayList<>();
    static ArrayList<SiArea> areas = new ArrayList<>();

    /**
     * Добавление зоны.
     *
     * @param area Зона
     */
    public static void add(SiArea area) {
        areasToAdd.add(area);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        new SiServerLoadTicker(ticker -> {
            for (SiArea area : areasToAdd) {
                areas.add(area);
                area.getType().getInteractions().spawn(area);
            }
            areasToAdd.clear();
            ArrayList<SiArea> areasToRemove = new ArrayList<>();
            for (SiArea area : areas) {
                area.update();
                if (area.getLifetime() <= 0) {
                    area.getType().getInteractions().disappear(area);
                    areasToRemove.add(area);
                }
            }
            for (SiArea area : areasToRemove) areas.remove(area);
        });
    }
}
