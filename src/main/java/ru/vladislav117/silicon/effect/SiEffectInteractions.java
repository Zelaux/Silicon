package ru.vladislav117.silicon.effect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Взаимодействия эффекта.
 */
public interface SiEffectInteractions {
    /**
     * Эффект выдан LivingEntity
     *
     * @param livingEntity LivingEntity
     * @param effect       Эффект
     * @param initiator    Тот, кто выдал эффект или null
     */
    void applyLivingEntity(LivingEntity livingEntity, SiEffect effect, @Nullable LivingEntity initiator);

    /**
     * Эффект выдан игроку
     *
     * @param player    Игрок
     * @param effect    Эффект
     * @param initiator Тот, кто выдал эффект или null
     */
    void applyPlayer(Player player, SiEffect effect, @Nullable LivingEntity initiator);

    /**
     * Применить эффект к LivingEntity.
     *
     * @param livingEntity LivingEntity
     * @param effect       Эффект
     * @param initiator    Тот, кто выдал эффект или null
     */
    void affectLivingEntity(LivingEntity livingEntity, SiEffect effect, @Nullable LivingEntity initiator);

    /**
     * Применить эффект к игроку.
     *
     * @param player    Игрок
     * @param effect    Эффект
     * @param initiator Тот, кто выдал эффект или null
     */
    void affectPlayer(Player player, SiEffect effect, @Nullable LivingEntity initiator);

    /**
     * Класс для более удобного переопределения методов.
     */
    class StaticInteractions implements SiEffectInteractions {
        @Override
        public void applyLivingEntity(LivingEntity livingEntity, SiEffect effect, @Nullable LivingEntity initiator) {

        }

        @Override
        public void applyPlayer(Player player, SiEffect effect, @Nullable LivingEntity initiator) {

        }

        @Override
        public void affectLivingEntity(LivingEntity livingEntity, SiEffect effect, @Nullable LivingEntity initiator) {

        }

        @Override
        public void affectPlayer(Player player, SiEffect effect, @Nullable LivingEntity initiator) {

        }
    }
}
