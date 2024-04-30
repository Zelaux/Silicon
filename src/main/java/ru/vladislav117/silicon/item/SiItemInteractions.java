package ru.vladislav117.silicon.item;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Collection;

/**
 * Взаимодействия предмета.
 */
public interface SiItemInteractions {
    /**
     * Клик правой кнопкой мыши.
     *
     * @param player    Игрок
     * @param shifted   Стоит ли игрок на шифте
     * @param itemStack Стак, которым кликнули
     * @param event     Событие
     */
    void rightClickAny(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event);

    /**
     * Клик правой кнопкой мыши по воздуху.
     *
     * @param player    Игрок
     * @param shifted   Стоит ли игрок на шифте
     * @param itemStack Стак, которым кликнули
     * @param event     Событие
     */
    void rightClickAir(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event);

    /**
     * Клик правой кнопкой мыши по блоку.
     *
     * @param player    Игрок
     * @param shifted   Стоит ли игрок на шифте
     * @param itemStack Стак, которым кликнули
     * @param block     Блок, по которому кликнули
     * @param event     Событие
     */
    void rightClickBlock(Player player, boolean shifted, SiItemStack itemStack, Block block, PlayerInteractEvent event);

    /**
     * Клик левой кнопкой мыши.
     *
     * @param player    Игрок
     * @param shifted   Стоит ли игрок на шифте
     * @param itemStack Стак, которым кликнули
     * @param event     Событие
     */
    void leftClickAny(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event);

    /**
     * Клик левой кнопкой мыши по воздуху.
     *
     * @param player    Игрок
     * @param shifted   Стоит ли игрок на шифте
     * @param itemStack Стак, которым кликнули
     * @param event     Событие
     */
    void leftClickAir(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event);

    /**
     * Клик левой кнопкой мыши по блоку.
     *
     * @param player    Игрок
     * @param shifted   Стоит ли игрок на шифте
     * @param itemStack Стак, которым кликнули
     * @param block     Блок, по которому кликнули
     * @param event     Событие
     */
    void leftClickBlock(Player player, boolean shifted, SiItemStack itemStack, Block block, PlayerInteractEvent event);

    /**
     * LivingEntity наносит урон по Entity.
     *
     * @param damager   Атакующий
     * @param itemStack Стак, которым наносят урон
     * @param victim    Жертва
     * @param event     Событие
     */
    void livingEntityDamageEntity(LivingEntity damager, SiItemStack itemStack, Entity victim, EntityDamageByEntityEvent event);

    /**
     * LivingEntity наносит урон по LivingEntity.
     *
     * @param damager   Атакующий
     * @param itemStack Стак, которым наносят урон
     * @param victim    Жертва
     * @param event     Событие
     */
    void livingEntityDamageLivingEntity(LivingEntity damager, SiItemStack itemStack, LivingEntity victim, EntityDamageByEntityEvent event);

    /**
     * LivingEntity наносит урон по игроку.
     *
     * @param damager   Атакующий
     * @param itemStack Стак, которым наносят урон
     * @param victim    Жертва
     * @param event     Событие
     */
    void livingEntityDamagePlayer(LivingEntity damager, SiItemStack itemStack, Player victim, EntityDamageByEntityEvent event);

    /**
     * Игрок наносит урон по Entity.
     *
     * @param damager   Атакующий
     * @param itemStack Стак, которым наносят урон
     * @param victim    Жертва
     * @param event     Событие
     */
    void playerDamageEntity(Player damager, SiItemStack itemStack, Entity victim, EntityDamageByEntityEvent event);

    /**
     * Игрок наносит урон по LivingEntity.
     *
     * @param damager   Атакующий
     * @param itemStack Стак, которым наносят урон
     * @param victim    Жертва
     * @param event     Событие
     */
    void playerDamageLivingEntity(Player damager, SiItemStack itemStack, LivingEntity victim, EntityDamageByEntityEvent event);

    /**
     * Игрок наносит урон по игроку.
     *
     * @param damager   Атакующий
     * @param itemStack Стак, которым наносят урон
     * @param victim    Жертва
     * @param event     Событие
     */
    void playerDamagePlayer(Player damager, SiItemStack itemStack, Player victim, EntityDamageByEntityEvent event);

    /**
     * Игрок потребляет предмет (ест или пьёт).
     *
     * @param player    Игрок
     * @param itemStack Предмет
     * @param event     Событие
     */
    void consume(Player player, SiItemStack itemStack, PlayerItemConsumeEvent event);

    /**
     * Взрывное зелье разбивается.
     *
     * @param itemStack Предмет
     * @param entities Затронутые сущности
     * @param event Событие
     */
    void potionSplash(SiItemStack itemStack, Collection<LivingEntity> entities, ThrownPotion potion, PotionSplashEvent event);

    /**
     * Взаимодействия для более удобного переопределения методов.
     */
    class StaticInteractions implements SiItemInteractions {
        @Override
        public void rightClickAny(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event) {

        }

        @Override
        public void rightClickAir(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event) {

        }

        @Override
        public void rightClickBlock(Player player, boolean shifted, SiItemStack itemStack, Block block, PlayerInteractEvent event) {

        }

        @Override
        public void leftClickAny(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event) {

        }

        @Override
        public void leftClickAir(Player player, boolean shifted, SiItemStack itemStack, PlayerInteractEvent event) {

        }

        @Override
        public void leftClickBlock(Player player, boolean shifted, SiItemStack itemStack, Block block, PlayerInteractEvent event) {

        }

        @Override
        public void livingEntityDamageEntity(LivingEntity damager, SiItemStack itemStack, Entity victim, EntityDamageByEntityEvent event) {

        }

        @Override
        public void livingEntityDamageLivingEntity(LivingEntity damager, SiItemStack itemStack, LivingEntity victim, EntityDamageByEntityEvent event) {

        }

        @Override
        public void livingEntityDamagePlayer(LivingEntity damager, SiItemStack itemStack, Player victim, EntityDamageByEntityEvent event) {

        }

        @Override
        public void playerDamageEntity(Player damager, SiItemStack itemStack, Entity victim, EntityDamageByEntityEvent event) {

        }

        @Override
        public void playerDamageLivingEntity(Player damager, SiItemStack itemStack, LivingEntity victim, EntityDamageByEntityEvent event) {

        }

        @Override
        public void playerDamagePlayer(Player damager, SiItemStack itemStack, Player victim, EntityDamageByEntityEvent event) {

        }

        @Override
        public void consume(Player player, SiItemStack itemStack, PlayerItemConsumeEvent event) {

        }

        @Override
        public void potionSplash(SiItemStack itemStack, Collection<LivingEntity> entities, ThrownPotion potion, PotionSplashEvent event) {

        }
    }
}
