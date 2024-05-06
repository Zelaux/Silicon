package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import ru.vladislav117.silicon.craft.SiCraft;
import ru.vladislav117.silicon.craft.SiCraftIngredient;
import ru.vladislav117.silicon.craft.SiCraftMenus;
import ru.vladislav117.silicon.craft.SiCrafts;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.namespace.SiNamespace;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextComponent;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Форменный крафт в верстаке.
 */
public class SiShapedCraft extends SiRecipeCraft {
    protected ArrayList<String> shape = new ArrayList<>();
    protected HashMap<Character, SiCraftIngredient> ingredients = new HashMap<>();

    /**
     * Создание нового форменного крафта.
     *
     * @param result Результат крафта
     */
    public SiShapedCraft(ItemStack result) {
        super(result);
    }

    /**
     * Добавить строку крафта.
     *
     * @param row Строка крафта
     * @return Этот же крафт.
     */
    public SiShapedCraft setRow(String row) {
        shape.add(row);
        return this;
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character  Символ
     * @param ingredient Ингредиент
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, SiCraftIngredient ingredient) {
        ingredients.put(character, ingredient);
        return this;
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character Символ
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, ItemStack itemStack) {
        return setIngredient(character, new SiCraftIngredient(itemStack));
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character Символ
     * @param material  Ингредиент
     * @param amount    Количество
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, Material material, int amount) {
        return setIngredient(character, new SiCraftIngredient(new ItemStack(material, amount)));
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character Символ
     * @param material  Ингредиент
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, Material material) {
        return setIngredient(character, new SiCraftIngredient(new ItemStack(material)));
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character Символ
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, SiItemStack itemStack) {
        return setIngredient(character, new SiCraftIngredient(itemStack.toItemStack()));
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character Символ
     * @param itemType  Ингредиент
     * @param amount    Количество
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, SiItemType itemType, int amount) {
        return setIngredient(character, new SiCraftIngredient(itemType.buildItemStack(amount).toItemStack()));
    }

    /**
     * Назначить ингредиент на символ.
     *
     * @param character Символ
     * @param itemType  Ингредиент
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, SiItemType itemType) {
        return setIngredient(character, new SiCraftIngredient(itemType.buildItemStack().toItemStack()));
    }

    /**
     * Назначить RecipeChoice на символ.
     * <p>
     * ВАЖНО! Крайне не рекомендуется пользоваться данным методом,
     * если вы не добавляете RecipeChoice из ванильных рецептов.
     *
     * @param character    Символ
     * @param recipeChoice RecipeChoice
     * @return Этот же крафт.
     */
    public SiShapedCraft setIngredient(char character, RecipeChoice recipeChoice) {
        return setIngredient(character, SiCraftIngredient.buildFromRecipeChoice(recipeChoice));
    }

    @Override
    public SiCraft addIngredient(SiCraftIngredient ingredient) {
        return this;
    }

    @Override
    public Recipe buildRecipe(String name) {
        ShapedRecipe recipe = new ShapedRecipe(SiNamespace.getKey(name), getResult());
        if (shape.size() == 1) {
            recipe.shape(shape.get(0));
        } else if (shape.size() == 2) {
            recipe.shape(shape.get(0), shape.get(1));
        } else if (shape.size() == 3) {
            recipe.shape(shape.get(0), shape.get(1), shape.get(2));
        }
        for (char character : ingredients.keySet()) {
            if (ingredients.get(character) == null) continue;
            recipe.setIngredient(character, ingredients.get(character).getRecipeChoice());
        }
        return recipe;
    }

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.CRAFTING_TABLE) {{
            setDescription(new SiLinedText("Предмет получается путём крафта на верстаке").getCompleteTextParts());
        }};
    }

    @Override
    public SiMenu buildMenu(String name) {
        ItemStack result = getResult();
        return new SiMenu(name, SiMenu.row6size, new SiTextComponent(result.displayName())) {{
            for (int row : new int[]{10, 19, 28}) {
                for (int i = 0; i < 3; i++) {
                    setElement(row + i, new SiMenuElement() {{
                        setItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                        setDisplayName(SiText.string(""));
                    }});
                }
            }
            if (shape.size() >= 1) {
                int charIndex = 0;
                for (Character character : shape.get(0).toCharArray()) {
                    SiCraftIngredient ingredient = ingredients.get(character);
                    if (character.equals(' ') || ingredient == null) {
                        charIndex++;
                        continue;
                    }
                    setElement(10 + charIndex, SiCraftMenus.buildIngredientElement(ingredient));
                    charIndex++;
                }
            }
            if (shape.size() >= 2) {
                int charIndex = 0;
                for (Character character : shape.get(1).toCharArray()) {
                    SiCraftIngredient ingredient = ingredients.get(character);
                    if (character.equals(' ') || ingredient == null) {
                        charIndex++;
                        continue;
                    }
                    setElement(19 + charIndex, SiCraftMenus.buildIngredientElement(ingredient));
                    charIndex++;
                }
            }
            if (shape.size() >= 3) {
                int charIndex = 0;
                for (Character character : shape.get(2).toCharArray()) {
                    SiCraftIngredient ingredient = ingredients.get(character);
                    if (character.equals(' ') || ingredient == null) {
                        charIndex++;
                        continue;
                    }
                    setElement(28 + charIndex, SiCraftMenus.buildIngredientElement(ingredient));
                    charIndex++;
                }
            }

            setElement(22, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(23, new SiMenuElement().setItemStack(buildIcon()));
            setElement(24, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(25, SiCraftMenus.buildIngredientElement(new SiItemStack(result)));

            buildStandardButtons(this, results);
        }};
    }
}
