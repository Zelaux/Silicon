package ru.vladislav117.silicon.utils;

/**
 * Полезные функции для работы с числами.
 */
public class SiNumberUtils {
    /**
     * Возвращает строку с числом и двумя цифрами после запятой.
     *
     * @param number Число
     * @return Строковое число с двумя цифрами после запятой.
     */
    public static String doubleToString2Digits(double number) {
        return String.valueOf((int) (number * 100) / 100.0);
    }
}
