package ru.vladislav117.silicon.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Параметризованный звук.
 */
public class SiSound {
    protected Sound sound;
    protected double volume = 1;
    protected double pitch = 1;
    protected SoundCategory category = null;

    /**
     * Создание нового параметризованного звука.
     *
     * @param sound    Звук
     * @param volume   Громкость
     * @param pitch    Высота
     * @param category Категория звука
     */
    public SiSound(Sound sound, double volume, double pitch, SoundCategory category) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.category = category;
    }

    /**
     * Создание нового параметризованного звука без категории.
     *
     * @param sound  Звук
     * @param volume Громкость
     * @param pitch  Высота
     */
    public SiSound(Sound sound, double volume, double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Создание нового параметризованного звука без категории со стандартной высотой.
     *
     * @param sound  Звук
     * @param volume Громкость
     */
    public SiSound(Sound sound, double volume) {
        this.sound = sound;
        this.volume = volume;
    }

    /**
     * Создание нового параметризованного звука без категории со стандартными высотой и громкостью.
     *
     * @param sound Звук
     */
    public SiSound(Sound sound) {
        this.sound = sound;
    }

    /**
     * Получение звука.
     *
     * @return Звук.
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Установка звука.
     *
     * @param sound Звук
     * @return Этот же параметризованный звук.
     */
    public SiSound setSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Получение громкости.
     *
     * @return Громкость.
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Установка громкости.
     *
     * @param volume Громкость
     * @return Этот же параметризованный звук.
     */
    public SiSound setVolume(double volume) {
        this.volume = volume;
        return this;
    }

    /**
     * Получение высоты.
     *
     * @return Высота.
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * Установка высоты.
     *
     * @param pitch Высота звука
     * @return Этот же параметризованный звук.
     */
    public SiSound setPitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    /**
     * Проиграть звук в позиции.
     *
     * @param location Позиция
     * @return Этот же параметризованный звук.
     */
    public SiSound play(Location location) {
        if (category == null) {
            location.getWorld().playSound(location, sound, (float) volume, (float) pitch);
        } else {
            location.getWorld().playSound(location, sound, category, (float) volume, (float) pitch);
        }
        return this;
    }

    /**
     * Проиграть звук для игроков.
     *
     * @param players Игроки
     * @return Этот же параметризованный звук.
     */
    public SiSound play(Player... players) {
        if (category == null) {
            for (Player player : players)
                player.playSound(player.getLocation().toCenterLocation(), sound, (float) volume, (float) pitch);
        } else {
            for (Player player : players)
                player.playSound(player.getLocation().toCenterLocation(), sound, category, (float) volume, (float) pitch);
        }
        return this;
    }

    /**
     * Проиграть звук для игроков.
     *
     * @param players Игроки
     * @return Этот же параметризованный звук.
     */
    public SiSound play(Collection<Player> players) {
        if (category == null) {
            for (Player player : players)
                player.playSound(player.getLocation().toCenterLocation(), sound, (float) volume, (float) pitch);
        } else {
            for (Player player : players)
                player.playSound(player.getLocation().toCenterLocation(), sound, category, (float) volume, (float) pitch);
        }
        return this;
    }

    /**
     * Создание "испускателя" звука на основе этого параметризованного звука.
     *
     * @return "Испускатель" звука на основе этого параметризованного звука.
     */
    public SiSingleSoundEmitter toSingleSoundEmitter() {
        return new SiSingleSoundEmitter(this);
    }
}
