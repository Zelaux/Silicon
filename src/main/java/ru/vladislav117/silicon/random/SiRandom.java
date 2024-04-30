package ru.vladislav117.silicon.random;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.function.SiFunction;

import java.util.List;

/**
 * Общий генератор случайных чисел. Помимо работы с обычной генерацией чисел так же имеет иные полезные методы.
 */
public final class SiRandom {
    static SiRandomGenerator generator = new SiRandomGenerator.JavaRandom();

    /**
     * Случайно генерирует истину или ложь.
     *
     * @return Истина или ложь.
     */
    public static boolean getBoolean() {
        return generator.getBoolean();
    }

    /**
     * Случайно генерирует целое число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от 0 (включительно) до заданного максимума (исключительно).
     */
    public static short getShort(short max) {
        return generator.getShort(max);
    }

    /**
     * Случайно генерирует целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    public static short getShort(short min, short max) {
        return generator.getShort(min, max);
    }

    /**
     * Случайно генерирует целое число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от 0 (включительно) до заданного максимума (исключительно).
     */
    public static int getInteger(int max) {
        return generator.getInteger(max);
    }

    /**
     * Случайно генерирует целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    public static int getInteger(int min, int max) {
        return generator.getInteger(min, max);
    }

    /**
     * Случайно генерирует целое число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от 0 (включительно) до заданного максимума (исключительно).
     */
    public static long getLong(long max) {
        return generator.getLong(max);
    }

    /**
     * Случайно генерирует целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    public static long getLong(long min, long max) {
        return generator.getLong(min, max);
    }

    /**
     * Случайно генерирует дробное число от 0 (включительно) до 1 (исключительно).
     *
     * @return Случайно сгенерированное дробное число от 0 (включительно) до 1 (исключительно).
     */
    public static float getFloat() {
        return generator.getFloat();
    }

    /**
     * Случайно генерирует дробное число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от 0 (включительно) до заданного максимума (исключительно).
     */
    public static float getFloat(float max) {
        return generator.getFloat(max);
    }

    /**
     * Случайно генерирует дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    public static float getFloat(float min, float max) {
        return generator.getFloat(min, max);
    }

    /**
     * Случайно генерирует дробное число от 0 (включительно) до 1 (исключительно).
     *
     * @return Случайно сгенерированное дробное число от 0 (включительно) до 1 (исключительно).
     */
    public static double getDouble() {
        return generator.getDouble();
    }

    /**
     * Случайно генерирует дробное число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от 0 (включительно) до заданного максимума (исключительно).
     */
    public static double getDouble(double max) {
        return generator.getDouble(max);
    }

    /**
     * Случайно генерирует дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    public static double getDouble(double min, double max) {
        return generator.getDouble(min, max);
    }

    /**
     * Выдает истину с заданным шансом.
     *
     * @param chance Шанс истины от 0 до 1
     * @return Истина или ложь.
     */
    public static boolean chance(double chance) {
        return generator.chance(chance);
    }

    /**
     * Выполняет функцию с указанным шансом.
     *
     * @param chance   Шанс выполнения функции
     * @param function Функция, которая будет выполнена
     * @return Была ли выполнена функция.
     */
    public static boolean runChance(double chance, SiFunction function) {
        return generator.runChance(chance, function);
    }

    /**
     * Выдаёт случайный элемент массива.
     *
     * @param array Массив
     * @param <T>   Тип объектов массива
     * @return Случайный элемент массива или null, если массив пустой.
     */
    @Nullable
    public static <T> T choice(T[] array) {
        return generator.choice(array);
    }

    /**
     * Выдаёт случайный элемент списка.
     *
     * @param list Список
     * @param <T>  Тип объектов списка
     * @return Случайный элемент списка или null, если список пустой.
     */
    @Nullable
    public static <T> T choice(List<T> list) {
        return generator.choice(list);
    }
}
