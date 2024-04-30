package ru.vladislav117.silicon.sound;

import org.bukkit.Sound;

/**
 * "Испускатель" конкретного звука.
 */
public class SiSingleSoundEmitter implements SiSoundEmitter {
    protected SiSound sound;

    /**
     * Создание нового "испускателя" конкретного звука.
     *
     * @param sound Параметризованный звук
     */
    public SiSingleSoundEmitter(SiSound sound) {
        this.sound = sound;
    }

    /**
     * Создание нового "испускателя" конкретного звука.
     *
     * @param sound Звук
     */
    public SiSingleSoundEmitter(Sound sound) {
        this.sound = new SiSound(sound);
    }

    @Override
    public SiSound getSound() {
        return sound;
    }

    /**
     * Установить параметризованный звук.
     *
     * @param sound Параметризованный звук
     * @return Этот же "испускатель".
     */
    public SiSingleSoundEmitter setSound(SiSound sound) {
        this.sound = sound;
        return this;
    }
}
