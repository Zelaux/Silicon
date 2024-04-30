package ru.vladislav117.silicon.block;

import org.bukkit.block.Block;

/**
 * Функция обновления блока.
 */
@FunctionalInterface
public interface SiBlockUpdater {
    /**
     * Обновить блок.
     *
     * @param type  Тип блока
     * @param block Блок
     * @return Количество тиков до следующего обновления.
     */
    int update(SiBlockItemType type, Block block);
}
