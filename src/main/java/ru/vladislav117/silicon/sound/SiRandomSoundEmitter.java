package ru.vladislav117.silicon.sound;

import org.bukkit.Sound;
import ru.vladislav117.silicon.random.SiRandom;
import ru.vladislav117.silicon.random.SiRandomGenerator;

import java.util.ArrayList;

/**
 * "Испускатель" случайного звука.
 */
public class SiRandomSoundEmitter implements SiSoundEmitter {
    protected SiRandomGenerator randomGenerator = null;
    protected ArrayList<SiSoundEmitter> emitters = new ArrayList<>();

    /**
     * Создание "испускателя" случайного звука с пустым набором звуков.
     * <p>
     * ВАЖНО! Если не будет добавлено ни одного звука, то будет вызвано исключение.
     *
     * @param randomGenerator Генератор случайных чисел, который будет выбирать звук
     */
    public SiRandomSoundEmitter(SiRandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    /**
     * Создание "испускателя" случайного звука с пустым набором звуков и стандартным генератором случайных чисел.
     * <p>
     * ВАЖНО! Если не будет добавлено ни одного звука, то будет вызвано исключение.
     */
    public SiRandomSoundEmitter() {
    }

    /**
     * Добавление звука.
     *
     * @param soundEmitter "Испускатель" звука
     * @return Этот же "испускатель" случайного звука.
     */
    public SiRandomSoundEmitter add(SiSoundEmitter soundEmitter) {
        emitters.add(soundEmitter);
        return this;
    }

    /**
     * Добавление звука.
     *
     * @param sound Параметризованный звук
     * @return Этот же "испускатель" случайного звука.
     */
    public SiRandomSoundEmitter add(SiSound sound) {
        emitters.add(sound.toSingleSoundEmitter());
        return this;
    }

    /**
     * Добавление звука.
     *
     * @param sound Звук
     * @return Этот же "испускатель" случайного звука.
     */
    public SiRandomSoundEmitter add(Sound sound) {
        emitters.add(new SiSingleSoundEmitter(sound));
        return this;
    }

    @Override
    public SiSound getSound() {
        if (randomGenerator != null) return randomGenerator.choice(emitters).getSound();
        return SiRandom.choice(emitters).getSound();
    }
}
