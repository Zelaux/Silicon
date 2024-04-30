package ru.vladislav117.silicon.inventory;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Полезные функции для работы с инвентарём игрока.
 */
public class SiPlayerInventoryUtils {
    /**
     * Выдать игроку предметы. Всё, что не поместится в инвентарь, выпадет на землю.
     *
     * @param player    Игрок
     * @param itemStack Стак предметов
     */
    public static void give(@Nullable Player player, ItemStack itemStack) {
        if (player == null) return;
        HashMap<Integer, ItemStack> items = player.getInventory().addItem(itemStack);
        for (ItemStack item : items.values()) {
            Item itemEntity = player.getWorld().dropItem(player.getLocation(), item);
            itemEntity.setVelocity(new Vector());
        }
    }
}
