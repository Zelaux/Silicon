package ru.vladislav117.silicon.text.style;

import net.kyori.adventure.text.format.Style;
import ru.vladislav117.silicon.type.SiCloneable;

/**
 * Интерфейс, экземпляры реализаций которого могут быть использованы в качестве стилей текста.
 */
public interface SiStyleLike extends SiCloneable {
    @Override
    SiStyleLike clone();

    /**
     * Преобразование объекта к стилю текста.
     *
     * @return Стиль текста, основанный на экземпляре.
     */
    Style toStyle();
}
