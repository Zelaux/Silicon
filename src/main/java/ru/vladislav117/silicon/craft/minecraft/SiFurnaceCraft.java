package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import ru.vladislav117.silicon.craft.SiCraft;
import ru.vladislav117.silicon.craft.SiCraftIngredient;
import ru.vladislav117.silicon.namespace.SiNamespace;

/**
 * Крафт в печи.
 */
public class SiFurnaceCraft extends SiRecipeCraft {
    protected SiCraftIngredient ingredient;
    protected int time;
    protected double experience;

    /**
     * Создание нового крафта в печи.
     *
     * @param result     Результат крафта
     * @param time       Время крафта в тиках
     * @param experience Получаемый опыт
     */
    public SiFurnaceCraft(ItemStack result, int time, double experience) {
        super(result);
        this.time = time;
        this.experience = experience;
    }

    /**
     * Получение ингредиента.
     *
     * @return Ингредиент.
     */
    public SiCraftIngredient getIngredient() {
        return ingredient;
    }

    /**
     * Получение времени крафта.
     *
     * @return Время крафта в тиках.
     */
    public int getTime() {
        return time;
    }

    /**
     * Получение опыта.
     *
     * @return Получаемый опыт.
     */
    public double getExperience() {
        return experience;
    }

    @Override
    public SiCraft addIngredient(SiCraftIngredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new FurnaceRecipe(SiNamespace.getKey(name), result, ingredient.getRecipeChoice(), (float) experience, time);
    }
}
