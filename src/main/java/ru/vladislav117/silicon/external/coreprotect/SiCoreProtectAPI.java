package ru.vladislav117.silicon.external.coreprotect;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

/**
 * API к плагину Core Protect.
 */
public final class SiCoreProtectAPI {
    static @Nullable CoreProtect plugin = null;
    static @Nullable CoreProtectAPI api = null;

    /**
     * Инициализация.
     */
    public static void init() {
        Plugin unknownPlugin = Bukkit.getPluginManager().getPlugin("CoreProtect");
        if (unknownPlugin == null) return;
        if (!(unknownPlugin instanceof CoreProtect coreProtectPlugin)) return;
        if (coreProtectPlugin.getAPI().APIVersion() < 9) return;
        plugin = coreProtectPlugin;
        api = plugin.getAPI();
    }

    /**
     * Включено ли API.
     *
     * @return Включено ли API.
     */
    public static boolean isEnabled() {
        return plugin != null;
    }

    /**
     * Получение плагина Core Protect.
     *
     * @return Плагин Core Protect или null.
     */
    @Nullable
    public static CoreProtect getPlugin() {
        return plugin;
    }

    /**
     * Получение API.
     *
     * @return API или null.
     */
    @Nullable
    public static CoreProtectAPI getAPI() {
        return api;
    }
}
