package ru.vladislav117.silicon.particle;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * "Испускатель" частиц в виде линии.
 */
public class SiLineParticleEmitter implements SiParticleEmitter {
    protected SiParticleEmitter emitter;
    protected Vector direction = null;
    protected Double length = null;
    protected Location end = null;

    /**
     * Вычислить направление от одного вектора к другому.
     *
     * @param start Начало
     * @param end   Конец
     * @return Направление
     */
    static Vector direction(Location start, Location end) {
        return end.clone().toVector().subtract(start.clone().toVector()).normalize();
    }

    /**
     * Создание нового "испускателя" частиц в виде линии.
     *
     * @param end     Конец линии
     * @param emitter "Испускатель" частиц
     */
    public SiLineParticleEmitter(Location end, SiParticleEmitter emitter) {
        this.emitter = emitter;
        this.end = end;
    }

    /**
     * Создание нового "испускателя" частиц в виде линии.
     *
     * @param direction Направление линии
     * @param length    Длина линии
     * @param emitter   "Испускатель" частиц
     */
    public SiLineParticleEmitter(Vector direction, Double length, SiParticleEmitter emitter) {
        this.emitter = emitter;
        this.direction = direction;
        this.length = length;
    }

    /**
     * Создание нового "испускателя" частиц в виде линии. Так как не заданы параметры, линия будет нулевой длины.
     *
     * @param emitter "Испускатель" частиц
     */
    public SiLineParticleEmitter(SiParticleEmitter emitter) {
        this.emitter = emitter;
    }


    @Override
    public void emit(Location location) {
        if (end != null) {
            emit(location, end);
        } else if (direction != null) {
            emit(location, direction, length);
        } else {
            emitter.emit(location);
        }
    }

    /**
     * Создать линию из начала в направлении и с указанной длиной.
     *
     * @param start     Начало
     * @param direction Направление
     * @param length    Длина
     */
    public void emit(Location start, Vector direction, double length) {
        double progress = 0;
        while (progress < length) {
            emitter.emit(start.clone().add(direction.clone().multiply(progress)));
            progress += 0.1;
        }
    }

    /**
     * Создать линию из начала в конец.
     *
     * @param start Начало
     * @param end   Конец
     */
    public void emit(Location start, Location end) {
        Vector direction = direction(start, end);
        double length = start.distance(end);
        double progress = 0;
        while (progress < length) {
            emitter.emit(start.clone().add(direction.clone().multiply(progress)));
            progress += 0.1;
        }
    }
}
