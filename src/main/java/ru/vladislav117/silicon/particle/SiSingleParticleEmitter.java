package ru.vladislav117.silicon.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

/**
 * "Испускатель" конкретной частицы.
 */
public class SiSingleParticleEmitter implements SiParticleEmitter {
    protected SiParticle particle;

    /**
     * Создание нового "испускателя" конкретной частицы.
     *
     * @param particle Параметризованная частица
     */
    public SiSingleParticleEmitter(SiParticle particle) {
        this.particle = particle;
    }

    /**
     * Создание нового "испускателя" конкретной частицы.
     *
     * @param particle Частица
     * @param offset   Сдвиг
     * @param data     Данные или null
     */
    public SiSingleParticleEmitter(Particle particle, Vector offset, @Nullable Object data) {
        this.particle = new SiParticle(particle, offset, data);
    }

    /**
     * Создание нового "испускателя" конкретной частицы.
     *
     * @param particle Частица
     * @param offset   Сдвиг
     */
    public SiSingleParticleEmitter(Particle particle, Vector offset) {
        this.particle = new SiParticle(particle, offset);
    }

    /**
     * Создание нового "испускателя" конкретной частицы.
     *
     * @param particle Частица
     * @param data     Данные или null
     */
    public SiSingleParticleEmitter(Particle particle, @Nullable Object data) {
        this.particle = new SiParticle(particle, data);
    }

    /**
     * Создание нового "испускателя" конкретной частицы.
     *
     * @param particle Частица
     */
    public SiSingleParticleEmitter(Particle particle) {
        this.particle = new SiParticle(particle);
    }

    /**
     * Получение параметризованной частицы.
     *
     * @return Параметризованная частица.
     */
    public SiParticle getParticle() {
        return particle;
    }

    /**
     * Установка параметризованной частицы.
     *
     * @param particle Параметризованная частица
     * @return Этот же "испускатель".
     */
    public SiSingleParticleEmitter setParticle(SiParticle particle) {
        this.particle = particle;
        return this;
    }

    @Override
    public void emit(Location location) {
        location.getWorld().spawnParticle(particle.getParticle(), location, 1, particle.getOffset().getX(), particle.getOffset().getY(), particle.getOffset().getZ(), particle.getData());
    }
}
