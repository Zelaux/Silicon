package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.StonecuttingRecipe;
import ru.vladislav117.silicon.craft.SiCraft;
import ru.vladislav117.silicon.craft.SiCraftIngredient;
import ru.vladislav117.silicon.namespace.SiNamespace;

/**
 * Крафт на камнерезе.
 */
public class SiStonecutterCraft extends SiRecipeCraft {
    protected SiCraftIngredient ingredient;

    /**
     * Создание нового крафта на камнерезе.
     *
     * @param result Результат крафта
     */
    public SiStonecutterCraft(ItemStack result) {
        super(result);
    }

    /**
     * Получение ингредиента.
     *
     * @return Ингредиент.
     */
    public SiCraftIngredient getIngredient() {
        return ingredient;
    }

    @Override
    public SiCraft addIngredient(SiCraftIngredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new StonecuttingRecipe(SiNamespace.getKey(name), result, ingredient.getRecipeChoice());
    }
}
