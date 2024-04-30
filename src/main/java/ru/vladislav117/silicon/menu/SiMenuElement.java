package ru.vladislav117.silicon.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.icon.SiIcon;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.Collection;

/**
 * Элемент меню.
 */
public class SiMenuElement {
    protected String name = null;
    protected SiItemStack itemStack = new SiItemStack(Material.PAPER);
    protected ClickHandler clickHandler = null;
    protected String menuTransfer = null;

    /**
     * Создание нового элемента меню.
     *
     * @param name Имя элемента меню
     */
    public SiMenuElement(String name) {
        this.name = name;
        SiMenuElements.add(this);
    }

    /**
     * Создание нового элемента меню.
     */
    public SiMenuElement() {
    }

    /**
     * Установка предмета элемента.
     *
     * @param itemStack Предмет
     * @return Этот же элемент.
     */
    public SiMenuElement setItemStack(SiItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    /**
     * Установка предмета элемента.
     *
     * @param itemStack Предмет
     * @return Этот же элемент.
     */
    public SiMenuElement setItemStack(ItemStack itemStack) {
        this.itemStack = new SiItemStack(itemStack);
        return this;
    }

    /**
     * Установка предмета элемента.
     *
     * @param material Предмет
     * @return Этот же элемент.
     */
    public SiMenuElement setItemStack(Material material) {
        this.itemStack = new SiItemStack(material);
        return this;
    }

    /**
     * Установка предмета элемента.
     *
     * @param itemType Предмет
     * @return Этот же элемент.
     */
    public SiMenuElement setItemStack(SiItemType itemType) {
        this.itemStack = itemType.buildItemStack();
        return this;
    }

    /**
     * Установка предмета элемента.
     *
     * @param icon Иконка
     * @return Этот же элемент.
     */
    public SiMenuElement setItemStack(SiIcon icon) {
        this.itemStack = icon.buildItemStack();
        return this;
    }

    /**
     * Установка отображаемого имени предмета.
     *
     * @param displayName Отображаемое имя предмета.
     * @return Этот же элемент.
     */
    public SiMenuElement setDisplayName(SiTextLike displayName) {
        itemStack.setDisplayName(displayName);
        return this;
    }

    /**
     * Установка описания предмета.
     *
     * @param description Описание
     * @return Этот же элемент.
     */
    public SiMenuElement setDescription(Collection<SiTextLike> description) {
        itemStack.setDescription(description);
        return this;
    }

    /**
     * Установка описания предмета.
     *
     * @param description Описание
     * @return Этот же элемент.
     */
    public SiMenuElement setDescription(SiTextLike description) {
        itemStack.setDescription(description);
        return this;
    }

    /**
     * Получение имени элемента.
     *
     * @return Имя элемента или null, если не задано.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * Создать стак.
     *
     * @return Стак.
     */
    public SiItemStack buildItemStack() {
        SiItemStack stack = new SiItemStack(itemStack.toItemStack());
        stack.getTagsManager().setBoolean("menu_element", true);
        if (name != null) stack.getTagsManager().setString("menu_element_name", name);
        if (menuTransfer != null) stack.getTagsManager().setString("menu_transfer", menuTransfer);
        return stack;
    }

    /**
     * Получить обработчик нажатия.
     *
     * @return Обработчик нажатия или null, если не задан.
     */
    @Nullable
    public ClickHandler getClickHandler() {
        return clickHandler;
    }

    /**
     * Установка обработчика нажатия.
     *
     * @param clickHandler Обработчик нажатия
     * @return Этот же элемент.
     */
    public SiMenuElement setClickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    /**
     * Получить имя меню, в которое будет совершён переход при нажатии.
     *
     * @return Имя меню, в которое будет совершён переход при нажатии.
     */
    @Nullable
    public String getMenuTransfer() {
        return menuTransfer;
    }

    /**
     * Установить имя меню, в которое будет совершён переход при нажатии.
     *
     * @param menuTransfer Имя меню, в которое будет совершён переход при нажатии
     * @return Этот же элемент.
     */
    public SiMenuElement setMenuTransfer(String menuTransfer) {
        this.menuTransfer = menuTransfer;
        return this;
    }

    /**
     * Обработчик нажатия элемента.
     */
    @FunctionalInterface
    public interface ClickHandler {
        /**
         * Обработать нажатие.
         *
         * @param player    Игрок
         * @param itemStack Стак
         * @param event     Событие
         */
        void click(Player player, SiItemStack itemStack, InventoryClickEvent event);
    }
}
