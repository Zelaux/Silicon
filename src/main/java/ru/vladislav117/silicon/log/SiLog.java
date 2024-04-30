package ru.vladislav117.silicon.log;

import ru.vladislav117.silicon.Silicon;

import java.util.logging.Logger;

/**
 * Основной логгер фреймворка. Не может быть отключён, для отключаемых логгеров используйте SiLogger.
 */
public final class SiLog {
    static Logger logger;

    /**
     * Инициализация логгера.
     */
    public static void init() {
        logger = Silicon.getPlugin().getLogger();
    }

    /**
     * Преобразования массива объектов к строке, где каждый объект разделён пробелом.
     *
     * @param objects Массив объектов, где каждый объект будет преобразован в строку методом toString()
     * @return Строка, где все объекты будут разделены пробелом.
     */
    static String objectsToString(Object... objects) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : objects) stringBuilder.append(object).append(" ");
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * Вывод обычного сообщения в лог.
     *
     * @param objects Объекты, которые будут выведены в лог
     */
    public static void info(Object... objects) {
        logger.info(objectsToString(objects));
    }

    /**
     * Вывод предупредительного сообщения в лог.
     *
     * @param objects Объекты, которые будут выведены в лог
     */
    public static void warning(Object... objects) {
        logger.warning(objectsToString(objects));
    }
}
