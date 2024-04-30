package ru.vladislav117.silicon.filter.liquid;

import ru.vladislav117.silicon.liquid.SiLiquidStack;

/**
 * Абстрактный фильтр жидкостей.
 */
public abstract class SiLiquidFilter {
    protected boolean inverted = false;

    /**
     * Проверка жидкостного стака без учёта инверсии фильтра.
     *
     * @param liquidStack Жидкостный стак
     * @return Подходит ли предмет.
     */
    public abstract boolean testLiquidStack(SiLiquidStack liquidStack);

    /**
     * Проверка жидкостного стака с учётом инверсии фильтра.
     *
     * @param itemStack Жидкостный стак
     * @return Подходит ли предмет.
     */
    public boolean isSuitable(SiLiquidStack itemStack) {
        boolean suitable = testLiquidStack(itemStack);
        if (inverted) return !suitable;
        return suitable;
    }
}
