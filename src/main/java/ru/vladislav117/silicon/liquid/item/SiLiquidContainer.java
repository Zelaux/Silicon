package ru.vladislav117.silicon.liquid.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.liquid.SiLiquidStack;
import ru.vladislav117.silicon.liquid.SiLiquidTypes;
import ru.vladislav117.silicon.liquid.SiLiquids;
import ru.vladislav117.silicon.tags.SiTagsManager;

/**
 * Жидкостный контейнер. Представляет собой предмет с жидкостью внутри.
 */
public class SiLiquidContainer {
    protected SiItemStack itemStack;
    protected int maxVolume;
    protected SiLiquidStack liquidStack;

    /**
     * Является ли предмет жидкостным контейнером.
     *
     * @param itemStack Предмет
     * @return Является ли предмет жидкостным контейнером.
     */
    public static boolean isLiquidContainer(SiItemStack itemStack) {
        return itemStack.getTagsManager().hasTag("liquid_container_volume");
    }

    /**
     * Является ли предмет жидкостным контейнером.
     *
     * @param itemStack Предмет
     * @return Является ли предмет жидкостным контейнером.
     */
    public static boolean isLiquidContainer(ItemStack itemStack) {
        return new SiItemStack(itemStack).getTagsManager().hasTag("liquid_container_volume");
    }

    /**
     * Создание нового жидкостного контейнера из предмета.
     *
     * @param itemStack Предмет
     */
    public SiLiquidContainer(SiItemStack itemStack) {
        this.itemStack = itemStack;
        maxVolume = itemStack.getTagsManager().getInteger("liquid_container_volume", SiLiquids.bucketVolume);
        liquidStack = SiLiquidStack.buildFromLiquidContainer(itemStack);
    }

    /**
     * Создание нового жидкостного контейнера из предмета.
     *
     * @param itemStack Предмет
     */
    public SiLiquidContainer(ItemStack itemStack) {
        this(new SiItemStack(itemStack));
    }

    /**
     * Создание нового жидкостного контейнера из предмета.
     *
     * @param itemStack Предмет
     * @return Жидкостный контейнер или null, если предмет не им является.
     */
    @Nullable
    public static SiLiquidContainer parseItemStack(SiItemStack itemStack) {
        if (!isLiquidContainer(itemStack)) return null;
        return new SiLiquidContainer(itemStack);
    }

    /**
     * Создание нового жидкостного контейнера из предмета.
     *
     * @param itemStack Предмет
     * @return Жидкостный контейнер или null, если предмет не им является.
     */
    @Nullable
    public static SiLiquidContainer parseItemStack(ItemStack itemStack) {
        if (!isLiquidContainer(itemStack)) return null;
        return new SiLiquidContainer(itemStack);
    }

    /**
     * Получение максимального объёма контейнера.
     *
     * @return Максимальный объём контейнера.
     */
    public int getMaxVolume() {
        return maxVolume;
    }

    /**
     * Установка максимального объёма контейнера.
     *
     * @param maxVolume Максимальный объём контейнера
     * @return Этот же контейнер.
     */
    public SiLiquidContainer setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
        return this;
    }

    /**
     * Получение жидкостного стака контейнера.
     *
     * @return Жидкостный стак контейнера.
     */
    public SiLiquidStack getLiquidStack() {
        return liquidStack;
    }

    /**
     * Установка жидкостного стака.
     *
     * @param liquidStack Жидкостный стак или null. Если указано null, будет задан стак с вакуумом.
     * @return Этот же контейнер.
     */
    public SiLiquidContainer setLiquidStack(@Nullable SiLiquidStack liquidStack) {
        if (liquidStack == null) liquidStack = new SiLiquidStack(SiLiquidTypes.vacuum, maxVolume);
        this.liquidStack = liquidStack;
        return this;
    }

    /**
     * Установка пустого жидкостного стака.
     *
     * @return Этот же контейнер.
     */
    public SiLiquidContainer setEmptyLiquidStack() {
        liquidStack = new SiLiquidStack(SiLiquidTypes.vacuum, maxVolume);
        return this;
    }

    /**
     * Получение свободного объёма контейнера.
     *
     * @return Свободный объём контейнера.
     */
    public int getFreeVolume() {
        return maxVolume - liquidStack.getVolume();
    }

    /**
     * Может ли стак быть добавлен хотя бы частично
     *
     * @param inputStack Стак
     * @return Может ли стак быть добавлен хотя бы частично
     */
    public boolean canBeAdded(SiLiquidStack inputStack) {
        if (liquidStack.getLiquidType().isReplaceable()) return true;
        if (!liquidStack.getLiquidType().equals(inputStack.getLiquidType())) return false;
        return true;
    }

    /**
     * Расчёт средней температуры двух стаков.
     *
     * @param temperature1 Температура 1
     * @param volume1      Объём 1
     * @param temperature2 Температура 2
     * @param volume2      Объём 2
     * @return Средняя температура двух стаков.
     */
    static double calculateTemperature(double temperature1, int volume1, double temperature2, int volume2) {
        double totalVolume = volume1 + volume2;
        return temperature1 * (volume1 / totalVolume) + temperature2 * (volume2 / totalVolume);
    }

    /**
     * Добавление стака к контейнеру.
     *
     * @param inputStack Стак
     * @return Остаток, который не поместился или null, если поместился весь стак.
     */
    @Nullable
    public SiLiquidStack add(SiLiquidStack inputStack) {
        if (!canBeAdded(inputStack)) return null;
        if (liquidStack.getLiquidType().isReplaceable()) {
            liquidStack.setLiquidType(inputStack.getLiquidType());
            int volume = Math.min(maxVolume, inputStack.getVolume());
            liquidStack.setVolume(volume);
            liquidStack.setTemperature(inputStack.getTemperature());
            int remainingVolume = inputStack.getVolume() - volume;
            if (remainingVolume <= 0) return null;
            return inputStack.setVolume(remainingVolume);
        }
        int volume = Math.min(getFreeVolume(), inputStack.getVolume());
        double finalTemperature = calculateTemperature(liquidStack.getTemperature(), liquidStack.getVolume(), inputStack.getTemperature(), volume);
        liquidStack.addVolume(volume).setTemperature(finalTemperature);
        int remainingVolume = inputStack.getVolume() - volume;
        if (remainingVolume <= 0) return null;
        return inputStack.setVolume(remainingVolume);
    }

    /**
     * Может ли быть удалён объём.
     *
     * @param volume Объём
     * @return Может ли быть удалён объём.
     */
    public boolean canBeRemoved(int volume) {
        return liquidStack.getVolume() >= volume;
    }

    /**
     * Удаление объёма. Если жидкости не осталось, то стак установится в вакуум.
     *
     * @param volume Объём
     * @return Был ли удалён объём.
     */
    public boolean remove(int volume) {
        if (!canBeRemoved(volume)) return false;
        liquidStack.addVolume(-volume);
        if (liquidStack.getVolume() <= 0) {
            liquidStack.setLiquidType(SiLiquidTypes.vacuum);
            liquidStack.setVolume(maxVolume);
            liquidStack.setTemperature(SiLiquidTypes.vacuum.getDefaultTemperature());
        }
        return true;
    }

    /**
     * Преобразование контейнера в стак.
     *
     * @return Стак.
     */
    public SiItemStack toItemStack() {
        SiTagsManager tags = itemStack.getTagsManager();
        tags.setInteger("liquid_container_volume", maxVolume);
        tags.setString("liquid_type", liquidStack.getLiquidType().getName());
        tags.setInteger("liquid_volume", liquidStack.getVolume());
        tags.setDouble("liquid_temperature", liquidStack.getTemperature());
        itemStack.updateDisplay();
        return itemStack;
    }

    @Override
    public String toString() {
        return "SiLiquidContainer{" +
                "itemStack=" + itemStack +
                ", maxVolume=" + maxVolume +
                ", liquidStack=" + liquidStack +
                '}';
    }
}
