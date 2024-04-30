package ru.vladislav117.silicon.sound;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Абстрактный "испускатель" звука.
 */
public interface SiSoundEmitter {
    /**
     * Получение звука, который будет проигран.
     *
     * @return Звук, который будет проигран.
     */
    SiSound getSound();

    /**
     * Проиграть звук в позиции.
     *
     * @param location Позиция
     * @return Этот же "испускатель".
     */
    default SiSoundEmitter emit(Location location) {
        getSound().play(location);
        return this;
    }

    /**
     * Проиграть звук для игроков.
     *
     * @param players Игроки
     * @return Этот же "испускатель".
     */
    default SiSoundEmitter emit(Player... players) {
        getSound().play(players);
        return this;
    }

    /**
     * Проиграть звук для игроков.
     *
     * @param players Игроки
     * @return Этот же "испускатель".
     */
    default SiSoundEmitter emit(Collection<Player> players) {
        getSound().play(players);
        return this;
    }
}
