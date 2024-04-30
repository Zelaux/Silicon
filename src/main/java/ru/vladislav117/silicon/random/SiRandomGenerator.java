package ru.vladislav117.silicon.random;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.function.SiFunction;

import java.util.List;
import java.util.Random;

/**
 * Генератор случайных чисел. Помимо работы с обычной генерацией чисел так же имеет иные полезные методы.
 */
public interface SiRandomGenerator {
    /**
     * Случайно генерирует истину или ложь.
     *
     * @return Истина или ложь.
     */
    boolean getBoolean();

    /**
     * Случайно генерирует целое число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от 0 (включительно) до заданного максимума (исключительно).
     */
    default short getShort(short max) {
        return getShort((short) 0, max);
    }

    /**
     * Случайно генерирует целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    short getShort(short min, short max);

    /**
     * Случайно генерирует целое число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от 0 (включительно) до заданного максимума (исключительно).
     */
    default int getInteger(int max) {
        return getInteger(0, max);
    }

    /**
     * Случайно генерирует целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    int getInteger(int min, int max);

    /**
     * Случайно генерирует целое число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от 0 (включительно) до заданного максимума (исключительно).
     */
    default long getLong(long max) {
        return getLong(0, max);
    }

    /**
     * Случайно генерирует целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное целое число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    long getLong(long min, long max);

    /**
     * Случайно генерирует дробное число от 0 (включительно) до 1 (исключительно).
     *
     * @return Случайно сгенерированное дробное число от 0 (включительно) до 1 (исключительно).
     */
    default float getFloat() {
        return getFloat(0, 1);
    }

    /**
     * Случайно генерирует дробное число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от 0 (включительно) до заданного максимума (исключительно).
     */
    default float getFloat(float max) {
        return getFloat(0, max);
    }

    /**
     * Случайно генерирует дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    float getFloat(float min, float max);

    /**
     * Случайно генерирует дробное число от 0 (включительно) до 1 (исключительно).
     *
     * @return Случайно сгенерированное дробное число от 0 (включительно) до 1 (исключительно).
     */
    default double getDouble() {
        return getDouble(0, 1);
    }

    /**
     * Случайно генерирует дробное число от 0 (включительно) до заданного максимума (исключительно).
     *
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от 0 (включительно) до заданного максимума (исключительно).
     */
    default double getDouble(double max) {
        return getDouble(0, max);
    }

    /**
     * Случайно генерирует дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     *
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Случайно сгенерированное дробное число от заданного минимума (включительно) до заданного максимума (исключительно).
     */
    double getDouble(double min, double max);

    /**
     * Выдает истину с заданным шансом.
     *
     * @param chance Шанс истины от 0 до 1
     * @return Истина или ложь.
     */
    default boolean chance(double chance) {
        return chance > getDouble();
    }

    /**
     * Выполняет функцию с указанным шансом.
     *
     * @param chance   Шанс выполнения функции
     * @param function Функция, которая будет выполнена
     * @return Была ли выполнена функция.
     */
    default boolean runChance(double chance, SiFunction function) {
        boolean run = chance(chance);
        if (run) function.call();
        return run;
    }

    /**
     * Выдаёт случайный элемент массива.
     *
     * @param array Массив
     * @param <T>   Тип объектов массива
     * @return Случайный элемент массива или null, если массив пустой.
     */
    @Nullable
    default <T> T choice(T[] array) {
        if (array.length == 0) return null;
        return array[getInteger(array.length)];
    }

    /**
     * Выдаёт случайный элемент списка.
     *
     * @param list Список
     * @param <T>  Тип объектов списка
     * @return Случайный элемент списка или null, если список пустой.
     */
    @Nullable
    default <T> T choice(List<T> list) {
        if (list.size() == 0) return null;
        return list.get(getInteger(list.size()));
    }

    /**
     * Реализация SiRandomGenerator, использующая встроенный генератор псевдослучайных чисел Java.
     */
    class JavaRandom implements SiRandomGenerator {
        protected Random random;

        /**
         * Создание нового генератора.
         *
         * @param random Генератор псевдослучайных чисел
         */
        public JavaRandom(Random random) {
            this.random = random;
        }

        /**
         * Создание нового генератора с заданным зерном.
         *
         * @param seed Зерно генератора псевдослучайных чисел
         */
        public JavaRandom(long seed) {
            random = new Random(seed);
        }

        /**
         * Создание нового генератора со случайным зерном.
         */
        public JavaRandom() {
            random = new Random();
        }

        @Override
        public boolean getBoolean() {
            return random.nextBoolean();
        }

        @Override
        public short getShort(short min, short max) {
            return (short) random.nextInt(min, max);
        }

        @Override
        public int getInteger(int min, int max) {
            return random.nextInt(min, max);
        }

        @Override
        public long getLong(long min, long max) {
            return random.nextLong(min, max);
        }

        @Override
        public float getFloat(float min, float max) {
            return random.nextFloat(min, max);
        }

        @Override
        public double getDouble(double min, double max) {
            return random.nextDouble(min, max);
        }
    }
}
