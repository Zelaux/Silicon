package ru.vladislav117.silicon.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.HashMap;

/**
 * Меню.
 */
public class SiMenu {
    public static final int row1size = 9;
    public static final int row2size = 9 * 2;
    public static final int row3size = 9 * 3;
    public static final int row4size = 9 * 4;
    public static final int row5size = 9 * 5;
    public static final int row6size = 9 * 6;

    protected String name;
    protected int size;
    protected SiTextLike displayName = SiText.string("");
    protected HashMap<Integer, SiMenuElement> elements = new HashMap<>();
    protected SiMenuElement plugElement = new SiMenuElement().setItemStack(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(SiText.string(""));

    /**
     * Создание нового меню
     *
     * @param name        Имя меню
     * @param size        Размер меню (число, кратное 9)
     * @param displayName Отображаемое имя меню
     */
    public SiMenu(String name, int size, SiTextLike displayName) {
        this.name = name;
        this.size = size;
        this.displayName = displayName;
        SiMenus.add(this);
    }

    /**
     * Создание нового меню
     *
     * @param name Имя меню
     * @param size Размер меню (число, кратное 9)
     */
    public SiMenu(String name, int size) {
        this.name = name;
        this.size = size;
        SiMenus.add(this);
    }

    /**
     * Получение имени меню.
     *
     * @return Имя меню.
     */
    public String getName() {
        return name;
    }

    /**
     * Получение отображаемого имени меню.
     *
     * @return Отображаемое имя меню.
     */
    public SiTextLike getDisplayName() {
        return displayName;
    }

    /**
     * Установка элемента меню.
     *
     * @param index   Индекс
     * @param element Элемент
     * @return Это же меню.
     */
    public SiMenu setElement(int index, SiMenuElement element) {
        elements.put(index, element);
        return this;
    }

    /**
     * Создать инвентарь меню.
     *
     * @return Инвентарь меню.
     */
    public Inventory buildInventory() {
        Inventory inventory = Bukkit.createInventory(null, size, displayName.toComponent());
        for (int i = 0; i < size; i++) inventory.setItem(i, plugElement.buildItemStack().toItemStack());
        for (Integer index : elements.keySet())
            inventory.setItem(index, elements.get(index).buildItemStack().toItemStack());
        return inventory;
    }

    /**
     * Открыть меню игроку.
     *
     * @param player Игрок
     * @return Это же меню.
     */
    public SiMenu open(Player player) {
        player.openInventory(buildInventory());
        return this;
    }
}
