package ru.vladislav117.silicon.function;

/**
 * Функция-фильтр. На вход получает объект, а на выходе значение, подходит ли объект фильтру.
 *
 * @param <T> Тип фильтруемого объекта
 */
@FunctionalInterface
public interface SiFilterFunction<T> {
    /**
     * Отфильтровать объект.
     *
     * @param object Фильтруемый объект
     * @return Подходит ли объект фильтру.
     */
    boolean isSuitable(T object);
}
