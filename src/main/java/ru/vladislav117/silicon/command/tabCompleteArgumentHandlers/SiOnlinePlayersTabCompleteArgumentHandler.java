package ru.vladislav117.silicon.command.tabCompleteArgumentHandlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.vladislav117.silicon.command.SiCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Подстановка аргументов с типом ника игрока онлайн.
 */
public class SiOnlinePlayersTabCompleteArgumentHandler implements SiCommand.Handler.TabCompleteArgumentHandler {
    public static final SiOnlinePlayersTabCompleteArgumentHandler onlinePlayers = new SiOnlinePlayersTabCompleteArgumentHandler();

    @Override
    public List<String> tabComplete(int argumentIndex, String argument, CommandSender sender, String alias, String[] args, Location location) {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) playerNames.add(player.getName());
        return playerNames;
    }
}
