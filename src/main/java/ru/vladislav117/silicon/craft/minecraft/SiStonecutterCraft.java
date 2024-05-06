package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.StonecuttingRecipe;
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
import ru.vladislav117.silicon.text.structure.SiLinedText;

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
        return new StonecuttingRecipe(SiNamespace.getKey(name), getResult(), ingredient.getRecipeChoice());
    }

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.STONECUTTER){{
            setDescription(new SiLinedText("Предмет получается путём резки на камнерезе").getCompleteTextParts());
        }};
    }

    @Override
    public SiMenu buildMenu(String name) {
        ItemStack result = getResult();
        return new SiMenu(name, SiMenu.row6size, new SiTextComponent(result.displayName())) {{
            setElement(20, SiCraftMenus.buildIngredientElement(ingredient));
            setElement(21, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(22, new SiMenuElement().setItemStack(buildIcon()));
            setElement(23, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(24, SiCraftMenus.buildIngredientElement(new SiItemStack(result)));

            buildStandardButtons(this, results);
        }};
    }
}
