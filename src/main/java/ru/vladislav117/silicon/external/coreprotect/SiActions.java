package ru.vladislav117.silicon.external.coreprotect;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Действия, регистрируемые Core Protect.
 */
public final class SiActions {
    // TODO: 30.04.2024 Прочие, более гибкие методы

    /**
     * Установка блока.
     *
     * @param user      Пользователь
     * @param location  Позиция
     * @param material  Материал
     * @param blockData Данные о блоке или null
     */
    public static void placeBlock(String user, Location location, Material material, @Nullable BlockData blockData) {
        if (SiCoreProtectAPI.isEnabled()) {
            SiCoreProtectAPI.getAPI().logPlacement(user, location, material, blockData);
        }
        location.getBlock().setType(material);
        if (blockData != null) location.getBlock().setBlockData(blockData);
    }

    /**
     * Разрушение блока.
     *
     * @param user     Пользователь
     * @param location Позиция
     * @param tool     Инструмент, который будет ломать блок
     */
    public static void breakBlock(String user, Location location, ItemStack tool) {
        if (SiCoreProtectAPI.isEnabled()) {
            SiCoreProtectAPI.getAPI().logRemoval(user, location, location.getBlock().getType(), location.getBlock().getBlockData().clone());
        }
        location.getBlock().breakNaturally(tool);
    }

    /**
     * Разрушение блока.
     *
     * @param user     Пользователь
     * @param location Позиция
     */
    public static void breakBlock(String user, Location location) {
        if (SiCoreProtectAPI.isEnabled()) {
            SiCoreProtectAPI.getAPI().logRemoval(user, location, location.getBlock().getType(), location.getBlock().getBlockData().clone());
        }
        location.getBlock().breakNaturally(new ItemStack(Material.NETHERITE_PICKAXE));
    }

    /**
     * Начать отслеживать изменения инвентаря.
     *
     * @param user     Пользователь
     * @param location Позиция
     */
    public static void startLogTransaction(String user, Location location) {
        if (SiCoreProtectAPI.isEnabled()) {
            SiCoreProtectAPI.getAPI().logContainerTransaction(user, location);
        }
    }
}
