package ru.vladislav117.silicon.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.text.style.SiStyleLike;
import ru.vladislav117.silicon.type.SiCloneable;

import java.util.Collection;

/**
 * Абстрактный класс текста. Имеет стиль и текст, отображающийся при наведении.
 * Содержит методы для быстрого создания текста.
 */
public abstract class SiText implements SiTextLike, SiCloneable {
    @Nullable
    protected SiStyleLike style = null;
    @Nullable
    protected SiTextLike hoverText = null;

    @Override
    public abstract SiText clone();

    @Override
    @Nullable
    public SiStyleLike getStyle() {
        return style;
    }

    @Override
    public SiText setStyle(@Nullable SiStyleLike style) {
        this.style = style;
        return this;
    }

    /**
     * Получение текста при наведении.
     *
     * @return Текст при наведении или null, если не задан.
     */
    @Nullable
    public SiTextLike getHoverText() {
        return hoverText;
    }

    /**
     * Установка текста при наведении.
     *
     * @param hoverText Текст при наведении или null, если не задан
     * @return Этот же текст.
     */
    public SiText setHoverText(@Nullable SiTextLike hoverText) {
        this.hoverText = hoverText;
        return this;
    }

    // Конструкторы

    /**
     * Создание нового строкового текста.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     * @param style  Стиль текста или null, если не задан
     */
    public static SiTextString string(Object object, @Nullable SiStyleLike style) {
        return new SiTextString(object, style);
    }

    /**
     * Создание нового строкового текста.
     *
     * @param object Объект, который будет преобразован в строку методом toString()
     */
    public static SiTextString string(Object object) {
        return new SiTextString(object);
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translatable Переводимый объект с ключом перевода
     * @param style        Стиль текста или null, если не задан
     */
    public static SiTextTranslatable translatable(Translatable translatable, @Nullable SiStyleLike style) {
        return new SiTextTranslatable(translatable, style);
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translatable Переводимый объект с ключом перевода
     */
    public static SiTextTranslatable translatable(Translatable translatable) {
        return new SiTextTranslatable(translatable);
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translationKey Ключ перевода
     * @param style          Стиль текста или null, если не задан
     */
    public static SiTextTranslatable translatable(String translationKey, @Nullable SiStyleLike style) {
        return new SiTextTranslatable(translationKey, style);
    }

    /**
     * Создание нового переводимого текста.
     *
     * @param translationKey Ключ перевода
     */
    public static SiTextTranslatable translatable(String translationKey) {
        return new SiTextTranslatable(translationKey);
    }

    /**
     * Создание нового текста с компонентом текста.
     *
     * @param component Компонент текста
     * @param style     Стиль текста или null, если не задан
     */
    public static SiTextComponent component(Component component, @Nullable SiStyleLike style) {
        return new SiTextComponent(component, style);
    }

    /**
     * Создание нового текста с компонентом текста.
     *
     * @param component Компонент текста
     */
    public static SiTextComponent component(Component component) {
        return new SiTextComponent(component);
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     * @param style Стиль текста или null, если не задан
     */
    public static SiTextContainer container(Collection<SiTextLike> texts, @Nullable SiStyleLike style) {
        return new SiTextContainer(texts, style);
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     */
    public static SiTextContainer container(Collection<SiTextLike> texts) {
        return new SiTextContainer(texts);
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     * @param style Стиль текста или null, если не задан
     */
    public static SiTextContainer container(SiTextLike[] texts, @Nullable SiStyleLike style) {
        return new SiTextContainer(texts, style);
    }

    /**
     * Создание нового текстового контейнера.
     *
     * @param texts Тексты, которые будут добавлены в контейнер
     */
    public static SiTextContainer container(SiTextLike[] texts) {
        return new SiTextContainer(texts);
    }

    /**
     * Создание нового пустого текстового контейнера.
     */
    public static SiTextContainer container() {
        return new SiTextContainer();
    }
}
