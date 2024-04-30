package ru.vladislav117.silicon.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.item.category.SiItemCategories;
import ru.vladislav117.silicon.item.category.SiItemCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SiItemType extends SiContent {
    protected Material material;
    protected boolean usesUuid = false;
    protected SiItemTexture texture = null;
    protected SiItemDisplay display = null;
    protected HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    protected ArrayList<SiAttributeModifier> attributeModifiers = new ArrayList<>();
    protected ArrayList<ItemFlag> flags = new ArrayList<>();
    protected HashMap<String, Object> defaultTags = new HashMap<>();
    protected ArrayList<String> defaultTagsHints = null;
    protected SiItemInteractions interactions = null;
    protected ArrayList<SiItemCategory> categories = new ArrayList<>();

    /**
     * Создание нового типа предмета.
     *
     * @param name     Имя типа предмета
     * @param material Материал
     */
    public SiItemType(String name, Material material) {
        super(name);
        this.material = material;
        SiItemTypes.all.add(this);
    }

    /**
     * Получение материала типа предмета.
     *
     * @return Материал типа предмета.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Использует ли этот предмет UUID.
     *
     * @return Использует ли этот предмет UUID.
     */
    public boolean isUsesUuid() {
        return usesUuid;
    }

    /**
     * Получение взаимодействий типа предмета.
     *
     * @return Взаимодействия типа предмета.
     */
    @Nullable
    public SiItemInteractions getInteractions() {
        return interactions;
    }

    /**
     * Получение текстуры предмета.
     *
     * @return Текстура предмета или null.
     */
    public SiItemTexture getTexture() {
        return texture;
    }

    /**
     * Получение отображения предмета.
     *
     * @return Отображение предмета или null.
     */
    public SiItemDisplay getDisplay() {
        return display;
    }

    /**
     * Добавление зачарования.
     *
     * @param enchantment Зачарование
     * @param level       Уровень
     * @return Этот же тип предмета.
     */
    public SiItemType addEnchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    /**
     * Добавление модификатора атрибутов.
     *
     * @param attributeModifier Модификатор атрибутов.
     * @return Этот же тип предмета.
     */
    public SiItemType addAttributeModifier(SiAttributeModifier attributeModifier) {
        attributeModifiers.add(attributeModifier);
        return this;
    }

    /**
     * Добавление флага.
     *
     * @param flag Флаг
     * @return Этот же тип предмета.
     */
    public SiItemType addFlag(ItemFlag flag) {
        flags.add(flag);
        return this;
    }

    /**
     * Получение тегов по умолчанию.
     *
     * @return Теги по умолчанию.
     */
    public HashMap<String, Object> getDefaultTags() {
        return defaultTags;
    }

    /**
     * Получение списка подсказок по тегам по умолчанию.
     *
     * @return Список подсказок по тегам по умолчанию.
     */
    public ArrayList<String> getDefaultTagsHints() {
        if (defaultTagsHints == null) {
            defaultTagsHints = new ArrayList<>();
            for (String tag : defaultTags.keySet()) {
                defaultTagsHints.add(tag + "=" + defaultTags.get(tag));
            }
        }
        return defaultTagsHints;
    }

    /**
     * Проверка, является ли тип предмета unknown.
     *
     * @return Является ли тип предмета unknown.
     */
    public boolean isUnknown() {
        return this == SiItemTypes.unknown;
    }

    /**
     * Получить все категории предмета.
     *
     * @return Категории предмета.
     */
    public ArrayList<SiItemCategory> getCategories() {
        return categories;
    }

    /**
     * Получить основную категорию предмета.
     *
     * @return Основная категория предмета.
     */
    public SiItemCategory getMainCategory() {
        return categories.isEmpty() ? SiItemCategories.everything : categories.get(0);
    }

    /**
     * Добавление категории предмета.
     *
     * @param category Категория
     * @return Этот же тип предмета.
     */
    public SiItemType addCategory(SiItemCategory category) {
        categories.add(category);
        return this;
    }

    /**
     * Создать стак с предметами этого типа.
     *
     * @param amount Количество предметов
     * @param tags   Теги стака
     * @return Новый стак предметов.
     */
    public SiItemStack buildItemStack(int amount, HashMap<String, Object> tags) {
        SiItemStack itemStack = new SiItemStack(material, amount);
        itemStack.setEnchantments(enchantments);
        itemStack.addAttributeModifiers(attributeModifiers);
        itemStack.addFlags(flags);
        itemStack.getTagsManager().setTags(defaultTags);
        itemStack.getTagsManager().setTags(tags);
        if (usesUuid) itemStack.getTagsManager().setString("uuid", UUID.randomUUID().toString());
        itemStack.getTagsManager().setString("item_type", name);
        itemStack.updateDisplay();
        return itemStack;
    }

    /**
     * Создать стак с предметом этого типа.
     *
     * @param amount Количество предметов
     * @return Новый стак предметов.
     */
    public SiItemStack buildItemStack(int amount) {
        return buildItemStack(amount, new HashMap<>());
    }

    /**
     * Создать стак с одним предметом этого типа.
     *
     * @param tags Теги стака
     * @return Новый стак предметов.
     */
    public SiItemStack buildItemStack(HashMap<String, Object> tags) {
        return buildItemStack(1, tags);
    }

    /**
     * Создать стак с одним предметом этого типа.
     *
     * @return Новый стак предметов.
     */
    public SiItemStack buildItemStack() {
        return buildItemStack(1, new HashMap<>());
    }
}
