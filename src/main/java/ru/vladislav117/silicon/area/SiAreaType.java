package ru.vladislav117.silicon.area;

import org.bukkit.Location;
import ru.vladislav117.silicon.content.SiContent;

/**
 * Тип зоны.
 */
public class SiAreaType extends SiContent {
    protected int defaultLifetime = 20;
    protected double defaultPower = 1;
    protected SiAreaInteractions interactions = new SiAreaInteractions.StaticInteractions();

    /**
     * Создание нового типа зоны.
     *
     * @param name Имя типа зоны
     */
    public SiAreaType(String name) {
        super(name);
    }

    /**
     * Получение времени жизни по умолчанию.
     *
     * @return Время жизни по умолчанию.
     */
    public int getDefaultLifetime() {
        return defaultLifetime;
    }

    /**
     * Установка времени жизни по умолчанию.
     *
     * @param defaultLifetime Время жизни по умолчанию
     * @return Этот же тип зоны.
     */
    public SiAreaType setDefaultLifetime(int defaultLifetime) {
        this.defaultLifetime = defaultLifetime;
        return this;
    }

    /**
     * Получение силы по умолчанию.
     *
     * @return Сила по умолчанию.
     */
    public double getDefaultPower() {
        return defaultPower;
    }

    /**
     * Установка силы по умолчанию.
     *
     * @param defaultPower Сила по умолчанию
     * @return Этот же тип зоны.
     */
    public SiAreaType setDefaultPower(double defaultPower) {
        this.defaultPower = defaultPower;
        return this;
    }

    /**
     * Получение взаимодействий зоны.
     *
     * @return Взаимодействия зоны.
     */
    public SiAreaInteractions getInteractions() {
        return interactions;
    }

    /**
     * Установка взаимодействий зоны.
     *
     * @param interactions Взаимодействия зоны
     * @return Этот же тип зоны.
     */
    public SiAreaType setInteractions(SiAreaInteractions interactions) {
        this.interactions = interactions;
        return this;
    }

    /**
     * Создание зоны.
     *
     * @param location Позиция
     * @param radius   Радиус
     * @param power    Сила
     * @param lifetime Время жизни
     * @return Созданная зона.
     */
    public SiArea spawn(Location location, double radius, double power, int lifetime) {
        return new SiArea(this, location, radius, power, lifetime);
    }

    /**
     * Создание зоны с временем жизни по умолчанию.
     *
     * @param location Позиция
     * @param radius   Радиус
     * @param power    Сила
     * @return Созданная зона.
     */
    public SiArea spawn(Location location, double radius, double power) {
        return new SiArea(this, location, radius, power);
    }

    /**
     * Создание зоны с силой и временем жизни по умолчанию.
     *
     * @param location Позиция
     * @param radius   Радиус
     * @return Созданная зона.
     */
    public SiArea spawn(Location location, double radius) {
        return new SiArea(this, location, radius);
    }
}
