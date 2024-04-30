package ru.vladislav117.silicon.liquid;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.tags.SiTagsManager;
import ru.vladislav117.silicon.type.SiCloneable;

/**
 * Жидкостный стак.
 */
public class SiLiquidStack implements SiCloneable {
    protected SiLiquidType liquidType;
    protected int volume;
    protected double temperature;

    /**
     * Создание нового жидкостного стака.
     *
     * @param liquidType  Тип жидкости
     * @param volume      Объём стака
     * @param temperature Температура жидкости
     */
    public SiLiquidStack(SiLiquidType liquidType, int volume, double temperature) {
        this.liquidType = liquidType;
        this.volume = volume;
        this.temperature = temperature;
    }

    /**
     * Создание нового жидкостного стака с температурой жидкости по умолчанию.
     *
     * @param liquidType Тип жидкости
     * @param volume     Объём стака
     */
    public SiLiquidStack(SiLiquidType liquidType, int volume) {
        this.liquidType = liquidType;
        this.volume = volume;
        temperature = liquidType.getDefaultTemperature();
    }

    /**
     * Создание нового жидкостного стака с объёмом одного ведра по умолчанию.
     *
     * @param liquidType  Тип жидкости
     * @param temperature Температура жидкости
     */
    public SiLiquidStack(SiLiquidType liquidType, double temperature) {
        this.liquidType = liquidType;
        volume = SiLiquids.bucketVolume;
        this.temperature = temperature;
    }

    /**
     * Создание нового жидкостного стака с объёмом одного ведра и температурой жидкости по умолчанию.
     *
     * @param liquidType Тип жидкости
     */
    public SiLiquidStack(SiLiquidType liquidType) {
        this.liquidType = liquidType;
        volume = SiLiquids.bucketVolume;
        temperature = liquidType.getDefaultTemperature();
    }

    /**
     * Копирование существующего жидкостного стака.
     *
     * @param liquidStack Жидкостный стак, который будет скопирован
     */
    public SiLiquidStack(SiLiquidStack liquidStack) {
        liquidType = liquidStack.getLiquidType();
        volume = liquidStack.getVolume();
        temperature = liquidStack.getTemperature();
    }

    @Override
    public SiLiquidStack clone() {
        return new SiLiquidStack(this);
    }

    /**
     * Создание стака из жидкостного контейнера.
     *
     * @param itemStack Жидкостный контейнер
     * @return Стак или null, если предмет не является жидкостным контейнером.
     */
    @Nullable
    public static SiLiquidStack buildFromLiquidContainer(SiItemStack itemStack) {
        SiTagsManager tags = itemStack.getTagsManager();
        if (!tags.hasTag("liquid_container_volume")) return null;
        SiLiquidType containerLiquidType = SiLiquidTypes.all.get(tags.getString("liquid_type", SiLiquidTypes.vacuum.getName()), SiLiquidTypes.vacuum);
        int containerLiquidVolume = tags.getInteger("liquid_volume", SiLiquids.bucketVolume);
        double containerLiquidTemperature = tags.getDouble("liquid_temperature", containerLiquidType.getDefaultTemperature());
        return new SiLiquidStack(containerLiquidType, containerLiquidVolume, containerLiquidTemperature);
    }

    /**
     * Получение типа жидкости.
     *
     * @return Тип жидкости.
     */
    public SiLiquidType getLiquidType() {
        return liquidType;
    }

    /**
     * Установка типа жидкости.
     *
     * @param liquidType Тип жидкости
     * @return Этот же стак.
     */
    public SiLiquidStack setLiquidType(SiLiquidType liquidType) {
        this.liquidType = liquidType;
        return this;
    }

    /**
     * Получение объёма.
     *
     * @return Объём.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Установка объёма.
     *
     * @param volume Объём
     * @return Этот же стак.
     */
    public SiLiquidStack setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    /**
     * Добавление объёма.
     *
     * @param volume Объём
     * @return Этот же стак.
     */
    public SiLiquidStack addVolume(int volume) {
        this.volume += volume;
        return this;
    }

    /**
     * Получение температуры.
     *
     * @return Температура.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Установка температуры.
     *
     * @param temperature Температура
     * @return Этот же стак.
     */
    public SiLiquidStack setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    @Override
    public String toString() {
        return "SiLiquidStack{" +
                "liquidType=" + liquidType +
                ", volume=" + volume +
                ", temperature=" + temperature +
                '}';
    }
}
