package ru.vladislav117.silicon.emoji;

import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.log.SiLog;

/**
 * Эмоджи.
 */
public class SiEmoji extends SiContent {
    protected String symbol;

    /**
     * Создание нового эмоджи.
     *
     * @param name Имя эмоджи
     */
    public SiEmoji(String name) {
        super(name);
        Character character = (char) ('֍' + name.hashCode());
        symbol = String.valueOf(character);
        SiEmojis.all.add(this);
    }

    /**
     * Получение символа эмоджи.
     *
     * @return Символ эмоджи.
     */
    public String getSymbol() {
        return symbol;
    }
}
