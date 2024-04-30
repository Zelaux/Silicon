package ru.vladislav117.silicon.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.style.SiStyleLike;

/**
 * Класс, представляющий собой строковый текст.
 */
public class SiTextString extends SiText {
    protected String string;

    /**
     * Создание нового строкового текста.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     * @param style  Стиль текста или null, если не задан
     */
    public SiTextString(Object object, @Nullable SiStyleLike style) {
        string = object.toString();
        this.style = style;
    }

    /**
     * Создание нового строкового текста.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     */
    public SiTextString(Object object) {
        string = object.toString();
    }

    /**
     * Копирование существующего текста.
     *
     * @param text Текст, который будет скопирован
     */
    public SiTextString(SiTextString text) {
        string = text.string;
        if (text.style != null) style = text.style.clone();
        if (text.hoverText != null) hoverText = text.hoverText.clone();
    }

    @Override
    public SiTextString clone() {
        return new SiTextString(this);
    }

    /**
     * Получение строки текста.
     *
     * @return Строка текста.
     */
    public String getString() {
        return string;
    }

    /**
     * Установка строки текста.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     * @return Этот же текст.
     */
    public SiTextString setString(Object object) {
        string = object.toString();
        return this;
    }

    @Override
    public String toString() {
        return "SiTextString{" +
                "string='" + string + '\'' +
                ", style=" + style +
                ", hoverText=" + hoverText +
                '}';
    }

    @Override
    public Component toComponent() {
        Component component = Component.text(string);
        if (style != null) component = component.style(style.toStyle());
        if (hoverText != null) component = component.hoverEvent(HoverEvent.showText(hoverText.toComponent()));
        return component;
    }
}
