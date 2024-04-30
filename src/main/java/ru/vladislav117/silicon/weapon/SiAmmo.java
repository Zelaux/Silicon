package ru.vladislav117.silicon.weapon;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.filter.item.SiItemFilter;
import ru.vladislav117.silicon.filter.item.SiTypeItemFilter;
import ru.vladislav117.silicon.filter.liquid.SiLiquidFilter;
import ru.vladislav117.silicon.filter.liquid.SiUniversalLiquidFilter;
import ru.vladislav117.silicon.inventory.SiInventoryManager;
import ru.vladislav117.silicon.inventory.SiLiquidInventoryManager;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.liquid.SiLiquidType;
import ru.vladislav117.silicon.sound.SiSoundEmitter;
import ru.vladislav117.silicon.weapon.bullet.SiBulletType;
import ru.vladislav117.silicon.weapon.item.SiAmmoMagazineItemType;

/**
 * Боеприпас для оружия.
 */
public interface SiAmmo {
    /**
     * Проверка, содержит ли инвентарь боеприпас.
     *
     * @param inventory Инвентарь
     * @return Содержит ли инвентарь боеприпас.
     */
    boolean contains(Inventory inventory);

    /**
     * Удаление боеприпаса из инвентаря и выстрел.
     *
     * @param inventory Инвентарь
     * @param location  Позиция выстрела
     * @param direction Направление выстрела
     * @param shooter   Стрелок
     * @param weapon    Оружие
     */
    void removeAndShoot(Inventory inventory, Location location, Vector direction, String shooter, SiItemStack weapon);

    /**
     * Абстрактный боеприпас, хранящий звук выстрела.
     */
    abstract class SoundAmmo implements SiAmmo {
        protected SiSoundEmitter soundEmitter = null;

        /**
         * Получение звука выстрела.
         *
         * @return Звук выстрела.
         */
        @Nullable
        public SiSoundEmitter getSoundEmitter() {
            return soundEmitter;
        }

        /**
         * Установка звука выстрела.
         *
         * @param soundEmitter Звук выстрела
         * @return Этот же боеприпас.
         */
        public SoundAmmo setSoundEmitter(SiSoundEmitter soundEmitter) {
            this.soundEmitter = soundEmitter;
            return this;
        }
    }

    /**
     * Боеприпас-предмет.
     */
    class ItemType extends SoundAmmo {
        protected SiItemFilter filter;
        protected SiBulletType bulletType;

        /**
         * Создание нового боеприпаса.
         *
         * @param itemType   Тип предмета
         * @param bulletType Тип снаряда
         */
        public ItemType(SiItemType itemType, SiBulletType bulletType) {
            filter = new SiTypeItemFilter(itemType);
            this.bulletType = bulletType;
        }

        /**
         * Создание нового боеприпаса.
         *
         * @param material   Тип предмета
         * @param bulletType Тип снаряда
         */
        public ItemType(Material material, SiBulletType bulletType) {
            filter = new SiTypeItemFilter(material);
            this.bulletType = bulletType;
        }

        @Override
        public boolean contains(Inventory inventory) {
            return new SiInventoryManager(inventory).containsAtLeast(filter, 1);
        }

        @Override
        public void removeAndShoot(Inventory inventory, Location location, Vector direction, String shooter, SiItemStack weapon) {
            new SiInventoryManager(inventory).removeSuitable(filter, 1);
            bulletType.spawn(location, direction, shooter);
            if (soundEmitter != null) soundEmitter.emit(location);
        }
    }

    /**
     * Боеприпас-магазин.
     */
    class AmmoMagazine extends ItemType {
        /**
         * Создание нового боеприпаса.
         *
         * @param itemType   Тип магазина
         * @param bulletType Тип снаряда
         */
        public AmmoMagazine(SiAmmoMagazineItemType itemType, SiBulletType bulletType) {
            super(itemType, bulletType);
            this.filter = new SiItemFilter() {
                @Override
                public boolean testItemStack(ItemStack itemStack) {
                    SiItemStack stack = new SiItemStack(itemStack);
                    if (!stack.getItemType().equals(itemType)) return false;
                    return stack.getTagsManager().getInteger("ammo") > 0;
                }
            };
        }

        @Override
        public void removeAndShoot(Inventory inventory, Location location, Vector direction, String shooter, SiItemStack weapon) {
            new SiInventoryManager(inventory).modifyFirstSuitable(filter, itemStack -> {
                itemStack.getTagsManager().setInteger("ammo", itemStack.getTagsManager().getInteger("ammo") - 1);
                itemStack.updateDisplay();
                return itemStack;
            });
            bulletType.spawn(location, direction, shooter);
            if (soundEmitter != null) soundEmitter.emit(location);
        }
    }

    /**
     * Боеприпас-жидкость.
     */
    class LiquidType extends SoundAmmo {
        protected SiLiquidFilter filter;
        protected int volume;
        protected SiBulletType bulletType;

        /**
         * Создание нового боеприпаса.
         *
         * @param liquidType Тип жидкости
         * @param volume     Объём для одного выстрела
         * @param bulletType Тип снаряда
         */
        public LiquidType(SiLiquidType liquidType, int volume, SiBulletType bulletType) {
            filter = new SiUniversalLiquidFilter(liquidType);
            this.volume = volume;
            this.bulletType = bulletType;
        }

        @Override
        public boolean contains(Inventory inventory) {
            return new SiLiquidInventoryManager(inventory).canBeRemoved(filter, volume);
        }

        @Override
        public void removeAndShoot(Inventory inventory, Location location, Vector direction, String shooter, SiItemStack weapon) {
            new SiLiquidInventoryManager(inventory).removeSuitable(filter, volume);
            bulletType.spawn(location, direction, shooter);
            if (soundEmitter != null) soundEmitter.emit(location);
        }
    }
}
