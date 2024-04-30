package ru.vladislav117.silicon.ticker;

import ru.vladislav117.silicon.function.SiHandlerFunction;

/**
 * "Тикер". Представляет собой некую функцию, выполняющуюся каждый тик сервера.
 */
public class SiTicker {
    protected long ticks = 0;
    protected long ticksThreshold = Long.MAX_VALUE;
    protected SiHandlerFunction<SiTicker> handler;

    /**
     * Создание нового "тикера".
     *
     * @param ticksThreshold Порог тиков, после которого "тикер" прекращает работу
     * @param handler        Обработчик "тикера", содержит код выполняемой функции
     */
    public SiTicker(long ticksThreshold, SiHandlerFunction<SiTicker> handler) {
        this.ticksThreshold = ticksThreshold;
        this.handler = handler;
        SiTickers.addTicker(this);
    }

    /**
     * Создание нового "тикера", который работает огромное количество тиков (относительно бесконечно).
     *
     * @param handler Обработчик "тикера", содержит код выполняемой функции
     */
    public SiTicker(SiHandlerFunction<SiTicker> handler) {
        this.handler = handler;
        SiTickers.addTicker(this);
    }

    /**
     * Получение тиков, которые прожил "тикер".
     *
     * @return Количество тиков.
     */
    public long getTicks() {
        return ticks;
    }

    /**
     * Получение порога тиков, после которого "тикер" прекращает работу.
     *
     * @return Порог тиков, после которого "тикер" прекращает работу.
     */
    public long getTicksThreshold() {
        return ticksThreshold;
    }

    /**
     * Получение обработчика "тикера", который содержит код выполняемой функции.
     *
     * @return Обработчик "тикера", который содержит код выполняемой функции.
     */
    public SiHandlerFunction<SiTicker> getHandler() {
        return handler;
    }

    /**
     * Выполнение "тикера" и прибавление одного тика к прожитым тикам.
     */
    public void tick() {
        ticks++;
        handler.handle(this);
    }

    /**
     * Прекращение работы "тикера". Порог тиков устанавливается в текущее значение прожитых тиков.
     */
    public void kill() {
        ticksThreshold = ticks;
    }
}
