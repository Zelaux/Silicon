package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import ru.vladislav117.silicon.craft.SiCraft;
import ru.vladislav117.silicon.craft.SiCraftIngredient;
import ru.vladislav117.silicon.craft.SiCraftMenus;
import ru.vladislav117.silicon.craft.SiCrafts;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.namespace.SiNamespace;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextComponent;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.structure.SiLinedText;

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

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.FURNACE){{
            setDescription(new SiLinedText("Предмет получается путём переплавки в печи").getCompleteTextParts());
        }};
    }

    @Override
    public SiMenu buildMenu(String name) {
        return new SiMenu(name, SiMenu.row6size, new SiTextComponent(result.displayName())) {{
            setElement(20, SiCraftMenus.buildIngredientElement(ingredient));
            setElement(21, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(22, new SiMenuElement().setItemStack(buildIcon()));
            setElement(23, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(24, SiCraftMenus.buildIngredientElement(new SiItemStack(result)));

            buildStandardButtons(this, new SiItemStack(result));
        }};
    }
}
