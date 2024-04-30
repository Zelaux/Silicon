package ru.vladislav117.silicon.item;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Отображение имени и описания предмета.
 */
public interface SiItemDisplay {
    /**
     * Получить имя предмета в зависимости от стака.
     *
     * @param itemStack Стак
     * @return Имя предмета.
     */
    SiTextLike getDisplayName(SiItemStack itemStack);

    /**
     * Получить описание предмета в зависимости от стака.
     *
     * @param itemStack Стак
     * @return Описание предмета.
     */
    List<SiTextLike> getDescription(SiItemStack itemStack);

    /**
     * Получение цвета в зависимости от стака (например, цвет зелья или брони).
     *
     * @param itemStack Стак
     * @return Цвет или null.
     */
    @Nullable
    default SiColor getColor(SiItemStack itemStack) {
        return null;
    }

    /**
     * Статические имя и описание предмета, не зависящие от стака.
     */
    class StaticDisplay implements SiItemDisplay {
        protected SiTextLike displayName;
        protected ArrayList<SiTextLike> description = new ArrayList<>();
        protected SiColor color = null;

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(SiTextLike displayName, Collection<SiTextLike> description) {
            this.displayName = displayName;
            this.description.addAll(description);
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(SiTextLike displayName, SiTextLike description) {
            this.displayName = displayName;
            this.description.add(description);
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(SiTextLike displayName, SiLinedText description) {
            this.displayName = displayName;
            this.description.addAll(description.getCompleteTextParts());
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(SiTextLike displayName, String description) {
            this.displayName = displayName;
            this.description.addAll(new SiLinedText(description).getCompleteTextParts());
        }

        /**
         * Создание новых статических имени и пустого описания.
         *
         * @param displayName Имя
         */
        public StaticDisplay(SiTextLike displayName) {
            this.displayName = displayName;
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(String displayName, Collection<SiTextLike> description) {
            this.displayName = SiText.string(displayName);
            this.description.addAll(description);
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(String displayName, SiTextLike description) {
            this.displayName = SiText.string(displayName);
            this.description.add(description);
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(String displayName, SiLinedText description) {
            this.displayName = SiText.string(displayName);
            this.description.addAll(description.getCompleteTextParts());
        }

        /**
         * Создание новых статических имени и описания.
         *
         * @param displayName Имя
         * @param description Описание
         */
        public StaticDisplay(String displayName, String description) {
            this.displayName = SiText.string(displayName);
            this.description.addAll(new SiLinedText(description).getCompleteTextParts());
        }

        /**
         * Установка цвета.
         *
         * @param color Цвет
         * @return Это же отображение.
         */
        public StaticDisplay setColor(SiColor color) {
            this.color = color;
            return this;
        }

        /**
         * Создание новых статических имени и пустого описания.
         *
         * @param displayName Имя
         */
        public StaticDisplay(String displayName) {
            this.displayName = SiText.string(displayName);
        }

        @Override
        public SiTextLike getDisplayName(SiItemStack itemStack) {
            return displayName;
        }

        @Override
        public List<SiTextLike> getDescription(SiItemStack itemStack) {
            return description;
        }

        @Override
        @Nullable
        public SiColor getColor(SiItemStack itemStack) {
            return color;
        }
    }
}
