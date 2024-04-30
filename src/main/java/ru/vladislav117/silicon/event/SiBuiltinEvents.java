package ru.vladislav117.silicon.event;

/**
 * Класс-контейнер для встроенных в фреймворк событий.
 */
public final class SiBuiltinEvents {
    /**
     * Событие, вызывающееся при загрузке сервера.
     */
    public static class ServerLoadEvent extends SiEvent {
    }

    /**
     * Событие, вызывающееся перед первичной загрузкой.
     */
    public static class PrimaryLoadStartEvent extends SiEvent {
    }

    /**
     * Событие, вызывающееся после первичной загрузки.
     */
    public static class PrimaryLoadEndEvent extends SiEvent {
    }

    /**
     * Событие, вызывающееся перед вторичной загрузкой.
     */
    public static class SecondaryLoadStartEvent extends SiEvent {
    }

    /**
     * Событие, вызывающееся после вторичной загрузки.
     */
    public static class SecondaryLoadEndEvent extends SiEvent {
    }

    /**
     * Событие, вызывающееся перед записью инструкций для ресурспака.
     */
    public static class ResourcepackWriteStartEvent extends SiEvent {
    }
}
