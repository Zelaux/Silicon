package ru.vladislav117.silicon.function;

/**
 * Простейшая функция, не имеет входных и выходных данных.
 */
@FunctionalInterface
public interface SiFunction {
    /**
     * Вызвать функцию.
     */
    void call();
}
