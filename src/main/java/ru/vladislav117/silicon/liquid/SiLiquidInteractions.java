package ru.vladislav117.silicon.liquid;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.item.SiItemStack;

/**
 * Взаимодействия с жидкостью.
 */
public interface SiLiquidInteractions {
    /**
     * Игрок выпил жидкость.
     *
     * @param liquidStack Стак
     * @param player      Игрок
     * @param event       Событие
     */
    void drink(SiLiquidStack liquidStack, Player player, @Nullable PlayerItemConsumeEvent event);

    /**
     * Предмет облит жидкостью.
     *
     * @param liquidStack Стак
     * @param itemStack   Предмет
     * @return Новый предмет в результате обливания.
     */
    SiItemStack pourItemStack(SiLiquidStack liquidStack, SiItemStack itemStack);

    /**
     * LivingEntity облит жидкостью. Так же вызывается, если игрок выпил жидкость.
     *
     * @param liquidStack Стак
     * @param entity      LivingEntity
     * @param initiator   Инициатор обливания, например тот, кто бросил бутылочку
     */
    void pourLivingEntity(SiLiquidStack liquidStack, LivingEntity entity, @Nullable Entity initiator);

    /**
     * Игрок облит жидкостью. Так же вызывается, если игрок выпил жидкость.
     *
     * @param liquidStack Стак
     * @param player      Игрок
     * @param initiator   Инициатор обливания, например тот, кто бросил бутылочку
     */
    void pourPlayer(SiLiquidStack liquidStack, Player player, @Nullable Entity initiator);

    /**
     * Жидкость пролилась, но никого не задела.
     *
     * @param liquidStack Стак
     * @param location    Позиция
     * @param initiator   Инициатор проливания
     * @param itemStack   Бутылочка, если проливание произошло в результате броска зелья, иначе null
     * @param potion      Бутылочка-сущность, если проливание произошло в результате броска зелья, иначе null
     * @param event       Событие, если проливание произошло в результате броска зелья, иначе null
     */
    void liquidSpilled(SiLiquidStack liquidStack, Location location, @Nullable Entity initiator, @Nullable SiItemStack itemStack, @Nullable ThrownPotion potion, @Nullable PotionSplashEvent event);

    /**
     * Взаимодействия для более удобного переопределения методов.
     */
    class StaticInteractions implements SiLiquidInteractions {
        @Override
        public void drink(SiLiquidStack liquidStack, Player player, PlayerItemConsumeEvent event) {

        }

        @Override
        public SiItemStack pourItemStack(SiLiquidStack liquidStack, SiItemStack itemStack) {
            return itemStack;
        }

        @Override
        public void pourLivingEntity(SiLiquidStack liquidStack, LivingEntity entity, @Nullable Entity initiator) {

        }

        @Override
        public void pourPlayer(SiLiquidStack liquidStack, Player player, @Nullable Entity initiator) {

        }

        @Override
        public void liquidSpilled(SiLiquidStack liquidStack, Location location, @Nullable Entity initiator, @Nullable SiItemStack itemStack, @Nullable ThrownPotion potion, @Nullable PotionSplashEvent event) {

        }
    }
}
