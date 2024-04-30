package ru.vladislav117.silicon.filter.liquid;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.liquid.SiLiquidStack;
import ru.vladislav117.silicon.liquid.SiLiquidType;
import ru.vladislav117.silicon.range.SiRange;

/**
 * Универсальный фильтр жидкостей.
 */
public class SiUniversalLiquidFilter extends SiLiquidFilter {
    @Nullable
    protected SiLiquidType liquidType = null;
    protected SiRange temperature = new SiRange(-Double.MAX_VALUE, Double.MAX_VALUE);

    /**
     * Создание нового универсального фильтра жидкостей.
     *
     * @param liquidType  Тип жидкости или null, если подойдёт любая жидкость
     * @param temperature Диапазон допустимой температуры
     */
    public SiUniversalLiquidFilter(@Nullable SiLiquidType liquidType, SiRange temperature) {
        this.liquidType = liquidType;
        this.temperature = temperature;
    }

    /**
     * Создание нового универсального фильтра жидкостей.
     *
     * @param liquidType     Тип жидкости или null, если подойдёт любая жидкость
     * @param minTemperature Минимальная допустимая температура
     * @param maxTemperature Максимальная допустимая температура
     */
    public SiUniversalLiquidFilter(@Nullable SiLiquidType liquidType, double minTemperature, double maxTemperature) {
        this.liquidType = liquidType;
        temperature.setLeftBorder(minTemperature);
        temperature.setRightBorder(maxTemperature);
    }

    /**
     * Создание нового универсального фильтра жидкостей.
     *
     * @param liquidType Тип жидкости или null, если подойдёт любая жидкость
     */
    public SiUniversalLiquidFilter(@Nullable SiLiquidType liquidType) {
        this.liquidType = liquidType;
    }

    /**
     * Получение типа жидкости.
     *
     * @return Тип жидкости или null, если подойдёт любая жидкость.
     */
    @Nullable
    public SiLiquidType getLiquidType() {
        return liquidType;
    }

    @Override
    public boolean testLiquidStack(SiLiquidStack liquidStack) {
        if (liquidType != null && !liquidType.equals(liquidStack.getLiquidType())) return false;
        if (!temperature.isInRange(liquidStack.getTemperature())) return false;
        return true;
    }
}
