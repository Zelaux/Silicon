package ru.vladislav117.silicon.namespace;

import org.bukkit.NamespacedKey;
import ru.vladislav117.silicon.Silicon;

/**
 * Класс для работы с ключами пространств имён.
 */
public class SiNamespace {
    /**
     * Получить ключ пространства имён плагина.
     *
     * @param key Имя ключа
     * @return Ключ в пространстве имён плагина.
     */
    public static NamespacedKey getKey(String key) {
        return new NamespacedKey(Silicon.getPlugin(), key);
    }
}
