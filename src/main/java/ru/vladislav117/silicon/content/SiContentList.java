package ru.vladislav117.silicon.content;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Список контента.
 *
 * @param <T> Тип контента
 */
public class SiContentList<T extends SiContent> {
    protected HashMap<String, T> items = new HashMap<>();
    protected ArrayList<T> itemsList = new ArrayList<>();
    protected ArrayList<String> itemsNames = new ArrayList<>();

    /**
     * Получение контента по имени.
     *
     * @param name Имя контента
     * @return Контент или null, если контента с данным именем нет.
     */
    public T get(String name) {
        return items.get(name);
    }

    /**
     * Получение контента по имени.
     *
     * @param name         Имя контента
     * @param defaultValue Значение по умолчанию
     * @return Контент или значение по умолчанию, если контента с данным именем нет.
     */
    public T get(String name, T defaultValue) {
        return items.getOrDefault(name, defaultValue);
    }

    /**
     * Получение всего контента.
     *
     * @return Весь контент.
     */
    public ArrayList<T> getAll() {
        return new ArrayList<>(itemsList); // (T[]) itemsList.toArray(new Object[0]);
    }

    /**
     * Получение имён всего контента.
     *
     * @return Имена всего контента.
     */
    public String[] getAllNames() {
        return itemsNames.toArray(new String[0]);
    }

    /**
     * Добавление контента.
     *
     * @param item Контент
     * @return Этот же список контента.
     */
    public SiContentList<T> add(T item) {
        items.put(item.getName(), item);
        itemsList.add(item);
        itemsNames.add(item.getName());
        return this;
    }
}
