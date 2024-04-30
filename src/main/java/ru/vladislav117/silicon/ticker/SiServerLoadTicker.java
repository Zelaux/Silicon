package ru.vladislav117.silicon.ticker;

import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.function.SiHandlerFunction;

/**
 * "Тикер". Представляет собой некую функцию, выполняющуюся каждый тик сервера, но только если сервер загружен.
 */
public class SiServerLoadTicker extends SiTicker {
    /**
     * Создание нового "тикера".
     *
     * @param ticksThreshold Порог тиков, после которого "тикер" прекращает работу
     * @param handler        Обработчик "тикера", содержит код выполняемой функции
     */
    public SiServerLoadTicker(long ticksThreshold, SiHandlerFunction<SiTicker> handler) {
        super(ticksThreshold, handler);
    }

    /**
     * Создание нового "тикера", который работает огромное количество тиков (относительно бесконечно).
     *
     * @param handler Обработчик "тикера", содержит код выполняемой функции
     */
    public SiServerLoadTicker(SiHandlerFunction<SiTicker> handler) {
        super(handler);
    }

    @Override
    public void tick() {
        if (Silicon.isServerLoaded()) super.tick();
    }
}
