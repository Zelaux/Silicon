package ru.vladislav117.silicon.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.vladislav117.silicon.function.SiFilterFunction;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Запланированное сообщение.
 */
public class SiMessageTask {
    protected SiTextLike message;
    protected ArrayList<UUID> receivers = new ArrayList<>();
    protected ArrayList<Audience> otherReceivers = new ArrayList<>();

    /**
     * Создание нового запланированного сообщения.
     *
     * @param message Текст сообщения
     */
    public SiMessageTask(SiTextLike message) {
        this.message = message;
    }

    /**
     * Отправить сообщение всем, кто был указан как получатель.
     *
     * @return Это же сообщение.
     */
    public SiMessageTask send() {
        Component componentMessage = message.toComponent();
        for (UUID receiver : receivers) {
            Player player = Bukkit.getPlayer(receiver);
            if (player != null) player.sendMessage(componentMessage);
        }
        for (Audience receiver : otherReceivers) {
            receiver.sendMessage(componentMessage);
        }
        return this;
    }

    /**
     * Добавить получателя другого типа.
     *
     * @param audience Получатель
     * @return Это же сообщение.
     */
    public SiMessageTask addOtherReceiver(Audience audience) {
        otherReceivers.add(audience);
        return this;
    }

    /**
     * Добавить получателя.
     *
     * @param uuid UUID получателя
     * @return Это же сообщение.
     */
    public SiMessageTask addPlayer(UUID uuid) {
        receivers.add(uuid);
        return this;
    }

    /**
     * Добавить получателя.
     *
     * @param name Имя получателя
     * @return Это же сообщение.
     */
    public SiMessageTask addPlayer(String name) {
        receivers.add(Bukkit.getPlayerUniqueId(name));
        return this;
    }

    /**
     * Добавить получателя.
     *
     * @param player Получатель
     * @return Это же сообщение.
     */
    public SiMessageTask addPlayer(Player player) {
        receivers.add(player.getUniqueId());
        return this;
    }

    /**
     * Добавить всех игроков на сервере как получателей.
     *
     * @return Это же сообщение.
     */
    public SiMessageTask addAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            receivers.add(player.getUniqueId());
        }
        return this;
    }

    /**
     * Добавить всех игроков на сервере как получателей, но с условием.
     *
     * @param filter Фильтр игроков
     * @return Это же сообщение.
     */
    public SiMessageTask addAllPlayersIf(SiFilterFunction<Player> filter) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (filter.isSuitable(player)) receivers.add(player.getUniqueId());
        }
        return this;
    }

    /**
     * Добавить всех игроков в мире как получателей.
     *
     * @param world Мир
     * @return Это же сообщение.
     */
    public SiMessageTask addAllPlayersInWorld(World world) {
        for (Player player : world.getPlayers()) {
            receivers.add(player.getUniqueId());
        }
        return this;
    }

    /**
     * Добавить всех игроков в мире как получателей, но с условием.
     *
     * @param world  Мир
     * @param filter Фильтр игроков
     * @return Это же сообщение.
     */
    public SiMessageTask addAllPlayersInWorldIf(World world, SiFilterFunction<Player> filter) {
        for (Player player : world.getPlayers()) {
            if (filter.isSuitable(player)) receivers.add(player.getUniqueId());
        }
        return this;
    }

    /**
     * Добавить всех игроков в радиусе как получателей.
     *
     * @param location Точка отсчёта
     * @param radius   Радиус (квадрат)
     * @return Это же сообщение.
     */
    public SiMessageTask addPlayersInRadius(Location location, double radius) {
        for (Player player : location.getNearbyEntitiesByType(Player.class, radius)) {
            receivers.add(player.getUniqueId());
        }
        return this;
    }

    /**
     * Добавить всех игроков в радиусе как получателей, но с условием.
     *
     * @param location Точка отсчёта
     * @param radius   Радиус (квадрат)
     * @param filter   Фильтр игроков
     * @return Это же сообщение.
     */
    public SiMessageTask addPlayersInRadiusIf(Location location, double radius, SiFilterFunction<Player> filter) {
        for (Player player : location.getNearbyEntitiesByType(Player.class, radius)) {
            if (filter.isSuitable(player)) receivers.add(player.getUniqueId());
        }
        return this;
    }
}
