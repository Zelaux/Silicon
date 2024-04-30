package ru.vladislav117.silicon.area.effect;

import org.bukkit.potion.PotionEffect;
import ru.vladislav117.silicon.area.SiAreaType;
import ru.vladislav117.silicon.effect.SiEffect;
import ru.vladislav117.silicon.particle.SiParticleEmitter;

import java.util.ArrayList;

/**
 * Зона с эффектами.
 */
public class SiEffectAreaType extends SiAreaType {
    protected ArrayList<PotionEffect> potionEffects = new ArrayList<>();
    protected ArrayList<SiEffect> effects = new ArrayList<>();
    protected ArrayList<SiParticleEmitter> particleEmitters = new ArrayList<>();
    protected int particlesForRadius1 = 4;

    /**
     * Создание нового типа зоны.
     *
     * @param name Имя типа зоны
     */
    public SiEffectAreaType(String name) {
        super(name);
        interactions = new SiEffectAreaTypeInteractions();
    }

    /**
     * Получение эффектов.
     *
     * @return Эффекты.
     */
    public ArrayList<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    /**
     * Добавление эффекта.
     *
     * @param effect Эффект
     * @return Этот же тип зоны.
     */
    public SiEffectAreaType addPotionEffect(PotionEffect effect) {
        potionEffects.add(effect);
        return this;
    }

    /**
     * Получение эффектов.
     *
     * @return Эффекты.
     */
    public ArrayList<SiEffect> getEffects() {
        return effects;
    }

    /**
     * Добавление эффекта.
     *
     * @param effect Эффект
     * @return Этот же тип зоны.
     */
    public SiEffectAreaType addEffect(SiEffect effect) {
        effects.add(effect);
        return this;
    }

    /**
     * Получение "испускателей" частиц.
     *
     * @return "Испускатели" частиц.
     */
    public ArrayList<SiParticleEmitter> getParticleEmitters() {
        return particleEmitters;
    }

    /**
     * Добавление "испускателя" частиц.
     *
     * @param particleEmitter "Испускатель" частиц
     * @return Этот же тип зоны.
     */
    public SiEffectAreaType addParticleEmitter(SiParticleEmitter particleEmitter) {
        particleEmitters.add(particleEmitter);
        return this;
    }

    /**
     * Получение количества частиц для зоны с радиусом 1.
     *
     * @return Количество частиц для зоны с радиусом 1.
     */
    public int getParticlesForRadius1() {
        return particlesForRadius1;
    }

    /**
     * Установка количества частиц для зоны с радиусом 1.
     *
     * @param particlesForRadius1 Количество частиц для зоны с радиусом 1
     * @return Этот же тип зоны.
     */
    public SiEffectAreaType setParticlesForRadius1(int particlesForRadius1) {
        this.particlesForRadius1 = particlesForRadius1;
        return this;
    }
}
