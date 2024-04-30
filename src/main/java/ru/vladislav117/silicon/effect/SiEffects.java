package ru.vladislav117.silicon.effect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.ticker.SiServerLoadTicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Эффекты.
 */
public final class SiEffects {
    static HashMap<UUID, ArrayList<GivenEffect>> effects = new HashMap<>();

    /**
     * Выдать эффект.
     *
     * @param entity    Сущность
     * @param effect    Эффект
     * @param initiator Тот, кто выдал эффект или null, если не задан
     */
    public static void addEffect(LivingEntity entity, SiEffect effect, @Nullable LivingEntity initiator) {
        GivenEffect givenEffect = new GivenEffect(entity, effect, initiator);
        SiEffectInteractions interactions = givenEffect.getEffect().getType().getInteractions();
        if (interactions == null) return;
        interactions.applyLivingEntity(entity, effect, initiator);
        if (entity instanceof Player player) interactions.applyPlayer(player, effect, initiator);
    }

    /**
     * Очистить эффекты сущности.
     *
     * @param uuid UUID сущности.
     */
    public static void clearAll(UUID uuid) {
        if (!effects.containsKey(uuid)) return;
        effects.get(uuid).clear();
    }

    /**
     * Инициализация.
     */
    public static void init() {
        new SiServerLoadTicker(ticker -> {
            for (UUID uuid : effects.keySet()) {
                Entity entity = Bukkit.getEntity(uuid);
                if (entity == null) continue;
                LivingEntity livingEntity = (LivingEntity) entity;
                ArrayList<GivenEffect> effectsToRemove = new ArrayList<>();
                for (GivenEffect effect : effects.get(uuid)) {
                    SiEffectInteractions interactions = effect.getEffect().getType().getInteractions();
                    if (interactions == null) continue;
                    interactions.affectLivingEntity(livingEntity, effect.getEffect(), effect.getInitiator());
                    if (livingEntity instanceof Player player)
                        interactions.affectPlayer(player, effect.getEffect(), effect.getInitiator());
                    effect.getEffect().setTicks(effect.getEffect().getTicks() - 1);
                    if (effect.getEffect().getTicks() <= 0) {
                        effectsToRemove.add(effect);
                    }
                }
                for (GivenEffect effect : effectsToRemove) effects.get(uuid).remove(effect);
            }
        });
    }

    /**
     * Выданный эффект, хранит в себе сущность, эффект и того, кто выдал.
     */
    public static class GivenEffect {
        protected LivingEntity entity;
        protected SiEffect effect;
        @Nullable
        protected LivingEntity initiator;

        /**
         * Создание нового выданного эффекта.
         *
         * @param entity    Сущность
         * @param effect    Эффект
         * @param initiator Тот, кто выдал эффект или null, если не задан
         */
        public GivenEffect(LivingEntity entity, SiEffect effect, @Nullable LivingEntity initiator) {
            this.entity = entity;
            this.effect = effect;
            this.initiator = initiator;
            if (!effects.containsKey(entity.getUniqueId())) effects.put(entity.getUniqueId(), new ArrayList<>());
            effects.get(entity.getUniqueId()).add(this);
        }

        /**
         * Получение сущности.
         *
         * @return Сущность.
         */
        public LivingEntity getEntity() {
            return entity;
        }

        /**
         * Получение эффекта.
         *
         * @return Эффект.
         */
        public SiEffect getEffect() {
            return effect;
        }

        /**
         * Получение того, кто выдал эффект.
         *
         * @return Тот, кто выдал эффект или null, если не задано.
         */
        @Nullable
        public LivingEntity getInitiator() {
            return initiator;
        }
    }
}
