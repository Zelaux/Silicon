package ru.vladislav117.silicon.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.style.SiStyleLike;

/**
 * Класс, представляющий собой текст с компонентом текста.
 */
public class SiTextComponent extends SiText {
    protected Component component;

    /**
     * Создание нового текста с компонентом текста.
     *
     * @param component Компонент текста
     * @param style     Стиль текста или null, если не задан
     */
    public SiTextComponent(Component component, @Nullable SiStyleLike style) {
        this.component = component;
        this.style = style;
    }

    /**
     * Создание нового текста с компонентом текста.
     *
     * @param component Компонент текста
     */
    public SiTextComponent(Component component) {
        this.component = component;
    }

    /**
     * Копирование существующего текста с компонентом текста.
     * <p>
     * ВАЖНО! При копировании сам компонент скопирован не будет!
     *
     * @param text Текст, который будет скопирован
     */
    public SiTextComponent(SiTextComponent text) {
        component = text.component;
        if (text.style != null) style = text.style.clone();
        if (text.hoverText != null) hoverText = text.hoverText.clone();
    }

    @Override
    public SiTextComponent clone() {
        return new SiTextComponent(this);
    }

    /**
     * Получение компонента текста.
     *
     * @return Компонент текста.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Установка компонента текста.
     *
     * @param component Компонент текста
     * @return Этот же текст.
     */
    public SiTextComponent setComponent(Component component) {
        this.component = component;
        return this;
    }

    @Override
    public String toString() {
        return "SiTextComponent{" +
                "component=" + component +
                ", style=" + style +
                ", hoverText=" + hoverText +
                '}';
    }

    @Override
    public Component toComponent() {
        return component;
//        Component textComponent = Component.empty();
//        textComponent = textComponent.append(component);
//        if (style != null) textComponent = textComponent.style(style.toStyle());
//        if (hoverText != null) textComponent = textComponent.hoverEvent(HoverEvent.showText(hoverText.toComponent()));
//        return textComponent;
    }
}
