package ru.vladislav117.silicon.area;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Зона, взаимодействующая с сущностями.
 */
public class SiArea {
    protected SiAreaType type;
    protected Location location;
    protected double radius;
    protected double power;
    protected int lifetime;

    /**
     * Создание новой зоны.
     *
     * @param type     Тип
     * @param location Позиция
     * @param radius   Радиус (1/2 ребра куба)
     * @param power    Сила
     * @param lifetime Время жизни
     */
    public SiArea(SiAreaType type, Location location, double radius, double power, int lifetime) {
        this.type = type;
        this.location = location;
        this.radius = radius;
        this.power = power;
        this.lifetime = lifetime;
        SiAreas.add(this);
    }

    /**
     * Создание новой зоны. Время жизни будет взято по умолчанию.
     *
     * @param type     Тип
     * @param location Позиция
     * @param radius   Радиус (1/2 ребра куба)
     * @param power    Сила
     */
    public SiArea(SiAreaType type, Location location, double radius, double power) {
        this.type = type;
        this.location = location;
        this.radius = radius;
        this.power = power;
        lifetime = type.getDefaultLifetime();
        SiAreas.add(this);
    }

    /**
     * Создание новой зоны. Время жизни и сила будут взяты по умолчанию.
     *
     * @param type     Тип
     * @param location Позиция
     * @param radius   Радиус (1/2 ребра куба)
     */
    public SiArea(SiAreaType type, Location location, double radius) {
        this.type = type;
        this.location = location;
        this.radius = radius;
        this.power = type.getDefaultPower();
        lifetime = type.getDefaultLifetime();
        SiAreas.add(this);
    }

    /**
     * Получить тип зоны.
     *
     * @return Тип зоны.
     */
    public SiAreaType getType() {
        return type;
    }

    /**
     * Установить тип зоны.
     *
     * @param type Тип
     * @return Эта же зона.
     */
    public SiArea setType(SiAreaType type) {
        this.type = type;
        return this;
    }

    /**
     * Получить позицию зоны.
     *
     * @return Позиция зоны.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Установить позицию зоны.
     *
     * @param location Позиция
     * @return Эта же зона.
     */
    public SiArea setLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Получить радиус зоны.
     *
     * @return Радиус зоны.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Установить радиус зоны.
     *
     * @param radius Радиус зоны
     * @return Эта же зона.
     */
    public SiArea setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Получить силу зоны.
     *
     * @return Сила зоны.
     */
    public double getPower() {
        return power;
    }

    /**
     * Установить силу зоны.
     *
     * @param power Сила зоны
     * @return Эта же зона.
     */
    public SiArea setPower(double power) {
        this.power = power;
        return this;
    }

    /**
     * Получить время жизни зоны.
     *
     * @return Время жизни зоны.
     */
    public int getLifetime() {
        return lifetime;
    }

    /**
     * Установить время жизни зоны.
     *
     * @param lifetime Время жизни
     * @return Эта же зона.
     */
    public SiArea setLifetime(int lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    /**
     * Обновление зоны.
     *
     * @return Эта же зона.
     */
    public SiArea update() {
        type.getInteractions().update(this);
        for (Entity entity : location.getNearbyEntities(radius, radius, radius)) {
            type.getInteractions().affectEntity(this, entity);
            if (entity instanceof LivingEntity livingEntity)
                type.getInteractions().affectLivingEntity(this, livingEntity);
            if (entity instanceof Player player)
                type.getInteractions().affectPlayer(this, player);
        }
        lifetime--;
        return this;
    }
}
