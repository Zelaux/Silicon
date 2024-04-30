package ru.vladislav117.silicon.type;

/**
 * Интерфейс, экземпляры реализаций которого могут быть клонированы.
 */
public interface SiCloneable {
    /**
     * Копирование объекта.
     *
     * @return Клонированный экземпляр.
     */
    SiCloneable clone();
}
