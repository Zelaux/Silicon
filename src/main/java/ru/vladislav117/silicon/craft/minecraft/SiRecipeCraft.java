package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import ru.vladislav117.silicon.craft.SiCraft;

/**
 * Абстрактный крафт с рецептом.
 */
public abstract class SiRecipeCraft extends SiCraft {
    /**
     * Создание абстрактного крафта с рецептом.
     *
     * @param result Результат крафта
     */
    public SiRecipeCraft(ItemStack result) {
        super(result);
    }

    /**
     * Создание рецепта по крафту.
     *
     * @param name Имя рецепта
     * @return Рецепт.
     */
    public abstract Recipe buildRecipe(String name);
}
