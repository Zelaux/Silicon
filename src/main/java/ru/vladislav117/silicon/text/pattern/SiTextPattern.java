package ru.vladislav117.silicon.text.pattern;

import ru.vladislav117.silicon.text.SiText;

/**
 * Интерфейс, экземпляры реализаций которого являются шаблонами текста.
 */
public interface SiTextPattern {
    /**
     * Собрать шаблон в текст.
     *
     * @return Собранный по шаблону текст.
     */
    SiText build();
}
