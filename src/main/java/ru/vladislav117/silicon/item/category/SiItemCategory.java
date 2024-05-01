package ru.vladislav117.silicon.item.category;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Категория предметов.
 */
public class SiItemCategory extends SiContent {
    protected SiTextLike displayName;
    protected SiColor color;
    protected ItemStack itemStack = new ItemStack(Material.PAPER);
    protected boolean displays = true;
    protected ArrayList<SiItemType> itemTypes = new ArrayList<>();

    /**
     * Создание новой категории.
     *
     * @param name Имя категории
     */
    public SiItemCategory(String name, SiColor color, SiTextLike displayName) {
        super(name);
        this.displayName = displayName;
        if (displayName.getStyle() == null) displayName.setStyle(color);
        SiItemCategories.all.add(this);
    }

    /**
     * Получение отображаемого имени категории.
     *
     * @return Отображаемое имя категории.
     */
    public SiTextLike getDisplayName() {
        return displayName;
    }

    /**
     * Получение отображаемого имени категории для описания предмета.
     *
     * @return Отображаемое имя категории для описания предмета.
     */
    public SiTextLike getDisplayNameForItemDescription() {
        return SiText.container().addString("<", SiPalette.Interface.gray).addText(displayName).addString(">", SiPalette.Interface.gray);
    }

    /**
     * Установка предмета категории.
     *
     * @param itemStack Предмет
     * @return Эта же категория.
     */
    public SiItemCategory setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    /**
     * Установка предмета категории.
     *
     * @param itemStack Предмет
     * @return Эта же категория.
     */
    public SiItemCategory setItemStack(SiItemStack itemStack) {
        this.itemStack = itemStack.toItemStack();
        return this;
    }

    /**
     * Установка предмета категории.
     *
     * @param material Предмет
     * @return Эта же категория.
     */
    public SiItemCategory setItemStack(Material material) {
        this.itemStack = new ItemStack(material);
        return this;
    }

    /**
     * Установка предмета категории.
     *
     * @param itemType Предмет
     * @return Эта же категория.
     */
    public SiItemCategory setItemStack(SiItemType itemType) {
        this.itemStack = itemType.buildItemStack().toItemStack();
        return this;
    }

    /**
     * Получение, отображается ли категория в описании предмета.
     *
     * @return Отображается ли категория в описании предмета.
     */
    public boolean isDisplays() {
        return displays;
    }

    /**
     * Установка, отображается ли категория в описании предмета.
     *
     * @param displays Отображается ли категория в описании предмета
     * @return Эта же категория.
     */
    public SiItemCategory setDisplays(boolean displays) {
        this.displays = displays;
        return this;
    }

    /**
     * Преобразование категории в стак.
     *
     * @return Стак.
     */
    public SiItemStack toItemStack() {
        SiItemStack stack = new SiItemStack(itemStack);
        stack.setDisplayName(displayName);
        return stack;
    }

    /**
     * Получение предметов категории.
     *
     * @return Предметы категории.
     */
    public ArrayList<SiItemType> getItemTypes() {
        return itemTypes;
    }

    /**
     * Добавление предмета в категорию.
     *
     * @param itemType Предмет
     * @return Эта же категория.
     */
    public SiItemCategory addItemType(SiItemType itemType) {
        if (!itemTypes.contains(itemType)) itemTypes.add(itemType);
        return this;
    }
}
