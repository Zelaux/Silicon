package ru.vladislav117.silicon.particle;

import org.bukkit.Location;
import ru.vladislav117.silicon.random.SiRandom;

/**
 * "Испускатель" частиц в виде сферы.
 */
public class SiSphereParticleEmitter implements SiParticleEmitter {
    protected SiParticleEmitter particleEmitter;
    protected double radius = 0;
    protected double randomBorderOffset = 0;

    /**
     * Создание нового "испускателя" частиц в виде сферы.
     *
     * @param particleEmitter    "Испускатель" частиц
     * @param radius             Радиус сферы
     * @param randomBorderOffset Сдвиг частицы. При испускании может быть сдвинута по каждой из трёх осей.
     */
    public SiSphereParticleEmitter(SiParticleEmitter particleEmitter, double radius, double randomBorderOffset) {
        this.particleEmitter = particleEmitter;
        this.radius = radius;
        this.randomBorderOffset = randomBorderOffset;
    }

    /**
     * Создание нового "испускателя" частиц в виде сферы.
     *
     * @param particleEmitter "Испускатель" частиц
     * @param radius          Радиус сферы
     */
    public SiSphereParticleEmitter(SiParticleEmitter particleEmitter, double radius) {
        this.particleEmitter = particleEmitter;
        this.radius = radius;
    }

    /**
     * Создание нового "испускателя" частиц в виде сферы с нулевым радиусом.
     *
     * @param particleEmitter "Испускатель" частиц
     */
    public SiSphereParticleEmitter(SiParticleEmitter particleEmitter) {
        this.particleEmitter = particleEmitter;
    }

    /**
     * Получение "испускателя" частиц.
     *
     * @return "Испускатель" частиц.
     */
    public SiParticleEmitter getParticleEmitter() {
        return particleEmitter;
    }

    /**
     * Получение радиуса.
     *
     * @return Радиус.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Получение сдвига частицы.
     *
     * @return Сдвиг частицы.
     */
    public double getRandomBorderOffset() {
        return randomBorderOffset;
    }

    @Override
    public void emit(Location location) {
        if (radius > 0) {
            emit(location, radius, randomBorderOffset);
        } else {
            particleEmitter.emit(location);
        }
    }

    /**
     * Создание сферы в позиции с заданным радиусом и сдвигом.
     *
     * @param location           Позиция сферы
     * @param radius             Радиус сферы
     * @param randomBorderOffset Сдвиг частицы
     */
    public void emit(Location location, double radius, double randomBorderOffset) {
        for (double phi = 0; phi <= Math.PI; phi += Math.PI / (radius * 2.2)) {
            for (double theta = 0; theta <= Math.PI * 2; theta += Math.PI / (radius * 2.2)) {
                double x = radius * Math.cos(theta) * Math.sin(phi);
                double y = radius * Math.cos(phi);
                double z = radius * Math.sin(theta) * Math.sin(phi);

                Location particleLocation = location.clone();
                if (randomBorderOffset != 0)
                    particleLocation.add(x + SiRandom.getDouble(-randomBorderOffset, randomBorderOffset), y + SiRandom.getDouble(-randomBorderOffset, randomBorderOffset), z + SiRandom.getDouble(-randomBorderOffset, randomBorderOffset));
                particleEmitter.emit(particleLocation);
            }
        }
    }
}
