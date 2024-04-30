package ru.vladislav117.silicon.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.command.SiCommand;
import ru.vladislav117.silicon.command.tabCompleteArgumentHandlers.SiIntegerTabCompleteArgumentHandler;
import ru.vladislav117.silicon.command.tabCompleteArgumentHandlers.SiOnlinePlayersTabCompleteArgumentHandler;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.permission.SiPermission;
import ru.vladislav117.silicon.text.SiText;

import java.util.ArrayList;
import java.util.List;

/**
 * Валюта.
 */
public class SiCurrency extends SiContent {
    protected String symbol = "";
    protected SiCurrencyActions actions;

    /**
     * Создание новой валюты.
     *
     * @param name Имя валюты
     */
    public SiCurrency(String name, SiCurrencyActions actions) {
        super(name);
        this.actions = actions;
        SiCurrencies.all.add(this);
    }

    /**
     * Получение символа валюты.
     *
     * @return Символ валюты.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Установка символа валюты.
     *
     * @param symbol Символ валюты
     * @return Эта же валюта.
     */
    public SiCurrency setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    /**
     * Получение действий валюты.
     *
     * @return Действия валюты.
     */
    public SiCurrencyActions getActions() {
        return actions;
    }

    /**
     * Получение баланса аккаунта.
     *
     * @param account Аккаунт
     * @return Баланс аккаунта.
     */
    public int getBalance(String account) {
        return actions.getBalance(this, account);
    }

    /**
     * Установка баланса аккаунта.
     *
     * @param account Аккаунт
     * @param value   Значение
     * @return Эта же валюта.
     */
    public SiCurrency setBalance(String account, int value) {
        actions.setBalance(this, account, value);
        return this;
    }

    /**
     * Добавление к балансу аккаунта.
     *
     * @param account Аккаунт
     * @param value   Значение
     * @return Эта же валюта.
     */
    public SiCurrency addBalance(String account, int value) {
        actions.addBalance(this, account, value);
        return this;
    }

    /**
     * Совершение транзакции между аккаунтами.
     *
     * @param sender   Аккаунт отправителя
     * @param receiver Аккаунт получателя
     * @param value    Значение
     * @return Была ли проведена транзакция (false, если у отправителя не хватает средств).
     */
    public boolean makeTransaction(String sender, String receiver, int value) {
        return actions.makeTransaction(this, sender, receiver, value);
    }

    /**
     * Создание команды для получения баланса.
     *
     * @param commandName    Имя команды
     * @param commandAliases Псевдонимы команды
     * @param permission     Разрешение команды или null
     * @return Эта же валюта.
     */
    public SiCurrency createBalanceCommand(String commandName, List<String> commandAliases, @Nullable SiPermission permission) {
        new SiCommand(commandName, commandAliases, permission, new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player player)) {
                SiText.string("Команду может отправлять только игрок").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            SiText.container().addString("Ваш баланс: ", SiPalette.Interface.green).addString(getBalance(player.getName()) + symbol, SiPalette.Interface.yellow).toMessageTask().addPlayer(player).send();
            return true;
        }));
        return this;
    }

    /**
     * Создание команды для перевода.
     *
     * @param commandName    Имя команды
     * @param commandAliases Псевдонимы команды
     * @param permission     Разрешение команды или null
     * @return Эта же валюта.
     */
    public SiCurrency createPayCommand(String commandName, List<String> commandAliases, @Nullable SiPermission permission) {
        new SiCommand(commandName, commandAliases, permission, new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player player)) {
                SiText.string("Команду может отправлять только игрок").setStyle(SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 0) {
                SiText.string("Не указан получатель", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 1) {
                SiText.string("Не указано количество", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            int value;
            try {
                value = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                SiText.string("Количество должно быть числом", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (value <= 0) {
                SiText.string("Значение должно быть больше нуля", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            Player receiver = Bukkit.getPlayer(args[0]);
            if (receiver == null) {
                SiText.string("Игрок " + args[0] + " не на сервере", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            boolean successful = actions.makeTransaction(this, player.getName(), args[0], value);
            if (!successful) {
                SiText.string("У вас недостаточно средств", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            SiText.container().addString("Вы отправили ", SiPalette.Interface.green).addString(value + symbol, SiPalette.Interface.yellow).addString(" игроку ", SiPalette.Interface.green).addString(args[0], SiPalette.Interface.yellow).toMessageTask().addOtherReceiver(sender).send();
            SiText.container().addString("Вы получили ", SiPalette.Interface.green).addString(value + symbol, SiPalette.Interface.yellow).addString(" от игрока ", SiPalette.Interface.green).addString(player.getName(), SiPalette.Interface.yellow).toMessageTask().addPlayer(receiver).send();
            return true;
        }) {{
            addTabCompleteArgumentHandler(0, SiOnlinePlayersTabCompleteArgumentHandler.onlinePlayers);
            addTabCompleteArgumentHandler(1, SiIntegerTabCompleteArgumentHandler.onlyPositive);
        }});
        return this;
    }
}
