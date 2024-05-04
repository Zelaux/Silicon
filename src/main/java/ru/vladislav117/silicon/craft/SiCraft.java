package ru.vladislav117.silicon.craft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.item.category.SiItemCategory;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.structure.SiLinedText;

import java.util.ArrayList;

/**
 * Абстрактный крафт.
 */
public abstract class SiCraft {
    @Nullable
    protected String name = null;
    protected ItemStack result;

    /**
     * Создание нового крафта.
     *
     * @param result Результат крафта
     */
    public SiCraft(ItemStack result) {
        this.result = result.clone();
        SiCrafts.add(this);
    }

    /**
     * Получение результата крафта.
     *
     * @return Результат крафта.
     */
    public ItemStack getResult() {
        return result;
    }

    public ArrayList<ItemStack> getResults() {
        return new ArrayList<>() {{
            add(result);
        }};
    }

    /**
     * Получить имя крафта.
     *
     * @return Имя крафта или null, если имя ещё не назначено.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * Назначить имя крафту.
     *
     * @param name Имя
     * @return Этот же крафт.
     */
    public SiCraft setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Добавить ингредиент в крафт.
     *
     * @param ingredient Ингредиент
     * @return Этот же крафт.
     */
    public abstract SiCraft addIngredient(SiCraftIngredient ingredient);

    /**
     * Добавить ингредиент в крафт.
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(ItemStack itemStack) {
        return addIngredient(new SiCraftIngredient(itemStack));
    }

    /**
     * Добавить ингредиент в крафт.
     *
     * @param material Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(Material material, int amount) {
        return addIngredient(new SiCraftIngredient(new ItemStack(material, amount)));
    }

    /**
     * Добавить ингредиент в крафт.
     *
     * @param material Ингредиент
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(Material material) {
        return addIngredient(new SiCraftIngredient(new ItemStack(material)));
    }

    /**
     * Добавить ингредиент в крафт.
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(SiItemStack itemStack) {
        return addIngredient(new SiCraftIngredient(itemStack.toItemStack()));
    }

    /**
     * Добавить ингредиент в крафт.
     *
     * @param itemType Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(SiItemType itemType, int amount) {
        return addIngredient(new SiCraftIngredient(itemType.buildItemStack(amount).toItemStack()));
    }

    /**
     * Добавить ингредиент в крафт.
     *
     * @param itemType Ингредиент
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(SiItemType itemType) {
        return addIngredient(new SiCraftIngredient(itemType.buildItemStack().toItemStack()));
    }

    /**
     * Добавить ингредиент в крафт как RecipeChoice.
     * <p>
     * ВАЖНО! Крайне не рекомендуется пользоваться данным методом,
     * если вы не добавляете RecipeChoice из ванильных рецептов.
     *
     * @param recipeChoice RecipeChoice
     * @return Этот же крафт.
     */
    public SiCraft addIngredient(RecipeChoice recipeChoice) {
        return addIngredient(SiCraftIngredient.buildFromRecipeChoice(recipeChoice));
    }

    public abstract SiItemStack buildIcon();

    public abstract SiMenu buildMenu(String name);

    protected void buildStandardButtons(SiMenu menu, SiItemStack result) {
        menu.setElement(45, SiCrafts.allCategories.buildMenuElement(menu.getName() + "_category_crafts").setDisplayName(SiText.string("Все категории")).setClickHandler((player, itemStack, event) -> {
            SiCraftMenus.getCategoriesMenu().open(player);
        }));
        int categoryIndex = 0;
        for (SiItemCategory category : result.getItemType().getCategories()) {
            menu.setElement(46 + categoryIndex, SiCraftMenus.buildElementForCategory(category));
            categoryIndex++;
            if (categoryIndex >= 53) break;
        }
        menu.setElement(53, SiCrafts.infoCrafts.buildMenuElement().setDescription(new SiLinedText("Не знаете как скрафтить какой-либо из предметов? Нажмите на него, и вы увидите рецепты для этого предмета.").getCompleteTextParts()));
    }
}
