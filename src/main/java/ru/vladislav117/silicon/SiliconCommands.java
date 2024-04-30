package ru.vladislav117.silicon;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.command.SiCommand;
import ru.vladislav117.silicon.command.tabCompleteArgumentHandlers.SiIntegerTabCompleteArgumentHandler;
import ru.vladislav117.silicon.command.tabCompleteArgumentHandlers.SiOnlinePlayersTabCompleteArgumentHandler;
import ru.vladislav117.silicon.economy.SiCurrencies;
import ru.vladislav117.silicon.economy.SiCurrency;
import ru.vladislav117.silicon.effect.SiEffectType;
import ru.vladislav117.silicon.effect.SiEffectTypes;
import ru.vladislav117.silicon.inventory.SiPlayerInventoryUtils;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.item.SiItemTypes;
import ru.vladislav117.silicon.region.SiRegion;
import ru.vladislav117.silicon.region.SiRegionSet;
import ru.vladislav117.silicon.region.SiRegions;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextContainer;
import ru.vladislav117.silicon.text.style.SiStyle;
import ru.vladislav117.silicon.world.SiWorld;
import ru.vladislav117.silicon.world.SiWorlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Команды фреймворка.
 */
public class SiliconCommands {
    /**
     * Инициализировать команды фреймворка.
     */
    public static void init() {
        new SiCommand("tpworld", List.of("worldtp", "tpw", "wtp"), "admin", new SiCommand.Handler((sender, commandLabel, args) -> {
            if (args.length == 0) {
                SiText.string("Не указан игрок!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 1) {
                SiText.string("Не указан мир!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length < 5) {
                SiText.string("Не указаны все координаты!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                SiText.string("Игрок не на сервере!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            SiWorld world = SiWorlds.all.get(args[1]);
            if (world == null) {
                SiText.string("Мир не найден!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            int x, y, z;
            try {
                x = Integer.parseInt(args[2]);
                y = Integer.parseInt(args[3]);
                z = Integer.parseInt(args[4]);
            } catch (Exception exception) {
                SiText.string("Координаты должны быть числом!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            target.teleport(new Location(world.getWorld(), x, y, z));
            return true;
        }) {{
            addTabCompleteArgumentHandler(0, (argumentIndex, argument, sender, alias, args, location) -> {
                ArrayList<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) playerNames.add(player.getName());
                return playerNames;
            });
            addTabCompleteArgumentHandler(1, (argumentIndex, argument, sender, alias, args, location) -> List.of(SiWorlds.all.getAllNames()));
            addTabCompleteArgumentHandler(2, (argumentIndex, argument, sender, alias, args, location) -> new ArrayList<>() {{
                for (int i = 0; i < 10; i++) add(argument + i);
            }});
            addTabCompleteArgumentHandler(3, (argumentIndex, argument, sender, alias, args, location) -> new ArrayList<>() {{
                for (int i = 0; i < 10; i++) add(argument + i);
            }});
            addTabCompleteArgumentHandler(4, (argumentIndex, argument, sender, alias, args, location) -> new ArrayList<>() {{
                for (int i = 0; i < 10; i++) add(argument + i);
            }});
        }});

        new SiCommand("giveitemstack", List.of("gis", "gci", "gic", "itemget"), "admin", new SiCommand.Handler((sender, commandLabel, args) -> {
            if (args.length == 0) {
                SiText.string("Не указан получатель предмета").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 1) {
                SiText.string("Не указан ID предмета").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            int amount = 1;
            if (args.length >= 3) {
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (Exception exception) {
                    SiText.string("Количество должно быть числом").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
                if (amount <= 0) {
                    SiText.string("Количество должно быть больше нуля").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                SiText.string("Игрок с таким ником не найден").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            HashMap<String, Object> tags = new HashMap<>();
            for (int argumentIndex = 3; argumentIndex < args.length; argumentIndex++) {
                String[] argumentParts = args[argumentIndex].split("=");
                if (argumentParts.length <= 1) {
                    continue;
                }
                Object value;
                try {
                    value = Integer.parseInt(argumentParts[1]);
                } catch (Exception exception) {
                    try {
                        value = Double.parseDouble(argumentParts[1]);
                    } catch (Exception exception1) {
                        try {
                            value = Float.parseFloat(argumentParts[1]);
                        } catch (Exception exception2) {
                            value = argumentParts[1];
                        }
                    }
                }
                tags.put(argumentParts[0], value);
            }
            Player target = Bukkit.getPlayer(args[0]);
            SiItemType itemType = SiItemTypes.all.get(args[1], SiItemTypes.unknown);
            if (itemType.isUnknown() && !itemType.getName().equals(args[1])) {
                SiText.string("Предмета с таким ID не существует").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            SiPlayerInventoryUtils.give(target, itemType.buildItemStack(amount, tags).toItemStack());
            return true;
        }) {{
            addTabCompleteArgumentHandler(0, (argumentIndex, argument, sender, alias, args, location) -> {
                ArrayList<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) playerNames.add(player.getName());
                return playerNames;
            });
            addTabCompleteArgumentHandler(1, (argumentIndex, argument, sender, alias, args, location) -> List.of(SiItemTypes.all.getAllNames()));
            addTabCompleteArgumentHandler(2, (argumentIndex, argument, sender, alias, args, location) -> new ArrayList<>() {{
                for (int i = 0; i < 10; i++) add(argument + i);
            }});
            addOtherTabCompleteArgumentHandler((argumentIndex, argument, sender, alias, args, location) -> SiItemTypes.all.get(args[1], SiItemTypes.unknown).getDefaultTagsHints());
        }});

        new SiCommand("items", List.of("itemslist"), "admin", new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    SiText.string("Не указан игрок, которому нужно открыть меню.", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    SiText.string("Игрок не найден!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
                SiText.string("Вы открыли меню для игрока " + player.getName(), SiPalette.Interface.yellow).toMessageTask().addPlayer(player).send();
                SiItemTypes.menu.open(player);
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                SiItemTypes.menu.open(player);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                SiText.string("Игрок не найден!", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            SiItemTypes.menu.open(target);
            SiText.string("Вы открыли меню для игрока " + target.getName(), SiPalette.Interface.yellow).toMessageTask().addPlayer(player).send();
            return true;
        }) {{
            addTabCompleteArgumentHandler(0, (argumentIndex, argument, sender, alias, args, location) -> {
                ArrayList<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) playerNames.add(player.getName());
                return playerNames;
            });
        }});

        new SiCommand("givecustomeffect", List.of("effectgive", "giveeffect", "gce"), "admin", new SiCommand.Handler((sender, commandLabel, args) -> {
            if (args.length == 0) {
                SiText.string("Не указан целевой игрок").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 1) {
                SiText.string("Не указан ID эффекта").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 2) {
                SiText.string("Не указано время эффекта").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            int ticks = 1;
            try {
                ticks = Integer.parseInt(args[2]);
            } catch (Exception exception) {
                SiText.string("Тики должны быть числом").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (ticks <= 0) {
                SiText.string("Тики должны быть больше нуля").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            double power = 1;
            if (args.length >= 4) {
                try {
                    power = Double.parseDouble(args[3]);
                } catch (Exception exception) {
                    SiText.string("Мощность должна быть числом").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
                if (power <= 0) {
                    SiText.string("Мощность должна быть больше нуля").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
            }
            SiEffectType effectType = SiEffectTypes.all.get(args[1]);
            if (effectType == null) {
                SiText.string("Эффект не найден").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                SiText.string("Игрок не найден").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            Player initiator = null;
            if (sender instanceof Player) initiator = (Player) sender;
            effectType.buildEffect(ticks, power).give(player, initiator);
            return true;
        }) {{
            addTabCompleteArgumentHandler(0, (argumentIndex, argument, sender, alias, args, location) -> {
                ArrayList<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) playerNames.add(player.getName());
                return playerNames;
            });
            addTabCompleteArgumentHandler(1, (argumentIndex, argument, sender, alias, args, location) -> List.of(SiEffectTypes.all.getAllNames()));
            addTabCompleteArgumentHandler(2, (argumentIndex, argument, sender, alias, args, location) -> new ArrayList<>() {{
                for (int i = 0; i < 10; i++) add(argument + i);
            }});
            addTabCompleteArgumentHandler(3, (argumentIndex, argument, sender, alias, args, location) -> new ArrayList<>() {{
                for (int i = 0; i < 10; i++) add(argument + i);
            }});
        }});

        new SiCommand("economy", List.of("eco"), "admin", new SiCommand.Handler((sender, commandLabel, args) -> {
            if (args.length == 0) {
                SiText.string("Не указано действие").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 1) {
                SiText.string("Не указана валюта").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 2) {
                SiText.string("Не указан аккаунт").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if ((args[0].equals("set") || args[0].equals("add")) && args.length == 3) {
                SiText.string("Не указано количество").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            SiCurrency currency = SiCurrencies.all.get(args[1]);
            if (currency == null) {
                SiText.string("Указанной валюты не существует").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args[0].equals("get")) {
                int balance = currency.getBalance(args[2]);
                SiText.container().addString("Баланс аккаунта " + args[2] + ": ", SiPalette.Interface.green).addString(balance + currency.getSymbol(), SiPalette.Interface.yellow).toMessageTask().addOtherReceiver(sender).send();
                return true;
            }
            if (args[0].equals("set")) {
                int amount;
                try {
                    amount = Integer.parseInt(args[3]);
                } catch (NumberFormatException exception) {
                    SiText.string("Количество должно быть числом").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
                currency.setBalance(args[2], amount);
                SiText.string("Баланс " + args[2] + " обновлён").setStyle(SiPalette.Interface.green).toMessageTask().addOtherReceiver(sender).send();
                return true;
            }
            if (args[0].equals("add")) {
                int amount;
                try {
                    amount = Integer.parseInt(args[3]);
                } catch (NumberFormatException exception) {
                    SiText.string("Количество должно быть числом").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                    return false;
                }
                currency.addBalance(args[2], amount);
                SiText.string("Баланс " + args[2] + " обновлён").setStyle(SiPalette.Interface.green).toMessageTask().addOtherReceiver(sender).send();
                return true;
            }
            SiText.string("Такого действия не существует").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
            return false;
        }) {{
            addTabCompleteArgumentHandler(0, (argumentIndex, argument, sender, alias, args, location) -> List.of("get", "set", "add"));
            addTabCompleteArgumentHandler(1, (argumentIndex, argument, sender, alias, args, location) -> List.of(SiCurrencies.all.getAllNames()));
            addTabCompleteArgumentHandler(2, SiOnlinePlayersTabCompleteArgumentHandler.onlinePlayers);
            addTabCompleteArgumentHandler(3, (argumentIndex, argument, sender, alias, args, location) -> {
                if (args[0].equals("set") || args[0].equals("add"))
                    return SiIntegerTabCompleteArgumentHandler.any.tabComplete(argumentIndex, argument, sender, alias, args, location);
                return new ArrayList<>();
            });
        }});

        new SiCommand("region", List.of("rg"), new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player player)) {
                SiText.string("Команду может отправлять только игрок", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 0) {
                SiRegionSet regionSet = SiRegions.getRegionsAt(player.getLocation());
                if (regionSet.getRegions().isEmpty()) {
                    SiText.string("В этом месте нет регионов", SiPalette.Interface.gold).toMessageTask().addOtherReceiver(sender).send();
                    return true;
                }
                if (regionSet.getRegions().size() == 1) {
                    regionSet.getRegions().get(0).openMenu(player);
                    return true;
                }
                SiTextContainer message = SiText.container().addString("В этом месте несколько регионов, нажмите на необходимый:", SiPalette.Interface.gold).addNewLine();
                regionSet.forEach(region -> {
                    // TODO: 14.03.2024 При добавлении ClickEvent в SiText заменить эту часть
                    message.addComponent(SiText.container().addString("* ", SiPalette.Interface.green).addString(region.getName(), SiStyle.empty().setColor(SiPalette.Interface.green).setUnderlined(true)).addNewLine().toComponent().clickEvent(ClickEvent.callback(audience -> {
                        region.openMenu(player);
                    })));
                });
                message.toMessageTask().addPlayer(player).send();
                return true;
            }
            if (args.length == 1){
                SiRegion region = SiRegions.getRegion(args[0]);
                if (region == null){
                    SiText.string("Регион не найден", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                    return false;
                }
                region.buildMenu(region.isOwner(player)).open(player);
            }
            if (args.length < 3) {
                SiText.string("Вы не указали регион, действие и/или ник игрока", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            SiRegion region = SiRegions.getRegion(args[0]);
            if (region == null) {
                SiText.string("Регион не найден", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            if (!region.isOwner(player)) {
                SiText.string("Вы не являетесь владельцем этого региона", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            if (args[1].equals("addmember")) {
                if (region.isMember(args[2])){
                    SiText.string("Игрок уже в регионе", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                    return false;
                }
                region.getMembers().add(args[2]);
                region.save();
                SiText.string("Вы добавили игрока " + args[2] + " в регион", SiPalette.Interface.green).toMessageTask().addPlayer(player).send();
                return true;
            }
            if (args[1].equals("removemember")) {
                if (!region.isMember(args[2])){
                    SiText.string("Игрок не в регионе", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                    return false;
                }
                region.getMembers().remove(args[2]);
                region.getOwners().remove(args[2]);
                region.save();
                SiText.string("Вы удалили игрока " + args[2] + " из региона", SiPalette.Interface.yellow).toMessageTask().addPlayer(player).send();
                return false;
            }
            if (args[1].equals("addowner")) {
                if (!region.isMember(args[2])){
                    SiText.string("Игрок не в регионе", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                    return false;
                }
                if (region.isOwner(args[2])){
                    SiText.string("Игрок уже совладелец", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                    return false;
                }
                region.getOwners().add(args[2]);
                region.save();
                SiText.string("Вы назначили игрока " + args[2] + " совладельцем региона", SiPalette.Interface.green).toMessageTask().addPlayer(player).send();
                return false;
            }
            if (args[1].equals("removeowner")) {
                if (!region.isOwner(args[2])){
                    SiText.string("Игрок не совладелец", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                    return false;
                }
                region.getOwners().remove(args[2]);
                region.save();
                SiText.string("Вы удалили игрока " + args[2] + " из владельцев региона", SiPalette.Interface.yellow).toMessageTask().addPlayer(player).send();
                return false;
            }
            SiText.string("Выбранного действия не существует", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
            return false;
        }) {{
            addTabCompleteArgumentHandler(0, (argumentIndex, argument, sender, alias, args, location) -> {
//                if (!(sender instanceof Player player)) return new ArrayList<>();
//                ArrayList<String> regions = new ArrayList<>();
//                SiRegions.getRegionNames().forEach(regionName -> {
//                    SiRegion region = SiRegions.getRegion(regionName);
//                    if (region == null) return;
//                    if (region.isOwner(player)) regions.add(region.getName());
//                });
//                return regions;
                return new ArrayList<>(SiRegions.getRegionNames());
            });
            addTabCompleteArgumentHandler(1, (argumentIndex, argument, sender, alias, args, location) -> List.of("addmember", "removemember", "addowner", "removeowner"));
            addTabCompleteArgumentHandler(2, (argumentIndex, argument, sender, alias, args, location) -> {
                if (!(sender instanceof Player player)) return new ArrayList<>();
                SiRegion region = SiRegions.getRegion(args[0]);
                if (region == null) return new ArrayList<>();
                if (!region.isOwner(player)) return new ArrayList<>();
                if (args[1].equals("addmember")) {
                    ArrayList<String> players = new ArrayList<>();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (!region.isMember(onlinePlayer)) players.add(onlinePlayer.getName());
                    }
                    return players;
                }
                if (args[1].equals("removemember")) {
                    return new ArrayList<>(region.getMembers());
                }
                if (args[1].equals("addowner")) {
                    ArrayList<String> players = new ArrayList<>();
                    for (String member : region.getMembers()) {
                        if (!region.isOwner(member)) players.add(member);
                    }
                    return players;
                }
                if (args[1].equals("removeowner")) {
                    return new ArrayList<>(region.getOwners());
                }
                return new ArrayList<>();
            });
        }});
    }
}
