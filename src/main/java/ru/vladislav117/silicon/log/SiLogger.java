package ru.vladislav117.silicon.log;

import ru.vladislav117.silicon.type.SiCloneable;

import java.util.ArrayList;
import java.util.List;

/**
 * Логгер. Записывает в лог от имени логгера фреймворка, однако имеет префикс второго уровня. Может быть отключён.
 */
public class SiLogger implements SiCloneable {
    protected String prefix;
    protected String processedPrefix;
    protected boolean enabled = true;

    /**
     * Создание нового логгера.
     *
     * @param prefix  Префикс логгера
     * @param enabled Включён ли логгер по умолчанию
     */
    public SiLogger(String prefix, boolean enabled) {
        this.prefix = prefix;
        this.enabled = enabled;
        processPrefix();
    }

    /**
     * Создание нового логгера, включённого по умолчанию.
     *
     * @param prefix Префикс логгера
     */
    public SiLogger(String prefix) {
        this.prefix = prefix;
        processPrefix();
    }

    /**
     * Копирование существующего логгера. При копировании будет взят "сырой" префикс логгера.
     * Если метод обработки префикса переопределён, то внешний вид префикса изменится в зависимости от метода.
     *
     * @param logger Логгер, который будет скопирован
     */
    public SiLogger(SiLogger logger) {
        prefix = logger.prefix;
        enabled = logger.enabled;
        processPrefix();
    }

    @Override
    public SiLogger clone() {
        return new SiLogger(this);
    }

    /**
     * Метод обработки префикса. Если не переопределён, обрамляет префикс в квадратные скобки.
     */
    protected void processPrefix() {
        processedPrefix = "[" + prefix + "]";
    }

    /**
     * Добавляет обработанный префикс в начало массива объектов.
     *
     * @param objects Исходный массив объектов
     * @return Массив объектов на основе исходного, в начале которого стоит обработанный префикс.
     */
    protected Object[] addPrefixToObjectsArray(Object[] objects) {
        // TODO: 31.12.2023 Оптимизировать алгоритм добавления префикса в начало массива объектов
        ArrayList<Object> list = new ArrayList<>();
        list.add(processedPrefix);
        list.addAll(List.of(objects));
        return list.toArray();
    }

    /**
     * Получение префикса.
     *
     * @return Префикс логгера.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Получение значения, включён ли логгер.
     *
     * @return Включён ли логгер.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Включение или выключение логгера.
     *
     * @param enabled Включённость логгера
     * @return Этот же логгер.
     */
    public SiLogger setEnabled(boolean enabled) {
        // TODO: 24.01.2024 Найти синоним к слову "включённость" (для документации)
        this.enabled = enabled;
        return this;
    }

    /**
     * Включение логгера.
     *
     * @return Этот же логгер.
     */
    public SiLogger enable() {
        enabled = true;
        return this;
    }

    /**
     * Выключение логгера.
     *
     * @return Этот же логгер.
     */
    public SiLogger disable() {
        enabled = false;
        return this;
    }

    /**
     * Вывод обычного сообщения в лог.
     *
     * @param objects Объекты, которые будут выведены в лог
     * @return Этот же логгер.
     */
    public SiLogger info(Object... objects) {
        if (enabled) SiLog.info(addPrefixToObjectsArray(objects));
        return this;
    }

    /**
     * Вывод предупредительного сообщения в лог.
     *
     * @param objects Объекты, которые будут выведены в лог
     * @return Этот же логгер.
     */
    public SiLogger warning(Object... objects) {
        if (enabled) SiLog.warning(addPrefixToObjectsArray(objects));
        return this;
    }

    @Override
    public String toString() {
        return "SiLogger{" +
                "prefix='" + prefix + '\'' +
                ", processedPrefix='" + processedPrefix + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
