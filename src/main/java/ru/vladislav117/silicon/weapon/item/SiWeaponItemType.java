package ru.vladislav117.silicon.weapon.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.sound.SiSoundEmitter;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.weapon.SiAmmo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Оружие.
 */
public class SiWeaponItemType extends SiItemType {
    protected ArrayList<SiAmmo> ammo = new ArrayList<>();
    @Nullable
    protected SiTextLike notEnoughAmmoMessage = null;
    @Nullable
    protected SiSoundEmitter notEnoughAmmoSound = null;
    protected String cooldownName = "shooting";
    protected int cooldown = 0;

    /**
     * Создание нового оружия.
     *
     * @param name Имя оружия
     */
    public SiWeaponItemType(String name) {
        super(name, Material.CROSSBOW);
        flags.add(ItemFlag.HIDE_ITEM_SPECIFICS);
        defaultTags.put("weapon_owner", "");
    }

    /**
     * Получение боеприпасов.
     *
     * @return Боеприпасы оружия.
     */
    public ArrayList<SiAmmo> getAmmo() {
        return ammo;
    }

    /**
     * Добавление боеприпасов.
     *
     * @param ammo Боеприпасы
     * @return Это же оружие.
     */
    public SiWeaponItemType addAmmo(SiAmmo ammo) {
        this.ammo.add(ammo);
        return this;
    }

    /**
     * Получение сообщения о нехватке боеприпасов.
     *
     * @return Сообщение о нехватке боеприпасов или null, если не задано.
     */
    @Nullable
    public SiTextLike getNotEnoughAmmoMessage() {
        return notEnoughAmmoMessage;
    }

    /**
     * Получение звука нехватки боеприпасов.
     *
     * @return Звука нехватки боеприпасов или null, если не задан.
     */
    @Nullable
    public SiSoundEmitter getNotEnoughAmmoSound() {
        return notEnoughAmmoSound;
    }

    /**
     * Получение названия кулдауна с учётом имени игрока.
     *
     * @param player Игрок
     * @return Название кулдауна с учётом имени игрока.
     */
    public String getCooldownName(Player player) {
        return player.getName() + "." + cooldownName;
    }

    /**
     * Получение кулдауна в тиках.
     *
     * @return Кулдаун в тиках.
     */
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public SiItemStack buildItemStack(int amount, HashMap<String, Object> tags) {
        return super.buildItemStack(amount, tags).setCrossbowProjectile(new ItemStack(Material.ARROW));
    }
}
