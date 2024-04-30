package ru.vladislav117.silicon.region;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;

/**
 * Флаг региона.
 */
public class SiRegionFlag extends SiContent {
    protected SiTextLike displayName = null;
    protected ArrayList<SiTextLike> description = null;
    protected ItemStack icon = new ItemStack(Material.PAPER);
    protected boolean defaultValue;

    /**
     * Создание нового флага региона.
     *
     * @param name Имя контента
     */
    public SiRegionFlag(String name, boolean defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
        SiRegionFlags.all.add(this);
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
     * Установка отображаемого имени.
     *
     * @param displayName Отображаемое имя
     * @return Этот же флаг.
     */
    protected SiRegionFlag setDisplayName(SiTextLike displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Установка отображаемого имени.
     *
     * @param displayName Отображаемое имя
     * @return Этот же флаг.
     */
    protected SiRegionFlag setDisplayName(String displayName) {
        this.displayName = SiText.string(displayName);
        return this;
    }

    /**
     * Получение описания.
     *
     * @return Описание.
     */
    public ArrayList<SiTextLike> getDescription() {
        return description;
    }

    /**
     * Установка описания.
     *
     * @param description Описание
     * @return Этот же флаг.
     */
    public SiRegionFlag setDescription(ArrayList<SiTextLike> description) {
        this.description = description;
        return this;
    }

    /**
     * Установка описания.
     *
     * @param description Описание
     * @return Этот же флаг.
     */
    protected SiRegionFlag setDescription(String description) {
        this.description = new SiLinedText(description).getCompleteTextParts();
        return this;
    }

    /**
     * Получение иконки.
     *
     * @return Иконка.
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Установка иконки.
     *
     * @param icon Иконка
     * @return Этот же флаг.
     */
    protected SiRegionFlag setIcon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Установка иконки.
     *
     * @param icon Иконка
     * @return Этот же флаг.
     */
    protected SiRegionFlag setIcon(Material icon) {
        this.icon = new ItemStack(icon);
        return this;
    }

    /**
     * Получение значения по умолчанию.
     *
     * @return Значение по умолчанию.
     */
    public boolean getDefaultValue() {
        return defaultValue;
    }

    /**
     * Установка значения по умолчанию.
     *
     * @param defaultValue Значение по умолчанию
     * @return Этот же флаг.
     */
    protected SiRegionFlag setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
}
