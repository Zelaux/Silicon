package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
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

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.CRAFTING_TABLE) {{
            setDescription(new SiLinedText("Предмет получается путём крафта на верстаке").getCompleteTextParts());
        }};
    }

    @Override
    public SiMenu buildMenu(String name) {
        return new SiMenu(name, SiMenu.row6size, new SiTextComponent(result.displayName())) {{
            for (int row : new int[]{10, 19, 28}) {
                for (int i = 0; i < 3; i++) {
                    setElement(row + i, new SiMenuElement() {{
                        setItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                        setDisplayName(SiText.string(""));
                    }});
                }
            }
            int ingredientIndex = 0;
            for (SiCraftIngredient ingredient : ingredients) {
                int startIndex = 10;
                if (ingredientIndex >= 3) startIndex = 19;
                if (ingredientIndex >= 6) startIndex = 28;
                setElement(startIndex + ingredientIndex, SiCraftMenus.buildIngredientElement(ingredient));
                ingredientIndex++;
            }

            setElement(22, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(23, new SiMenuElement().setItemStack(buildIcon()));
            setElement(24, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(25, SiCraftMenus.buildIngredientElement(new SiItemStack(result)));

            buildStandardButtons(this, new SiItemStack(result));
        }};
    }
}
