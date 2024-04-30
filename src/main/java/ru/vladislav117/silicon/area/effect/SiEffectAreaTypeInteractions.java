package ru.vladislav117.silicon.area.effect;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import ru.vladislav117.silicon.area.SiArea;
import ru.vladislav117.silicon.area.SiAreaInteractions;
import ru.vladislav117.silicon.effect.SiEffect;
import ru.vladislav117.silicon.particle.SiParticleEmitter;
import ru.vladislav117.silicon.random.SiRandom;

/**
 * Взаимодействия для зоны с частицами.
 */
public class SiEffectAreaTypeInteractions extends SiAreaInteractions.StaticInteractions {
    /**
     * Создать частицы для всех "испускателей".
     *
     * @param area Зона
     * @param type Тип зоны
     */
    protected void spawnParticles(SiArea area, SiEffectAreaType type) {
        for (SiParticleEmitter emitter : type.getParticleEmitters()) {
            double x = area.getLocation().getX() + SiRandom.getDouble(-area.getRadius(), area.getRadius());
            double y = area.getLocation().getY() + SiRandom.getDouble(-area.getRadius(), area.getRadius());
            double z = area.getLocation().getZ() + SiRandom.getDouble(-area.getRadius(), area.getRadius());
            emitter.emit(new Location(area.getLocation().getWorld(), x, y, z));
        }
    }

    @Override
    public void update(SiArea area) {
        if (!(area.getType() instanceof SiEffectAreaType type)) return;
        for (int i = 0; i < type.getParticlesForRadius1() * area.getRadius() * area.getRadius() * area.getRadius(); i++) {
            spawnParticles(area, type);
        }
    }

    @Override
    public void affectLivingEntity(SiArea area, LivingEntity entity) {
        if (!(area.getType() instanceof SiEffectAreaType type)) return;
        for (PotionEffect potionEffect : type.getPotionEffects()) {
            entity.addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration(), potionEffect.getAmplifier(), potionEffect.isAmbient(), potionEffect.hasParticles(), potionEffect.hasIcon()));
        }
        for (SiEffect effect : type.getEffects()) {
            effect.give(entity);
        }
    }
}
