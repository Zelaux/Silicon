package ru.vladislav117.silicon.craft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.List;

/**
 * Ингредиент крафта.
 */
public class SiCraftIngredient {
    protected ItemStack displayItemStack;
    protected RecipeChoice recipeChoice;

    /**
     * Создание нового ингредиента крафта.
     *
     * @param itemStack Ингредиент
     */
    public SiCraftIngredient(ItemStack itemStack) {
        displayItemStack = itemStack.clone();
        recipeChoice = new RecipeChoice.ExactChoice(itemStack);
    }

    /**
     * Создание нового ингредиента крафта.
     *
     * @param itemStacks Ингредиенты
     */
    public SiCraftIngredient(ItemStack[] itemStacks) {
        displayItemStack = itemStacks[0].clone();
        recipeChoice = new RecipeChoice.ExactChoice(itemStacks);
    }

    /**
     * Создание нового ингредиента крафта.
     *
     * @param itemStacks Ингредиенты
     */
    public SiCraftIngredient(List<ItemStack> itemStacks) {
        displayItemStack = itemStacks.get(0).clone();
        recipeChoice = new RecipeChoice.ExactChoice(itemStacks);
    }

    /**
     * Создание нового ингредиента крафта.
     *
     * @param displayItemStack Отображаемый ингредиент
     * @param itemStack        Ингредиент
     */
    public SiCraftIngredient(ItemStack displayItemStack, ItemStack itemStack) {
        this.displayItemStack = displayItemStack.clone();
        recipeChoice = new RecipeChoice.ExactChoice(itemStack);
    }

    /**
     * Создание нового ингредиента крафта.
     *
     * @param displayItemStack Отображаемый ингредиент
     * @param itemStacks       Ингредиенты
     */
    public SiCraftIngredient(ItemStack displayItemStack, ItemStack[] itemStacks) {
        this.displayItemStack = displayItemStack.clone();
        recipeChoice = new RecipeChoice.ExactChoice(itemStacks);
    }

    /**
     * Создание нового ингредиента крафта.
     *
     * @param displayItemStack Отображаемый ингредиент
     * @param itemStacks       Ингредиенты
     */
    public SiCraftIngredient(ItemStack displayItemStack, List<ItemStack> itemStacks) {
        this.displayItemStack = displayItemStack.clone();
        recipeChoice = new RecipeChoice.ExactChoice(itemStacks);
    }

    /**
     * Создание нового ингредиента крафта.
     *
     * @param displayItemStack Отображаемый ингредиент
     * @param recipeChoice     RecipeChoice
     */
    public SiCraftIngredient(ItemStack displayItemStack, RecipeChoice recipeChoice) {
        this.displayItemStack = displayItemStack;
        this.recipeChoice = recipeChoice;
    }

    /**
     * Создание ингредиента крафта из RecipeChoice.
     *
     * @param recipeChoice RecipeChoice
     * @return Ингредиент крафта.
     */
    public static SiCraftIngredient buildFromRecipeChoice(RecipeChoice recipeChoice) {
        if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice) {
            return new SiCraftIngredient(new ItemStack(materialChoice.getChoices().get(0)), materialChoice);
        } else if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice) {
            return new SiCraftIngredient(exactChoice.getChoices().get(0), exactChoice);
        }
        return null;
    }

    /**
     * Получение отображаемого ингредиента.
     *
     * @return Отображаемый ингредиент.
     */
    public ItemStack getDisplayItemStack() {
        return displayItemStack;
    }

    /**
     * Получение RecipeChoice.
     *
     * @return RecipeChoice.
     */
    public RecipeChoice getRecipeChoice() {
        return recipeChoice;
    }
}
