package ru.vladislav117.silicon.effect;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.content.SiContent;

/**
 * Тип эффекта.
 */
public class SiEffectType extends SiContent {
    @Nullable
    protected SiEffectInteractions interactions;

    /**
     * Создание нового типа эффекта.
     *
     * @param name Имя эффекта
     */
    public SiEffectType(String name) {
        super(name);
        SiEffectTypes.all.add(this);
    }

    /**
     * Получение взаимодействий эффекта.
     *
     * @return Взаимодействия эффекта или null, если не заданы.
     */
    @Nullable
    public SiEffectInteractions getInteractions() {
        return interactions;
    }

    /**
     * Установка взаимодействий эффекта.
     *
     * @param interactions Взаимодействия эффекта
     * @return Этот же тип эффекта.
     */
    public SiEffectType setInteractions(SiEffectInteractions interactions) {
        this.interactions = interactions;
        return this;
    }

    /**
     * Создать эффект.
     *
     * @param ticks Тики эффекта
     * @return Эффект.
     */
    public SiEffect buildEffect(int ticks) {
        return new SiEffect(this, ticks);
    }

    /**
     * Создать эффект.
     *
     * @param ticks Тики эффекта
     * @param power Сила эффекта
     * @return Эффект.
     */
    public SiEffect buildEffect(int ticks, double power) {
        return new SiEffect(this, ticks, power);
    }
}
