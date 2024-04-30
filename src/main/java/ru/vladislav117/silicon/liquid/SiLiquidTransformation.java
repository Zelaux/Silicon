package ru.vladislav117.silicon.liquid;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Трансформация жидкости.
 */
public class SiLiquidTransformation {
    protected double thresholdTemperature;
    @Nullable
    protected SiLiquidType newLiquidType = null;
    protected int itemsVolume = SiLiquids.bucketVolume;
    protected ArrayList<ItemStack> items = new ArrayList<>();

    /**
     * Создание новой трансформации жидкости.
     *
     * @param thresholdTemperature Пороговая температура, при преодолении которой произойдёт трансформация
     * @param newLiquidType        Новый тип жидкости или null, если жидкость исчезнет
     * @param items                Предметы, получающиеся при трансформации
     */
    public SiLiquidTransformation(double thresholdTemperature, @Nullable SiLiquidType newLiquidType, Collection<ItemStack> items) {
        this.thresholdTemperature = thresholdTemperature;
        this.newLiquidType = newLiquidType;
        this.items.addAll(items);
    }

    /**
     * Создание новой трансформации жидкости.
     *
     * @param thresholdTemperature Пороговая температура, при преодолении которой произойдёт трансформация
     * @param newLiquidType        Новый тип жидкости или null, если жидкость исчезнет
     * @param item                 Предмет, получающийся при трансформации
     */
    public SiLiquidTransformation(double thresholdTemperature, @Nullable SiLiquidType newLiquidType, ItemStack item) {
        this.thresholdTemperature = thresholdTemperature;
        this.newLiquidType = newLiquidType;
        items.add(item);
    }

    /**
     * Создание новой трансформации жидкости.
     *
     * @param thresholdTemperature Пороговая температура, при преодолении которой произойдёт трансформация
     * @param newLiquidType        Новый тип жидкости или null, если жидкость исчезнет
     */
    public SiLiquidTransformation(double thresholdTemperature, @Nullable SiLiquidType newLiquidType) {
        this.thresholdTemperature = thresholdTemperature;
        this.newLiquidType = newLiquidType;
    }

    /**
     * Получение пороговой температуры.
     *
     * @return Пороговая температура.
     */
    public double getThresholdTemperature() {
        return thresholdTemperature;
    }

    /**
     * Установка пороговой температуры.
     *
     * @param thresholdTemperature Пороговая температура
     * @return Эта же трансформация.
     */
    public SiLiquidTransformation setThresholdTemperature(double thresholdTemperature) {
        this.thresholdTemperature = thresholdTemperature;
        return this;
    }

    /**
     * Получение нового типа жидкости.
     *
     * @return Новый тип жидкости или null, если жидкость исчезнет.
     */
    @Nullable
    public SiLiquidType getNewLiquidType() {
        return newLiquidType;
    }

    /**
     * Установка нового типа жидкости.
     *
     * @param newLiquidType Новый тип жидкости
     * @return Эта же трансформация.
     */
    public SiLiquidTransformation setNewLiquidType(SiLiquidType newLiquidType) {
        this.newLiquidType = newLiquidType;
        return this;
    }

    /**
     * Получение стандартного объёма расчёта количества предметов.
     *
     * @return Стандартный объём расчёта количества предметов.
     */
    public int getItemsVolume() {
        return itemsVolume;
    }

    /**
     * Установка стандартного объёма расчёта количества предметов.
     *
     * @param itemsVolume Стандартный объём расчёта количества предметов
     * @return Этот же стак.
     */
    public SiLiquidTransformation setItemsVolume(int itemsVolume) {
        this.itemsVolume = itemsVolume;
        return this;
    }

    /**
     * Добавление предмета, получающегося при трансформации
     *
     * @param item Предмет, получающийся при трансформации
     * @return Эта же трансформация.
     */
    public SiLiquidTransformation addItem(ItemStack item) {
        items.add(item);
        return this;
    }

    /**
     * Получение предметов, получающихся при трансформации с учётом объёма жидкости.
     *
     * @param volume Объём жидкости
     * @return Предметы, получающиеся при трансформации.
     */
    public ArrayList<ItemStack> getItems(int volume) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (ItemStack item : items) {
            int amount = (int) (item.getAmount() * ((double) volume / itemsVolume));
            if (amount <= 0) continue;
            ItemStack itemStack = item.clone();
            itemStack.setAmount(amount);
            itemStacks.add(itemStack);
        }
        return itemStacks;
    }

    /**
     * Результат трансформации.
     */
    public static class TransformationResult {
        @Nullable
        public SiLiquidStack liquidStack;
        public Collection<ItemStack> items;

        /**
         * Создание нового результата трансформации.
         *
         * @param liquidStack Жидкостный стак или null, если жидкость исчезла
         * @param items       Предметы
         */
        public TransformationResult(@Nullable SiLiquidStack liquidStack, Collection<ItemStack> items) {
            this.liquidStack = liquidStack;
            this.items = items;
        }

        /**
         * Получение жидкостного стака.
         *
         * @return Жидкостный стак или null, если жидкость исчезла.
         */
        @Nullable
        public SiLiquidStack getLiquidStack() {
            return liquidStack;
        }

        /**
         * Получение предметов.
         *
         * @return Предметы.
         */
        public Collection<ItemStack> getItems() {
            return items;
        }
    }
}
