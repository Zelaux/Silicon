package ru.vladislav117.silicon.store;

import org.bukkit.entity.Player;
import ru.vladislav117.silicon.command.SiCommand;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.economy.SiCurrency;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.menu.SiMenus;
import ru.vladislav117.silicon.node.SiNode;
import ru.vladislav117.silicon.permission.SiPermission;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.*;

/**
 * Лавка предметов.
 */
public class SiStore extends SiContent {
    protected SiFile file;
    protected SiTextLike displayName;
    protected SiCurrency currency;
    protected HashMap<String, SiStorePosition> positions = new HashMap<>();
    protected ArrayList<SiStorePosition> orderedPositions = new ArrayList<>();
    protected SiNode data = SiNode.emptyMap();

    /**
     * Создание новой лавки.
     *
     * @param name           Навзание лавки
     * @param command        Команда для открытия
     * @param commandAliases Псевдонимы команды
     * @param displayName    Отображаемое имя
     * @param currency       Валюта
     */
    public SiStore(String name, String command, Collection<String> commandAliases, SiTextLike displayName, SiCurrency currency) {
        super(name);
        file = SiStores.directory.getChild(name + ".json");
        this.displayName = displayName;
        this.currency = currency;
        SiStores.all.add(this);
        if (file.exists()) data = file.readNode();
        new SiCommand(command, commandAliases, new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player player)) return false;
            open(player);
            return true;
        }));
    }

    /**
     * Получение файла.
     *
     * @return Файл.
     */
    public SiFile getFile() {
        return file;
    }

    /**
     * Получение отображаемого имени.
     *
     * @return Отображаемое имя.
     */
    public SiTextLike getDisplayName() {
        return displayName;
    }

    /**
     * Получение валюты.
     *
     * @return Валюта.
     */
    public SiCurrency getCurrency() {
        return currency;
    }

    /**
     * Получение позиций лавки.
     *
     * @return Позиции лавки.
     */
    public HashMap<String, SiStorePosition> getPositions() {
        return positions;
    }

    /**
     * Получение позиций лавки.
     *
     * @return Позиции лавки.
     */
    public ArrayList<SiStorePosition> getOrderedPositions() {
        return orderedPositions;
    }

    /**
     * Получение данных лавки.
     *
     * @return Данные лавки.
     */
    public SiNode getData() {
        return data;
    }

    /**
     * Сохранение лавки.
     *
     * @return Эта же лавка.
     */
    public SiStore save() {
        file.writeNode(data);
        return this;
    }

    /**
     * Создание меню лавки.
     *
     * @param player Игрок
     * @return Меню.
     */
    public SiMenu buildMenu(Player player) {
        ArrayList<SiMenuElement> elements = new ArrayList<>();
        for (SiStorePosition position : orderedPositions) {
            elements.add(position.toElement(player));
        }
        return SiMenus.buildMenuCluster(player.getName() + "_" + name, displayName, elements);
    }

    /**
     * Открытие меню лавки.
     *
     * @param player Игрок
     * @return Открытое меню.
     */
    public SiMenu open(Player player) {
        return buildMenu(player).open(player);
    }
}
