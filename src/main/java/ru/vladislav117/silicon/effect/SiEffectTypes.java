package ru.vladislav117.silicon.effect;

import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;

/**
 * Класс-контейнер для типов эффектов.
 */
public final class SiEffectTypes {
    public static final SiContentList<SiEffectType> all = new SiContentList<>();

    public static SiEffectType effectEraser;

    public static final SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            effectEraser = new SiEffectType("effect_eraser") {{
                setInteractions(new SiEffectInteractions.StaticInteractions() {
                    @Override
                    public void applyLivingEntity(LivingEntity livingEntity, SiEffect effect, @Nullable LivingEntity initiator) {
                        SiEffects.clearAll(livingEntity.getUniqueId());
                    }
                });
            }};
        });

        loaders.addSecondaryLoader(() -> {
        });
    }
}
