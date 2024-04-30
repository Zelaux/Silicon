package ru.vladislav117.silicon.weapon.bullet;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import ru.vladislav117.silicon.displayEntity.SiDisplayEntity;

/**
 * Взаимодействия снаряда.
 */
public interface SiBulletTypeActions {
    /**
     * Создание снаряда.
     *
     * @param displayEntity Снаряд
     */
    void spawn(SiDisplayEntity displayEntity);

    /**
     * Уничтожение снаряда по любой причине.
     *
     * @param displayEntity Снаряд
     */
    void destroyAny(SiDisplayEntity displayEntity);

    /**
     * Уничтожение снаряда от столкновения с блоком.
     *
     * @param displayEntity Снаряд
     * @param block         Блок
     */
    void destroyByBlock(SiDisplayEntity displayEntity, Block block);

    /**
     * Уничтожение снаряда от столкновения с сущностью.
     *
     * @param displayEntity Снаряд
     * @param entity        Сущность
     */
    void destroyByEntity(SiDisplayEntity displayEntity, Entity entity);

    /**
     * Уничтожение снаряда от столкновения с LivingEntity.
     *
     * @param displayEntity Снаряд
     * @param entity        LivingEntity
     */
    void destroyByLivingEntity(SiDisplayEntity displayEntity, LivingEntity entity);

    /**
     * Уничтожение снаряда от столкновения с игроком.
     *
     * @param displayEntity Снаряд
     * @param player        Игрок
     */
    void destroyByPlayer(SiDisplayEntity displayEntity, Player player);

    /**
     * Взаимодействия для более удобного переопределения методов.
     */
    class StaticActions implements SiBulletTypeActions {
        @Override
        public void spawn(SiDisplayEntity displayEntity) {

        }

        @Override
        public void destroyAny(SiDisplayEntity displayEntity) {

        }

        @Override
        public void destroyByBlock(SiDisplayEntity displayEntity, Block block) {

        }

        @Override
        public void destroyByEntity(SiDisplayEntity displayEntity, Entity entity) {

        }

        @Override
        public void destroyByLivingEntity(SiDisplayEntity displayEntity, LivingEntity entity) {

        }

        @Override
        public void destroyByPlayer(SiDisplayEntity displayEntity, Player player) {

        }
    }
}
