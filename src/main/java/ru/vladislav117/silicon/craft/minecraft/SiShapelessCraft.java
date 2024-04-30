package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import ru.vladislav117.silicon.craft.SiCraft;
import ru.vladislav117.silicon.craft.SiCraftIngredient;
import ru.vladislav117.silicon.namespace.SiNamespace;

import java.util.ArrayList;

/**
 * Бесформенный крафт в верстаке.
 */
public class SiShapelessCraft extends SiRecipeCraft {
    protected ArrayList<SiCraftIngredient> ingredients = new ArrayList<>();

    /**
     * Создание нового бесформенного крафта.
     *
     * @param result Результат крафта
     */
    public SiShapelessCraft(ItemStack result) {
        super(result);
    }

    @Override
    public SiCraft addIngredient(SiCraftIngredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new ShapelessRecipe(SiNamespace.getKey(name), result) {{
            for (SiCraftIngredient ingredient : ingredients) addIngredient(ingredient.getRecipeChoice());
        }};
    }
}
