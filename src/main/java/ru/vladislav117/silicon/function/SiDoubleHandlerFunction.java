package ru.vladislav117.silicon.function;

/**
 * Функция-обработчик двух объектов. На вход получает два обрабатываемых объекта.
 *
 * @param <T1> Тип первого обрабатываемого объекта
 * @param <T2> Тип второго обрабатываемого объекта
 */
@FunctionalInterface
public interface SiDoubleHandlerFunction<T1, T2> {
    /**
     * Обработать объекты.
     *
     * @param object1 Первый обрабатываемый объект
     * @param object2 Второй обрабатываемый объект
     */
    void handle(T1 object1, T2 object2);
}
