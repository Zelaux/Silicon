package ru.vladislav117.silicon.particle;

import org.bukkit.Location;

/**
 * Абстрактный "испускатель" частиц.
 */
public interface SiParticleEmitter {
    /**
     * Создать частицу(ы) в позиции.
     *
     * @param location Позиция
     */
    void emit(Location location);
}
