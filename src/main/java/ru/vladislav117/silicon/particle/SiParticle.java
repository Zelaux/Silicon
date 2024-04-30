package ru.vladislav117.silicon.particle;

import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

/**
 * Параметризованная частица.
 */
public class SiParticle {
    protected Particle particle;
    protected Vector offset = new Vector();
    @Nullable
    protected Object data = null;

    /**
     * Создание новой параметризованной частицы.
     *
     * @param particle Частица
     * @param offset   Сдвиг
     * @param data     Данные или null
     */
    public SiParticle(Particle particle, Vector offset, @Nullable Object data) {
        this.particle = particle;
        this.offset = offset;
        this.data = data;
    }

    /**
     * Создание новой параметризованной частицы.
     *
     * @param particle Частица
     * @param offset   Сдвиг
     */
    public SiParticle(Particle particle, Vector offset) {
        this.particle = particle;
        this.offset = offset;
    }

    /**
     * Создание новой параметризованной частицы.
     *
     * @param particle Частица
     * @param data     Данные или null
     */
    public SiParticle(Particle particle, @Nullable Object data) {
        this.particle = particle;
        this.data = data;
    }

    /**
     * Создание новой параметризованной частицы.
     *
     * @param particle Частица
     */
    public SiParticle(Particle particle) {
        this.particle = particle;
    }

    /**
     * Получение частицы.
     *
     * @return Частица.
     */
    public Particle getParticle() {
        return particle;
    }

    /**
     * Установка частицы.
     *
     * @param particle Частица
     * @return Эта же параметризованная частица.
     */
    public SiParticle setParticle(Particle particle) {
        this.particle = particle;
        return this;
    }

    /**
     * Получение сдвига.
     *
     * @return Сдвиг.
     */
    public Vector getOffset() {
        return offset;
    }

    /**
     * Установка сдвига.
     *
     * @param offset Сдвиг
     * @return Эта же параметризованная частица.
     */
    public SiParticle setOffset(Vector offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Получение данных.
     *
     * @return Данные.
     */
    @Nullable
    public Object getData() {
        return data;
    }

    /**
     * Установка данных.
     *
     * @param data Данные
     * @return Эта же параметризованная частица.
     */
    public SiParticle setData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * Преобразование в "испускатель" с этой частицей.
     *
     * @return "Испускатель" с этой частицей.
     */
    public SiSingleParticleEmitter toSingleParticleEmitter() {
        return new SiSingleParticleEmitter(this);
    }
}
