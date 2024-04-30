package ru.vladislav117.silicon.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.style.SiStyleLike;

/**
 * Класс, представляющий собой переводимый текст с ключом перевода.
 */
public class SiTextTranslatable extends SiText {
    protected String translationKey;

    /**
     * Создание нового переводимого текста.
     *
     * @param translatable Переводимый объект с ключом перевода
     * @param style        Стиль текста или null, если не задан
     */
    public SiTextTranslatable(Translatable translatable, @Nullable SiStyleLike style) {
        translationKey = translatable.translationKey();
        this.style = style;
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translatable Переводимый объект с ключом перевода
     */
    public SiTextTranslatable(Translatable translatable) {
        translationKey = translatable.translationKey();
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translationKey Ключ перевода
     * @param style          Стиль текста или null, если не задан
     */
    public SiTextTranslatable(String translationKey, @Nullable SiStyleLike style) {
        this.translationKey = translationKey;
        this.style = style;
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translationKey Ключ перевода
     */
    public SiTextTranslatable(String translationKey) {
        this.translationKey = translationKey;
    }

    /**
     * Копирование существующего текста с ключом перевода.
     *
     * @param text Текст, который будет скопирован
     */
    public SiTextTranslatable(SiTextTranslatable text) {
        translationKey = text.translationKey;
        if (text.style != null) style = text.style.clone();
        if (text.hoverText != null) hoverText = text.hoverText.clone();
    }

    @Override
    public SiTextTranslatable clone() {
        return new SiTextTranslatable(this);
    }

    /**
     * Получение ключа перевода.
     *
     * @return Ключ перевода.
     */
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Установка ключа перевода.
     *
     * @param translatable Переводимый объект, у которого будет взят ключ перевода
     * @return Этот же текст.
     */
    public SiTextTranslatable setTranslationKey(Translatable translatable) {
        translationKey = translatable.translationKey();
        return this;
    }

    /**
     * Установка ключа перевода.
     *
     * @param translationKey Ключ перевода
     * @return Этот же текст.
     */
    public SiTextTranslatable setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    @Override
    public String toString() {
        return "SiTextTranslatable{" +
                "translationKey='" + translationKey + '\'' +
                ", style=" + style +
                ", hoverText=" + hoverText +
                '}';
    }

    @Override
    public Component toComponent() {
        Component component = Component.translatable(translationKey);
        if (style != null) component = component.style(style.toStyle());
        if (hoverText != null) component = component.hoverEvent(HoverEvent.showText(hoverText.toComponent()));
        return component;
    }
}
