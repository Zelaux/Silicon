package ru.vladislav117.silicon.function;

/**
 * Функция-преобразователь. На вход получает обрабатываемый объект, а на выходе преобразование.
 *
 * @param <I> Тип входного объекта
 * @param <O> Тип выходного объекта
 */
@FunctionalInterface
public interface SiConverterFunction<I, O> {
    /**
     * Преобразовать объект.
     *
     * @param input Входной объект
     */
    O convert(I input);
}
