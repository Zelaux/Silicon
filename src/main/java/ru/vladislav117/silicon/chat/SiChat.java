package ru.vladislav117.silicon.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.emoji.SiEmojis;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.function.SiConverterFunction;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextContainer;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.ArrayList;

/**
 * Чат.
 */
public final class SiChat {
    public static SiTextLike globalChatPrefix = SiText.container().addString("[", SiPalette.Interface.yellow).addString("Г", SiPalette.Interface.green).addString("]", SiPalette.Interface.yellow);
    public static SiTextLike localChatPrefix = SiText.container().addString("[", SiPalette.Interface.yellow).addString("Л", SiPalette.Interface.gold).addString("]", SiPalette.Interface.yellow);
    static ArrayList<SiConverterFunction<Player, ArrayList<SiTextLike>>> prefixHandlers = new ArrayList<>();

    /**
     * Приём и обработка сообщения.
     *
     * @param messageText Текст сообщения
     * @param player      Игрок, который отправил сообщение
     */
    public static void handleMessage(Component messageText, Player player) {
        TextComponent message = (TextComponent) SiEmojis.handleMessage(messageText);
        String rawMessage = message.content();
        Bukkit.getLogger().info(player.getName() + ": " + rawMessage);
        boolean global = rawMessage.startsWith("!");
        if (global) {
            message = message.content(rawMessage.substring(1));
        }
        SiTextContainer fullMessage = SiText.container();
        if (global) {
            fullMessage.addText(globalChatPrefix);
        } else {
            fullMessage.addText(localChatPrefix);
        }
        fullMessage.addSpace();
        ArrayList<SiTextLike> prefixes = new ArrayList<>();
        for (SiConverterFunction<Player, ArrayList<SiTextLike>> prefixHandler : prefixHandlers) {
            prefixes.addAll(prefixHandler.convert(player));
        }
        for (SiTextLike prefix : prefixes) {
            fullMessage.addText(prefix).addSpace();
        }
        fullMessage.addString("<", SiPalette.Interface.gray).addString(player.getName()).addString(">", SiPalette.Interface.gray).addString(": ", SiPalette.Interface.gray);
        fullMessage.addComponent(message);

        Component fullMessageComponent = fullMessage.toComponent();
        int receivedMessages = 0;
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (global || (player.getWorld() == receiver.getWorld() && player.getLocation().distance(receiver.getLocation()) <= 128)) {
                receiver.sendMessage(fullMessageComponent);
                if (receiver != player) receivedMessages++;
            }
        }
        if (receivedMessages == 0) {
            SiText.string("Никто вас не услышал", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
            if (!global) {
                SiText.string("Чтобы написать в глобальный чат, поставьте \"!\" перед сообщением", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
            }
        }
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onAsyncChatEvent(AsyncChatEvent event) {
                if (event.isCancelled()) return;
                event.setCancelled(true);
                handleMessage(event.message(), event.getPlayer());
            }
        });
    }
}
