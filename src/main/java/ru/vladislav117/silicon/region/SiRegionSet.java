package ru.vladislav117.silicon.region;

import org.bukkit.entity.Player;
import ru.vladislav117.silicon.function.SiHandlerFunction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Набор регионов.
 */
public class SiRegionSet {
    protected ArrayList<SiRegion> regions = new ArrayList<>();

    /**
     * Создание набора регионов.
     *
     * @param regions Регионы
     */
    public SiRegionSet(Collection<SiRegion> regions) {
        this.regions.addAll(regions);
    }

    /**
     * Проверка, является ли игрок членов всех регионов.
     *
     * @param player Игрок
     * @return Является ли игрок членов всех регионов.
     */
    public boolean isMember(String player) {
        for (SiRegion region : regions) {
            if (!region.isMember(player)) return false;
        }
        return true;
    }

    /**
     * Проверка, является ли игрок членов всех регионов.
     *
     * @param player Игрок
     * @return Является ли игрок членов всех регионов.
     */
    public boolean isMember(Player player) {
        for (SiRegion region : regions) {
            if (!region.isMember(player)) return false;
        }
        return true;
    }

    /**
     * Проверка, является ли игрок владельцем всех регионов.
     *
     * @param player Игрок
     * @return Является ли игрок владельцем всех регионов.
     */
    public boolean isOwner(String player) {
        for (SiRegion region : regions) {
            if (!region.isOwner(player)) return false;
        }
        return true;
    }

    /**
     * Проверка, является ли игрок владельцем всех регионов.
     *
     * @param player Игрок
     * @return Является ли игрок владельцем всех регионов.
     */
    public boolean isOwner(Player player) {
        for (SiRegion region : regions) {
            if (!region.isOwner(player)) return false;
        }
        return true;
    }

    /**
     * Получение флага во всех регионах. Если хотя бы один флаг выключен, но метод вернёт false.
     *
     * @param flag Флаг
     * @return Значение флага во всех регионах.
     */
    public boolean getFlag(SiRegionFlag flag) {
        for (SiRegion region : regions) {
            if (!region.getFlag(flag)) return false;
        }
        return true;
    }

    /**
     * Получение флага для игрока во всех регионах. Если хотя бы один флаг выключен, но метод вернёт false.
     *
     * @param player Игрок
     * @param flag   Флаг
     * @return Значение флага во всех регионах.
     */
    public boolean getFlag(String player, SiRegionFlag flag) {
        for (SiRegion region : regions) {
            if (!region.getFlag(player, flag)) return false;
        }
        return true;
    }

    /**
     * Получение флага для игрока во всех регионах. Если хотя бы один флаг выключен, но метод вернёт false.
     *
     * @param player Игрок
     * @param flag   Флаг
     * @return Значение флага во всех регионах.
     */
    public boolean getFlag(Player player, SiRegionFlag flag) {
        for (SiRegion region : regions) {
            if (!region.getFlag(player, flag)) return false;
        }
        return true;
    }

    /**
     * Обработка каждого региона.
     *
     * @param function Функция для обработки
     * @return Этот же набор регионов.
     */
    public SiRegionSet forEach(SiHandlerFunction<SiRegion> function) {
        regions.forEach(function::handle);
        return this;
    }

    /**
     * Получение всех регионов.
     *
     * @return Все регионы.
     */
    public ArrayList<SiRegion> getRegions() {
        return regions;
    }
}
