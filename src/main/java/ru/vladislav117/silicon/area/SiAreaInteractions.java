package ru.vladislav117.silicon.area;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Взаимодействия зоны.
 */
public interface SiAreaInteractions {
    /**
     * Создание зоны.
     *
     * @param area Зона
     */
    void spawn(SiArea area);

    /**
     * Обновление зоны.
     *
     * @param area Зона
     */
    void update(SiArea area);

    /**
     * Взаимодействие зоны с сущностью.
     *
     * @param area   Зона
     * @param entity Сущность
     */
    void affectEntity(SiArea area, Entity entity);

    /**
     * Взаимодействие зоны с LivingEntity.
     *
     * @param area   Зона
     * @param entity LivingEntity
     */
    void affectLivingEntity(SiArea area, LivingEntity entity);

    /**
     * Взаимодействие зоны с игроком.
     *
     * @param area   Зона
     * @param player Игрок
     */
    void affectPlayer(SiArea area, Player player);

    /**
     * Исчезновение зоны.
     *
     * @param area Зона
     */
    void disappear(SiArea area);

    /**
     * Класс для более удобного переопределения методов.
     */
    class StaticInteractions implements SiAreaInteractions {
        @Override
        public void spawn(SiArea area) {

        }

        @Override
        public void update(SiArea area) {

        }

        @Override
        public void affectEntity(SiArea area, Entity entity) {

        }

        @Override
        public void affectLivingEntity(SiArea area, LivingEntity entity) {

        }

        @Override
        public void affectPlayer(SiArea area, Player player) {

        }

        @Override
        public void disappear(SiArea area) {

        }
    }
}
