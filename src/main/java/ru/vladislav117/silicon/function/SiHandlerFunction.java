package ru.vladislav117.silicon.function;

/**
 * Функция-обработчик. На вход получает обрабатываемый объект.
 *
 * @param <T> Тип обрабатываемого объекта
 */
@FunctionalInterface
public interface SiHandlerFunction<T> {
    /**
     * Обработать объект.
     *
     * @param object Обрабатываемый объект
     */
    void handle(T object);
}
