package ru.vladislav117.silicon.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.style.SiStyleLike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Класс, представляющий собой контейнер для других текстов.
 */
public class SiTextContainer extends SiText {
    protected ArrayList<SiTextLike> container = new ArrayList<>();

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     * @param style Стиль текста или null, если не задан
     */
    public SiTextContainer(Collection<SiTextLike> texts, @Nullable SiStyleLike style) {
        container.addAll(texts);
        this.style = style;
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     */
    public SiTextContainer(Collection<SiTextLike> texts) {
        container.addAll(texts);
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     * @param style Стиль текста или null, если не задан
     */
    public SiTextContainer(SiTextLike[] texts, @Nullable SiStyleLike style) {
        container.addAll(Arrays.asList(texts));
        this.style = style;
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     */
    public SiTextContainer(SiTextLike[] texts) {
        container.addAll(Arrays.asList(texts));
    }

    /**
     * Копирование существующего текстового контейнера.
     * При копировании все элементы контейнера так же будут скопированы.
     *
     * @param text Текстовый контейнер, который будет скопирован
     */
    public SiTextContainer(SiTextContainer text) {
        for (SiTextLike element : text.container) container.add(element.clone());
        if (text.style != null) style = text.style.clone();
        if (text.hoverText != null) hoverText = text.hoverText.clone();
    }

    @Override
    public SiTextContainer clone() {
        return new SiTextContainer(this);
    }

    /**
     * Создание нового пустого текстового контейнера.
     */
    public SiTextContainer() {
    }

    SiTextLike setStyleToTextLike(SiTextLike text, SiStyleLike style) {
        if (text instanceof SiText styledText) return styledText.clone().setStyle(style);
        return new SiTextContainer().addText(text).setStyle(style);
    }

    /**
     * Добавление текста в контейнер.
     *
     * @param text Текст, который будет добавлен
     * @return Этот же текст.
     */
    public SiTextContainer addText(SiTextLike text) {
        container.add(text);
        return this;
    }

    /**
     * Добавление текста в контейнер.
     *
     * @param text  Текст, который будет добавлен
     * @param style Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addText(SiTextLike text, @Nullable SiStyleLike style) {
        container.add(setStyleToTextLike(text, style));
        return this;
    }

    /**
     * Добавление текстов в контейнер.
     *
     * @param texts Тексты, которые будут добавлены
     * @return Этот же текст.
     */
    public SiTextContainer addTexts(Collection<SiTextLike> texts) {
        container.addAll(texts);
        return this;
    }

    /**
     * Добавление текстов в контейнер.
     *
     * @param texts Тексты, которые будут добавлены
     * @return Этот же текст.
     */
    public SiTextContainer addTexts(SiTextLike[] texts) {
        container.addAll(Arrays.asList(texts));
        return this;
    }

    /**
     * Добавление текстов в контейнер.
     *
     * @param texts Тексты, которые будут добавлены
     * @param style Стиль, который будет присвоен каждому добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addTexts(Collection<SiTextLike> texts, @Nullable SiStyleLike style) {
        for (SiTextLike text : texts) container.add(setStyleToTextLike(text, style));
        return this;
    }

    /**
     * Добавление текстов в контейнер.
     *
     * @param texts Тексты, которые будут добавлены
     * @param style Стиль, который будет присвоен каждому добавленному тексту
     * @return Этот же текст.
     */
    public SiTextContainer addTexts(SiTextLike[] texts, @Nullable SiStyleLike style) {
        for (SiTextLike text : texts) container.add(setStyleToTextLike(text, style));
        return this;
    }

    /**
     * Добавление строкового текста в контейнер.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     * @param style  Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addString(Object object, @Nullable SiStyleLike style) {
        container.add(new SiTextString(object, style));
        return this;
    }

    /**
     * Добавление строкового текста в контейнер.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     * @return Этот же текст.
     */
    public SiTextContainer addString(Object object) {
        container.add(new SiTextString(object));
        return this;
    }

    /**
     * Добавление переводимого текста в контейнер.
     *
     * @param translatable Переводимый объект, у которого будет взят ключ перевода
     * @param style        Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addTranslatable(Translatable translatable, @Nullable SiStyleLike style) {
        container.add(new SiTextTranslatable(translatable, style));
        return this;
    }

    /**
     * Добавление переводимого текста в контейнер.
     *
     * @param translatable Переводимый объект, у которого будет взят ключ перевода
     * @return Этот же текст.
     */
    public SiTextContainer addTranslatable(Translatable translatable) {
        container.add(new SiTextTranslatable(translatable));
        return this;
    }

    /**
     * Добавление переводимого текста в контейнер.
     *
     * @param translationKey Ключ перевода
     * @param style          Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addTranslatable(String translationKey, @Nullable SiStyleLike style) {
        container.add(new SiTextTranslatable(translationKey, style));
        return this;
    }

    /**
     * Добавление переводимого текста в контейнер.
     *
     * @param translationKey Ключ перевода
     * @return Этот же текст.
     */
    public SiTextContainer addTranslatable(String translationKey) {
        container.add(new SiTextTranslatable(translationKey));
        return this;
    }

    /**
     * Добавление текста с компонентом в контейнер.
     *
     * @param component Компонент текста
     * @param style     Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addComponent(Component component, @Nullable SiStyleLike style) {
        container.add(new SiTextComponent(component, style));
        return this;
    }

    /**
     * Добавление текста с компонентом в контейнер.
     *
     * @param component Компонент текста
     * @return Этот же текст.
     */
    public SiTextContainer addComponent(Component component) {
        container.add(new SiTextComponent(component));
        return this;
    }

    /**
     * Добавление контейнера в контейнер.
     *
     * @param texts Тексты контейнера
     * @param style Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addContainer(Collection<SiTextLike> texts, @Nullable SiStyleLike style) {
        container.add(new SiTextContainer(texts, style));
        return this;
    }

    /**
     * Добавление контейнера в контейнер.
     *
     * @param texts Тексты контейнера
     * @return Этот же текст.
     */
    public SiTextContainer addContainer(Collection<SiTextLike> texts) {
        container.add(new SiTextContainer(texts, style));
        return this;
    }

    /**
     * Добавление контейнера в контейнер.
     *
     * @param texts Тексты контейнера
     * @param style Стиль, который будет присвоен добавленному тексту или null, если не задан
     * @return Этот же текст.
     */
    public SiTextContainer addContainer(SiTextLike[] texts, @Nullable SiStyleLike style) {
        container.add(new SiTextContainer(texts, style));
        return this;
    }

    /**
     * Добавление контейнера в контейнер.
     *
     * @param texts Тексты контейнера
     * @return Этот же текст.
     */
    public SiTextContainer addContainer(SiTextLike[] texts) {
        container.add(new SiTextContainer(texts));
        return this;
    }

    /**
     * Добавление пробела в контейнер.
     *
     * @return Этот же текст.
     */
    public SiTextContainer addSpace() {
        container.add(new SiTextString(" "));
        return this;
    }

    /**
     * Добавление пробела в контейнер. Более короткий вариант метода addSpace().
     *
     * @return Этот же текст.
     */
    public SiTextContainer sp() {
        return addSpace();
    }

    /**
     * Добавление переноса строки в контейнер.
     *
     * @return Этот же текст.
     */
    public SiTextContainer addNewLine() {
        container.add(new SiTextComponent(Component.newline()));
        return this;
    }

    /**
     * Добавление переноса строки в контейнер. Более короткий вариант метода addNewLine().
     *
     * @return Этот же текст.
     */
    public SiTextContainer nl() {
        return addNewLine();
    }

    @Override
    public String toString() {
        return "SiTextContainer{" +
                "container=" + container +
                ", style=" + style +
                ", hoverText=" + hoverText +
                '}';
    }

    @Override
    public Component toComponent() {
        Component component = Component.empty();
        for (SiTextLike text : container) component = component.append(text.toComponent());
        if (style != null) component = component.style(style.toStyle());
        if (hoverText != null) component = component.hoverEvent(HoverEvent.showText(hoverText.toComponent()));
        return component;
    }
}
