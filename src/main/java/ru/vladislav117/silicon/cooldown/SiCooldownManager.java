package ru.vladislav117.silicon.cooldown;

import ru.vladislav117.silicon.function.SiFunction;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.HashMap;

/**
 * Менеджер кулдаунов. Благодаря абстракции может работать с разными типами кулдаунов.
 */
public abstract class SiCooldownManager {
    protected HashMap<String, Long> cooldowns = new HashMap<>();

    /**
     * Получение текущего глобального времени.
     *
     * @return Текущее глобальное время.
     */
    public abstract long getCurrentTime();

    /**
     * Получение времени окончания кулдауна.
     *
     * @param name Имя записи кулдауна
     * @return Время окончания кулдауна.
     */
    public long getEndTime(String name) {
        return cooldowns.getOrDefault(name, 0L);
    }

    /**
     * Проверка, имеется ли кулдаун у записи по имени.
     *
     * @param name Имя записи кулдауна
     * @return имеется ли кулдаун у записи.
     */
    public boolean hasCooldown(String name) {
        return getEndTime(name) > getCurrentTime();
    }

    /**
     * Получение остатка времени кулдауна.
     *
     * @param name Имя записи кулдауна
     * @return Остаток времени кулдауна.
     */
    public long getRemainingTime(String name) {
        long remainingTime = getEndTime(name) - getCurrentTime();
        return remainingTime < 0 ? 0 : remainingTime;
    }

    /**
     * Установка кулдауна.
     *
     * @param name Имя записи кулдауна
     * @param time Время, через которое кулдаун спадёт
     */
    public void setCooldown(String name, long time) {
        cooldowns.put(name, getCurrentTime() + time);
    }

    /**
     * Получение оставшегося времени кулдауна в читабельном виде.
     *
     * @param name Имя записи кулдауна
     * @return Оставшееся время кулдауна в читабельном виде.
     */
    public SiTextLike getFormattedRemainingTime(String name) {
        return getFormattedRemainingTime(getRemainingTime(name));
    }

    /**
     * Получение оставшегося времени кулдауна в читабельном виде.
     *
     * @param remainingTime Оставшееся время кулдауна
     * @return Оставшееся время кулдауна в читабельном виде.
     */
    public abstract SiTextLike getFormattedRemainingTime(long remainingTime);

    /**
     * Запуск функции, если кулдаун закончился.
     *
     * @param name     Имя записи кулдауна
     * @param function Функция, которая будет выполнена
     */
    public void runIfCooldownEnd(String name, SiFunction function) {
        if (!hasCooldown(name)) function.call();
    }

    /**
     * Запуск функции, если кулдаун закончился.
     *
     * @param name     Имя записи кулдауна
     * @param time     Время кулдауна, которое будет установлено при выполнении функции
     * @param function Функция, которая будет выполнена
     */
    public void runIfCooldownEnd(String name, long time, SiFunction function) {
        if (!hasCooldown(name)) {
            setCooldown(name, time);
            function.call();
        }
    }
}
