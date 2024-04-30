package ru.vladislav117.silicon.item;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.InventoryHolder;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.inventory.SiPlayerInventoryUtils;
import ru.vladislav117.silicon.log.SiLog;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.menu.SiMenus;
import ru.vladislav117.silicon.region.item.SiRegionItemDisplay;
import ru.vladislav117.silicon.region.item.SiRegionItemInteractions;
import ru.vladislav117.silicon.region.item.SiRegionItemType;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.ArrayList;

/**
 * Класс-контейнер для типов предметов.
 */
public final class SiItemTypes {
    public static final SiContentList<SiItemType> all = new SiContentList<>();

    public static SiItemType unknown;

    public static SiContentLoaders loaders = new SiContentLoaders();

    public static SiMenu menu;

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            unknown = new SiItemType("unknown", Material.COAL);
        });

        loaders.addSecondaryLoader(() -> {
            ArrayList<SiTextLike> description = new ArrayList<>() {{
                add(SiText.string("[ЛКМ] Выдать 1 предмет", SiPalette.Interface.green));
                add(SiText.string("[Shift+ЛКМ] Выдать стак предметов", SiPalette.Interface.green));
            }};
            ArrayList<SiMenuElement> elements = new ArrayList<>();
            for (SiItemType itemType : all.getAll()) {
                elements.add(new SiMenuElement("item_types_give_" + itemType.getName()).setItemStack(itemType).setDescription(description).setClickHandler((player, itemStack, event) -> {
                    if (event.isRightClick()) return;
                    int amount = 1;
                    if (event.isShiftClick()) {
                        amount = itemType.isUsesUuid() ? 1 : 0;
                        if (amount == 0) amount = itemType.getMaterial().getMaxStackSize();
                    }
                    SiPlayerInventoryUtils.give(player, itemType.buildItemStack(amount).toItemStack());
                }));
            }
            menu = SiMenus.buildMenuCluster("item_types", SiText.string("Все предметы"), elements);
        });

        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onPlayerInteractEvent(PlayerInteractEvent event) {
                if (event.isCancelled()) return;
                SiItemStack itemStack = new SiItemStack(event.getItem());
                SiItemType itemType = itemStack.getItemType();
                if (itemType.isUnknown() || itemType.getInteractions() == null) return;
                if (event.getAction().isRightClick()) {
                    itemType.getInteractions().rightClickAny(event.getPlayer(), event.getPlayer().isSneaking(), itemStack, event);
                    if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                        itemType.getInteractions().rightClickAir(event.getPlayer(), event.getPlayer().isSneaking(), itemStack, event);
                    } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        itemType.getInteractions().rightClickBlock(event.getPlayer(), event.getPlayer().isSneaking(), itemStack, event.getClickedBlock(), event);
                    }
                } else if (event.getAction().isLeftClick()) {
                    itemType.getInteractions().leftClickAny(event.getPlayer(), event.getPlayer().isSneaking(), itemStack, event);
                    if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
                        itemType.getInteractions().leftClickAir(event.getPlayer(), event.getPlayer().isSneaking(), itemStack, event);
                    } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        itemType.getInteractions().leftClickBlock(event.getPlayer(), event.getPlayer().isSneaking(), itemStack, event.getClickedBlock(), event);
                    }
                }
            }

            @EventHandler
            public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
                if (event.isCancelled()) return;
                if (!(event.getDamager() instanceof LivingEntity damager)) return;
                if (damager.getEquipment() == null) return;
                SiItemStack itemStack = new SiItemStack(damager.getEquipment().getItemInMainHand());
                if (damager instanceof Player p) itemStack = new SiItemStack(p.getInventory().getItemInMainHand());
                SiItemType itemType = itemStack.getItemType();
                if (itemType.isUnknown() || itemType.getInteractions() == null) return;
                Player playerDamager = null;
                if (damager instanceof Player) playerDamager = (Player) damager;

                itemType.getInteractions().livingEntityDamageEntity(damager, itemStack, event.getEntity(), event);
                if (playerDamager != null) {
                    itemType.getInteractions().playerDamageEntity(playerDamager, itemStack, event.getEntity(), event);
                }

                if (event.getEntity() instanceof LivingEntity livingEntityVictim) {
                    itemType.getInteractions().livingEntityDamageLivingEntity(damager, itemStack, livingEntityVictim, event);
                    if (playerDamager != null) {
                        itemType.getInteractions().playerDamageLivingEntity(playerDamager, itemStack, livingEntityVictim, event);
                    }
                }

                if (event.getEntity() instanceof Player playerVictim) {
                    itemType.getInteractions().livingEntityDamagePlayer(damager, itemStack, playerVictim, event);
                    if (playerDamager != null) {
                        itemType.getInteractions().playerDamagePlayer(playerDamager, itemStack, playerVictim, event);
                    }
                }
            }

            @EventHandler
            public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
                if (event.isCancelled()) return;
                SiItemStack itemStack = new SiItemStack(event.getItem());
                SiItemType itemType = itemStack.getItemType();
                if (itemType.isUnknown() || itemType.getInteractions() == null) return;
                itemType.getInteractions().consume(event.getPlayer(), itemStack, event);
            }

            @EventHandler
            public void onPotionSplashEvent(PotionSplashEvent event) {
                if (event.isCancelled()) return;
                SiItemStack itemStack = new SiItemStack(event.getPotion().getItem());
                SiItemType itemType = itemStack.getItemType();
                if (itemType.isUnknown() || itemType.getInteractions() == null) return;
                itemType.getInteractions().potionSplash(itemStack, event.getAffectedEntities(), event.getPotion(), event);
            }
        });
    }
}
