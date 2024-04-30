package ru.vladislav117.silicon.effect;

import org.bukkit.entity.LivingEntity;

public class SiEffect {
    protected SiEffectType type;
    protected int ticks;
    protected double power = 1;

    public SiEffect(SiEffectType type, int ticks, double power) {
        this.type = type;
        this.ticks = ticks;
        this.power = power;
    }

    public SiEffect(SiEffectType type, int ticks) {
        this.type = type;
        this.ticks = ticks;
    }

    public SiEffectType getType() {
        return type;
    }

    public int getTicks() {
        return ticks;
    }

    public SiEffect setTicks(int ticks) {
        this.ticks = ticks;
        return this;
    }

    public double getPower() {
        return power;
    }

    public SiEffect setPower(double power) {
        this.power = power;
        return this;
    }

    public SiEffect give(LivingEntity livingEntity) {
        SiEffects.addEffect(livingEntity, this, null);
        return this;
    }

    public SiEffect give(LivingEntity livingEntity, LivingEntity initiator) {
        SiEffects.addEffect(livingEntity, this, initiator);
        return this;
    }
}
