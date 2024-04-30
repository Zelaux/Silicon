package ru.vladislav117.silicon.external.vault;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import ru.vladislav117.silicon.log.SiLog;

/**
 * API к плагину Vault.
 */
public final class SiVaultAPI {
    static Permission permissions = null;

    /**
     * Проверяет, имеется ли у игрока разрешение.
     *
     * @param player       Игрок
     * @param permission   Разрешение
     * @param defaultValue Значение разрешения по умолчанию
     * @return Имеется ли у игрока разрешение.
     */
    public static boolean hasPermission(Player player, String permission, boolean defaultValue) {
        if (permissions == null) return player.isOp() || defaultValue;
        return permissions.has(player, permission);
    }

    /**
     * Проверяет, имеется ли у отправителя команды разрешение.
     *
     * @param sender       Отправитель команды
     * @param permission   Разрешение
     * @param defaultValue Значение разрешения по умолчанию.
     * @return Имеется ли у игрока разрешение.
     */
    public static boolean hasPermission(CommandSender sender, String permission, boolean defaultValue) {
        if (permissions == null) return sender.isOp() || defaultValue;
        return permissions.has(sender, permission);
    }

    /**
     * Инициализация API.
     */
    public static void init() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) return;
        SiLog.info("Vault plugin found, using it's API");
        RegisteredServiceProvider<Permission> registeredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (registeredServiceProvider == null) return;
        permissions = registeredServiceProvider.getProvider();
    }
}
