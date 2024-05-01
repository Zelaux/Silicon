package ru.vladislav117.silicon;

import org.bukkit.Bukkit;
import ru.vladislav117.silicon.achievement.SiAchievements;
import ru.vladislav117.silicon.area.SiAreaTypes;
import ru.vladislav117.silicon.area.SiAreas;
import ru.vladislav117.silicon.block.SiBlocks;
import ru.vladislav117.silicon.chat.SiChat;
import ru.vladislav117.silicon.craft.SiCraftMenus;
import ru.vladislav117.silicon.craft.SiCrafts;
import ru.vladislav117.silicon.cusomModelData.SiCustomModelDataRegistry;
import ru.vladislav117.silicon.displayEntity.SiDisplayEntities;
import ru.vladislav117.silicon.displayEntity.SiDisplayEntityTypes;
import ru.vladislav117.silicon.economy.SiCurrencies;
import ru.vladislav117.silicon.effect.SiEffectTypes;
import ru.vladislav117.silicon.effect.SiEffects;
import ru.vladislav117.silicon.emoji.SiEmojis;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.external.coreprotect.SiCoreProtectAPI;
import ru.vladislav117.silicon.external.vault.SiVaultAPI;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.icon.SiIcons;
import ru.vladislav117.silicon.item.SiItemTypes;
import ru.vladislav117.silicon.item.category.SiItemCategories;
import ru.vladislav117.silicon.liquid.SiLiquidTypes;
import ru.vladislav117.silicon.log.SiLog;
import ru.vladislav117.silicon.materialReplacer.SiMaterialsReplacers;
import ru.vladislav117.silicon.menu.SiMenuElements;
import ru.vladislav117.silicon.menu.SiMenus;
import ru.vladislav117.silicon.region.SiRegion;
import ru.vladislav117.silicon.region.SiRegionFlags;
import ru.vladislav117.silicon.region.SiRegions;
import ru.vladislav117.silicon.resourcepack.SiResourcepack;
import ru.vladislav117.silicon.shop.SiShops;
import ru.vladislav117.silicon.store.SiStores;
import ru.vladislav117.silicon.ticker.SiTicker;
import ru.vladislav117.silicon.ticker.SiTickers;
import ru.vladislav117.silicon.weapon.SiWeapons;
import ru.vladislav117.silicon.weapon.bullet.SiBulletTypes;
import ru.vladislav117.silicon.world.SiWorlds;

/**
 * Основной класс фреймворка. Содержит методы для инициализации.
 */
public final class Silicon {
    static SiliconPlugin plugin;
    static boolean serverLoaded = false;
    static long ticks;
    static SiFile directory = new SiFile(Bukkit.getPluginsFolder()).getChild("Silicon").mkdirs();
    static SiFile bukkitRoot = new SiFile(Bukkit.getPluginsFolder().getParentFile());

    /**
     * Получение плагина фреймворка.
     *
     * @return Плагин фреймворка.
     */
    public static SiliconPlugin getPlugin() {
        return plugin;
    }

    /**
     * Получение значения, загружен ли сервер или пока инициализируется.
     *
     * @return Загружен ли сервер.
     */
    public static boolean isServerLoaded() {
        return serverLoaded;
    }

    /**
     * Получение тиков сервера.
     *
     * @return Тики сервера.
     */
    public static long getTicks() {
        return ticks;
    }

    /**
     * Получение директории фреймворка.
     *
     * @return Директория фреймворка.
     */
    public static SiFile getDirectory() {
        return directory;
    }

    /**
     * Получение коневого каталога сервера.
     *
     * @return Корневой каталог сервера.
     */
    public static SiFile getBukkitRoot() {
        return bukkitRoot;
    }

    /**
     * Инициализация фреймворка.
     *
     * @param plugin Плагин, который инициализирует фреймворк
     */
    public static void init(SiliconPlugin plugin) {
        Silicon.plugin = plugin;
        SiLog.init();
        SiLog.info("Initializing Silicon");

        SiliconCommands.init();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            serverLoaded = true;
            SiEvents.call(new SiBuiltinEvents.ServerLoadEvent());
        }, 0);

        SiEvents.addHandler(SiBuiltinEvents.ServerLoadEvent.class, event -> {
            SiLog.info("Silicon primary loading");
            primaryLoad();
            SiLog.info("Silicon secondary loading");
            secondaryLoad();
        });

        SiTickers.init();
        new SiTicker(ticker -> ticks++);

        SiVaultAPI.init();
        SiCoreProtectAPI.init();

        SiResourcepack.init();
        SiRegionFlags.init();
        SiRegions.init();
        SiEmojis.init();
        SiChat.init();
        SiCurrencies.init();
        SiStores.init();
        SiShops.init();
        SiMenus.init();
        SiMenuElements.init();
        SiCustomModelDataRegistry.init();
        SiLiquidTypes.init();
        SiAreaTypes.init();
        SiAreas.init();
        SiIcons.init();
        SiItemCategories.init();
        SiAchievements.init();
        SiItemTypes.init();
        SiMaterialsReplacers.init();
        SiBlocks.init();
        SiCrafts.init();
        SiCraftMenus.init();
        SiDisplayEntityTypes.init();
        SiDisplayEntities.init();
        SiBulletTypes.init();
        SiWeapons.init();
        SiEffectTypes.init();
        SiEffects.init();
        SiWorlds.init();
    }

    /**
     * Первичная загрузка контента, вызывается сразу после загрузки миров.
     */
    public static void primaryLoad() {
        SiEvents.call(new SiBuiltinEvents.PrimaryLoadStartEvent());

        SiRegionFlags.loaders.primaryLoad();
        SiEmojis.loaders.primaryLoad();
        SiCurrencies.loaders.primaryLoad();
        SiStores.loaders.primaryLoad();
        SiShops.loaders.primaryLoad();
        SiBulletTypes.loaders.primaryLoad();
        SiLiquidTypes.loaders.primaryLoad();
        SiAreaTypes.loaders.primaryLoad();
        SiIcons.loaders.primaryLoad();
        SiItemCategories.loaders.primaryLoad();
        SiItemTypes.loaders.primaryLoad();
        SiAchievements.loaders.primaryLoad();
        SiDisplayEntityTypes.loaders.primaryLoad();
        SiEffectTypes.loaders.primaryLoad();
        SiWorlds.loaders.primaryLoad();

        SiEvents.call(new SiBuiltinEvents.PrimaryLoadEndEvent());
    }

    /**
     * Вторичная загрузка контента. Весь контент обрабатывается в обратном порядке.
     */
    public static void secondaryLoad() {
        SiEvents.call(new SiBuiltinEvents.SecondaryLoadStartEvent());

        SiWorlds.loaders.secondaryLoad();
        SiEffectTypes.loaders.secondaryLoad();
        SiDisplayEntityTypes.loaders.secondaryLoad();
        SiAchievements.loaders.secondaryLoad();
        SiItemTypes.loaders.secondaryLoad();
        SiItemCategories.loaders.secondaryLoad();
        SiIcons.loaders.secondaryLoad();
        SiAreaTypes.loaders.secondaryLoad();
        SiLiquidTypes.loaders.secondaryLoad();
        SiBulletTypes.loaders.secondaryLoad();
        SiShops.loaders.secondaryLoad();
        SiStores.loaders.secondaryLoad();
        SiCurrencies.loaders.secondaryLoad();
        SiEmojis.loaders.secondaryLoad();
        SiRegionFlags.loaders.secondaryLoad();

        SiEvents.call(new SiBuiltinEvents.SecondaryLoadEndEvent());
    }
}
