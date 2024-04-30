package ru.vladislav117.silicon.weapon;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.cooldown.SiTickCooldown;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.weapon.item.SiWeaponItemType;

/**
 * Класс-контейнер для работы с оружием.
 */
public final class SiWeapons {
    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onEntityShootBowEvent(EntityShootBowEvent event) {
                if (event.isCancelled()) return;
                if (!(event.getEntity() instanceof Player player)) return;
                if (event.getBow() == null) {
                    event.getProjectile().remove();
                    return;
                }
                if (event.getBow().getType() != Material.CROSSBOW) {
                    return;
                }

                SiItemStack crossbowItemStack = new SiItemStack(event.getBow());

                if (!(crossbowItemStack.getItemType() instanceof SiWeaponItemType weaponItemType)) return;
                event.getProjectile().remove();
                SiItemStack offhandItemStack = new SiItemStack(event.getEntity().getEquipment().getItemInOffHand());
                crossbowItemStack.setCrossbowProjectile(new ItemStack(Material.ARROW));
                if (weaponItemType.equals(offhandItemStack.getItemType())) {
                    // Weapon in the offhand
                    event.getEntity().getEquipment().setItemInOffHand(crossbowItemStack.setDurability(Material.CROSSBOW.getMaxDurability()).toItemStack());
                } else {
                    // Weapon in the main hand
                    event.getEntity().getEquipment().setItemInMainHand(crossbowItemStack.setDurability(Material.CROSSBOW.getMaxDurability()).toItemStack());
                }

                String weaponOwner = crossbowItemStack.getTagsManager().getString("weapon_owner", "");
                if (!weaponOwner.equals("") && !weaponOwner.equals(player.getName())) {
                    player.sendActionBar(SiText.string("Вы не являетесь владельцем оружия!", SiPalette.Interface.red).toComponent());
                    return;
                }

                if (SiTickCooldown.hasCooldown(weaponItemType.getCooldownName(player))) {
                    player.sendActionBar(SiTickCooldown.getFormattedRemainingTime(weaponItemType.getCooldownName(player)).setStyle(SiPalette.Interface.red).toComponent());
                    return;
                }

                SiAmmo selectedAmmo = null;

                for (SiAmmo ammo : weaponItemType.getAmmo()) {
                    if (ammo.contains(player.getInventory())) {
                        selectedAmmo = ammo;
                        break;
                    }
                }
                if (selectedAmmo == null && event.getEntity() instanceof Player) {
                    if (weaponItemType.getNotEnoughAmmoSound() != null) {
                        weaponItemType.getNotEnoughAmmoSound().emit(player.getLocation());
                    }
                    if (weaponItemType.getNotEnoughAmmoMessage() != null) {
                        player.sendActionBar(weaponItemType.getNotEnoughAmmoMessage().toComponent());
                    }
                    return;
                }
                if (selectedAmmo == null) return;
                SiTickCooldown.setCooldown(weaponItemType.getCooldownName(player), weaponItemType.getCooldown());
                selectedAmmo.removeAndShoot(player.getInventory(), player.getLocation().clone().add(0, 1.45, 0), player.getLocation().getDirection(), player.getUniqueId().toString(), crossbowItemStack);
            }
        });
    }
}
