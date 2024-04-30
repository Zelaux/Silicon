package ru.vladislav117.silicon.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import ru.vladislav117.silicon.random.SiRandom;
import ru.vladislav117.silicon.random.SiRandomGenerator;

import java.util.ArrayList;

/**
 * "Испускатель" случайных частиц.
 */
public class SiRandomParticleEmitter implements SiParticleEmitter {
    protected SiRandomGenerator randomGenerator = null;
    protected ArrayList<SiParticleEmitter> emitters = new ArrayList<>();

    /**
     * Создание "испускателя" случайных частиц с пустым набором частиц.
     * <p>
     * ВАЖНО! Если не будет добавлено ни одной частицы, то будет вызвано исключение.
     *
     * @param randomGenerator Генератор случайных чисел, который будет выбирать частицы
     */
    public SiRandomParticleEmitter(SiRandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    /**
     * Создание "испускателя" случайных частиц с пустым набором частиц и стандартным генератором случайных чисел.
     * <p>
     * ВАЖНО! Если не будет добавлено ни одной частицы, то будет вызвано исключение.
     */
    public SiRandomParticleEmitter() {
    }

    /**
     * Добавление частицы.
     *
     * @param particleEmitter "Испускатель" частиц
     * @return Этот же "испускатель" случайных частиц.
     */
    public SiRandomParticleEmitter add(SiParticleEmitter particleEmitter) {
        emitters.add(particleEmitter);
        return this;
    }

    /**
     * Добавление частицы.
     *
     * @param particle Параметризованная частица
     * @return Этот же "испускатель" случайных частиц.
     */
    public SiRandomParticleEmitter add(SiParticle particle) {
        emitters.add(particle.toSingleParticleEmitter());
        return this;
    }

    /**
     * Добавление частицы.
     *
     * @param particle Частица
     * @return Этот же "испускатель" случайных частиц.
     */
    public SiRandomParticleEmitter add(Particle particle) {
        emitters.add(new SiSingleParticleEmitter(particle));
        return this;
    }

    @Override
    public void emit(Location location) {
        if (randomGenerator != null) {
            randomGenerator.choice(emitters).emit(location);
            return;
        }
        SiRandom.choice(emitters).emit(location);
    }
}