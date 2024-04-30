package ru.vladislav117.silicon.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.function.SiHandlerFunction;
import ru.vladislav117.silicon.utils.ClassUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс-контейнер событий.
 */
public final class SiEvents {
    static HashMap<Object, ArrayList<SiHandlerFunction<?>>> eventHandlers = new HashMap<>();

    /**
     * Добавление нового обработчика события.
     *
     * @param eventType Тип события
     * @param handler   Обработчик события
     * @param <T>       Тип события
     */
    public static <T extends SiEvent> void addHandler(Class<T> eventType, SiHandlerFunction<T> handler) {
        if (!eventHandlers.containsKey(eventType)) eventHandlers.put(eventType, new ArrayList<>());
        eventHandlers.get(eventType).add(handler);
    }

    /**
     * Вызов обработчиков события.
     *
     * @param event Событие, которое произошло
     * @param <T>   Тип события
     */
    public static <T extends SiEvent> void call(T event) {
        Class<?> eventClass = ClassUtils.getOriginalClass(event);
        if (!eventHandlers.containsKey(eventClass)) return;
        eventHandlers.get(eventClass).forEach(handler -> ((SiHandlerFunction<T>) handler).handle(event));
    }

    /**
     * Регистрация Bukkit событий.
     *
     * @param listener Слушатель событий
     */
    public static void registerBukkitEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Silicon.getPlugin());
    }
}
