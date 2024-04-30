package ru.vladislav117.silicon.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.permission.SiPermission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Команда, имеющая обработчик, псевдонимы и разрешение.
 */
public class SiCommand {
    protected String name;
    protected ArrayList<String> aliases = new ArrayList<>();
    protected SiPermission permission = null;
    protected Handler handler;

    /**
     * Создание новой команды.
     *
     * @param name       Имя
     * @param aliases    Псевдонимы
     * @param permission Разрешение
     * @param handler    Обработчик
     */
    public SiCommand(String name, Collection<String> aliases, @Nullable SiPermission permission, Handler handler) {
        this.name = name;
        this.aliases.addAll(aliases);
        this.permission = permission;
        this.handler = handler;

        if (!this.aliases.contains(name)) this.aliases.add(name);

        for (String alias : this.aliases)
            Bukkit.getCommandMap().register(Silicon.getPlugin().getName(), new Command(alias) {
                @Override
                public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
                    if (SiCommand.this.permission != null && !SiCommand.this.permission.has(sender)) return new ArrayList<>();
                    return handler.tabComplete(sender, alias, args, location);
                }

                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                    if (SiCommand.this.permission != null && !SiCommand.this.permission.checkForCommand(sender)) return false;
                    return handler.handle(sender, commandLabel, args);
                }
            });
//        SiFile configurationFile = Silicon.getBukkitRoot().getChild("commands.yml");
//        YamlConfiguration configuration =  configurationFile.readYaml();
//        for (String alias : aliases) configuration.getConfigurationSection("aliases").set(alias, name);
//        configurationFile.writeYaml(configuration);
//        Bukkit.reloadCommandAliases();
    }

    /**
     * Создание новой команды.
     *
     * @param name       Имя
     * @param aliases    Псевдонимы
     * @param permission Разрешение
     * @param handler    Обработчик
     */
    public SiCommand(String name, String[] aliases, SiPermission permission, Handler handler) {
        this(name, List.of(aliases), permission, handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name       Имя
     * @param permission Разрешение
     * @param handler    Обработчик
     */
    public SiCommand(String name, SiPermission permission, Handler handler) {
        this(name, new ArrayList<>(), permission, handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name       Имя
     * @param aliases    Псевдонимы
     * @param permission Разрешение
     * @param handler    Обработчик
     */
    public SiCommand(String name, Collection<String> aliases, String permission, Handler handler) {
        this(name, aliases, new SiPermission(permission), handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name       Имя
     * @param aliases    Псевдонимы
     * @param permission Разрешение
     * @param handler    Обработчик
     */
    public SiCommand(String name, String[] aliases, String permission, Handler handler) {
        this(name, List.of(aliases), new SiPermission(permission), handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name       Имя
     * @param permission Разрешение
     * @param handler    Обработчик
     */
    public SiCommand(String name, String permission, Handler handler) {
        this(name, new ArrayList<>(), new SiPermission(permission), handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name    Имя
     * @param aliases Псевдонимы
     * @param handler Обработчик
     */
    public SiCommand(String name, Collection<String> aliases, Handler handler) {
        this(name, aliases, (SiPermission) null, handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name    Имя
     * @param aliases Псевдонимы
     * @param handler Обработчик
     */
    public SiCommand(String name, String[] aliases, Handler handler) {
        this(name, List.of(aliases), (SiPermission) null, handler);
    }

    /**
     * Создание новой команды.
     *
     * @param name    Имя
     * @param handler Обработчик
     */
    public SiCommand(String name, Handler handler) {
        this(name, new ArrayList<>(), (SiPermission) null, handler);
    }

    /**
     * Обработчик команды.
     */
    public static class Handler {
        protected CommandHandler commandHandler;
        protected TabCompleteHandler tabCompleteHandler = null;
        protected HashMap<Integer, TabCompleteArgumentHandler> tabCompleteArgumentsHandlers = new HashMap<>();

        /**
         * Создание нового обработчика команды.
         *
         * @param commandHandler     Обработчик команды
         * @param tabCompleteHandler Обработчик подсказок
         */
        public Handler(CommandHandler commandHandler, TabCompleteHandler tabCompleteHandler) {
            this.commandHandler = commandHandler;
            this.tabCompleteHandler = tabCompleteHandler;
        }

        /**
         * Создание нового обработчика команды.
         *
         * @param commandHandler Обработчик команды
         */
        public Handler(CommandHandler commandHandler) {
            this.commandHandler = commandHandler;
        }

        /**
         * Добавление подсказки к конкретному аргументу.
         *
         * @param argumentIndex Индекс аргумента
         * @param handler       Обработчик подсказки
         */
        public void addTabCompleteArgumentHandler(int argumentIndex, TabCompleteArgumentHandler handler) {
            tabCompleteArgumentsHandlers.put(argumentIndex, handler);
        }

        /**
         * Добавление подсказки к аргументу, к которому нет подсказок.
         *
         * @param handler Обработчик подсказки
         */
        public void addOtherTabCompleteArgumentHandler(TabCompleteArgumentHandler handler) {
            tabCompleteArgumentsHandlers.put(-1, handler);
        }

        /**
         * Обработать команду.
         *
         * @param sender       Отправитель
         * @param commandLabel Название команды
         * @param args         Аргументы
         * @return Была ли выполнена команда.
         */
        public boolean handle(CommandSender sender, String commandLabel, String[] args) {
            return commandHandler.handle(sender, commandLabel, args);
        }

        /**
         * Выдать подсказки.
         *
         * @param sender   Отправитель
         * @param alias    Псевдоним команды
         * @param args     Аргументы
         * @param location Позиция отправителя
         * @return Подсказки.
         */
        public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) {
            if (tabCompleteHandler != null) return tabCompleteHandler.tabComplete(sender, alias, args, location);
            if (tabCompleteArgumentsHandlers.containsKey(args.length - 1))
                return tabCompleteArgumentsHandlers.get(args.length - 1).tabComplete(args.length - 1, args[args.length - 1], sender, alias, args, location);
            if (tabCompleteArgumentsHandlers.containsKey(-1))
                return tabCompleteArgumentsHandlers.get(-1).tabComplete(args.length - 1, args[args.length - 1], sender, alias, args, location);
            return new ArrayList<>();
        }

        /**
         * Обработчик команды.
         */
        @FunctionalInterface
        public interface CommandHandler {
            /**
             * Обработать команду.
             *
             * @param sender       Отправитель
             * @param commandLabel Название команды
             * @param args         Аргументы
             * @return Была ли выполнена команда.
             */
            boolean handle(CommandSender sender, String commandLabel, String[] args);
        }

        /**
         * Обработчик подсказок.
         */
        @FunctionalInterface
        public interface TabCompleteHandler {
            /**
             * Получить подсказки.
             *
             * @param sender   Отправитель
             * @param alias    Псевдоним команды
             * @param args     Аргументы
             * @param location Позиция отправителя
             * @return Подсказки.
             */
            List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location);
        }

        /**
         * Обработчик подсказок конкретного аргумента.
         */
        @FunctionalInterface
        public interface TabCompleteArgumentHandler {
            /**
             * Получить подсказки к конкретному аргументу.
             *
             * @param argumentIndex Индекс аргумента
             * @param argument      Аргумент
             * @param sender        Отправитель
             * @param alias         Псевдоним команды
             * @param args          Аргументы
             * @param location      Позиция отправителя
             * @return Подсказки к аргументу.
             */
            List<String> tabComplete(int argumentIndex, String argument, CommandSender sender, String alias, String[] args, Location location);
        }
    }
}
