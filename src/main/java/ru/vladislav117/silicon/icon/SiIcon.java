package ru.vladislav117.silicon.icon;

import org.bukkit.Material;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.cusomModelData.SiCustomModelDataRecord;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.menu.SiMenuElement;

/**
 * Иконка.
 */
public class SiIcon extends SiContent {
    static String customModelDataCategory = "icon";
    protected SiCustomModelDataRecord customModelDataRecord;

    /**
     * Создание новой иконки.
     *
     * @param name Имя иконки
     */
    public SiIcon(String name) {
        super(name);
        this.customModelDataRecord = new SiCustomModelDataRecord(Material.PAPER, customModelDataCategory, name);
        SiIcons.all.add(this);
    }

    /**
     * Создать стак и иконкой.
     *
     * @param amount Количество предметов в стаке
     * @return Созданный стак.
     */
    public SiItemStack buildItemStack(int amount) {
        return new SiItemStack(Material.PAPER, amount).setCustomModelData(customModelDataRecord.getCustomModelData());
    }

    /**
     * Создать стак и иконкой.
     *
     * @return Созданный стак.
     */
    public SiItemStack buildItemStack() {
        return buildItemStack(1);
    }

    /**
     * Создать элемент меню с иконкой.
     *
     * @param name Имя элемента
     * @return Элемент меню.
     */
    public SiMenuElement buildMenuElement(String name) {
        return new SiMenuElement(name).setItemStack(buildItemStack());
    }

    /**
     * Создать элемент меню с иконкой.
     *
     * @return Элемент меню.
     */
    public SiMenuElement buildMenuElement() {
        return new SiMenuElement().setItemStack(buildItemStack());
    }
}
