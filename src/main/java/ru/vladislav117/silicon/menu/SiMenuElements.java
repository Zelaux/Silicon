package ru.vladislav117.silicon.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.log.SiLog;

import java.util.HashMap;

/**
 * Класс-контейнер для элементов меню.
 */
public final class SiMenuElements {
    static HashMap<String, SiMenuElement> elements = new HashMap<>();

    /**
     * Добавить элемент меню.
     *
     * @param element Элемент меню
     */
    public static void add(SiMenuElement element) {
        elements.put(element.getName(), element);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onInventoryClickEvent(InventoryClickEvent event) {
                if (event.isCancelled()) return;
                SiItemStack itemStack = new SiItemStack(event.getCurrentItem());
                if (!itemStack.getTagsManager().hasTag("menu_element")) return;
                event.setCancelled(true);
                if (!itemStack.getTagsManager().hasTag("menu_element_name")) return;
                SiMenuElement element = elements.get(itemStack.getTagsManager().getString("menu_element_name"));
                if (element.getClickHandler() == null) return;
                element.getClickHandler().click((Player) event.getWhoClicked(), itemStack, event);
                if (element.getMenuTransfer() != null) {
                    SiMenu menu = SiMenus.get(element.getMenuTransfer());
                    if (menu != null) {
                        menu.open((Player) event.getWhoClicked());
                        return;
                    }
                    SiLog.warning("Menu not found:", element.getMenuTransfer());
                }
            }
        });
    }
}
