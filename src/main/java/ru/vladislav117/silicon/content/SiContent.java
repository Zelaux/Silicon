package ru.vladislav117.silicon.content;

/**
 * Абстрактный контент.
 */
public class SiContent {
    protected String name;

    /**
     * Создание нового контента. При наследовании конструктора рекомендуется сразу регистрировать контент.
     *
     * @param name Имя контента
     */
    public SiContent(String name) {
        this.name = name;
    }

    /**
     * Получение имени контента.
     *
     * @return Имя контента.
     */
    public String getName() {
        return name;
    }
}
