package ru.vladislav117.silicon.utils;

/**
 * Полезные методы для работы с классами.
 */
public final class ClassUtils {
    /**
     * Получение оригинального класса.
     *
     * @param cls Класс
     * @return Оригинальный класс.
     */
    public static Class<?> getOriginalClass(Class<?> cls) {
        if (cls.isAnonymousClass()) {
            return cls.getInterfaces().length == 0 ? cls.getSuperclass() : cls.getInterfaces()[0];
        } else {
            return cls;
        }
    }

    /**
     * Получение оригинального класса объекта.
     *
     * @param object Объект
     * @return Оригинальный класс объекта.
     */
    public static Class<?> getOriginalClass(Object object) {
        return getOriginalClass(object.getClass());
    }
}
