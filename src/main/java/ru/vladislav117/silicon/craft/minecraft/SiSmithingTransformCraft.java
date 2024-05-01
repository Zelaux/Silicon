package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingTransformRecipe;
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

/**
 * Крафт преобразования предмета на кузнечном столе.
 */
public class SiSmithingTransformCraft extends SiRecipeCraft {
    protected boolean saveNbt = true;
    protected SiCraftIngredient template = null;
    protected SiCraftIngredient base = null;
    protected SiCraftIngredient addition = null;

    /**
     * Создание нового крафта преобразования предмета на кузнечном столе. NBT теги по умолчанию сохраняются.
     *
     * @param result Результат крафта
     */
    public SiSmithingTransformCraft(ItemStack result) {
        super(result);
    }

    /**
     * Создание нового крафта преобразования предмета на кузнечном столе.
     *
     * @param result  Результат крафта
     * @param saveNbt Сохранять ли NBT теги
     */
    public SiSmithingTransformCraft(ItemStack result, boolean saveNbt) {
        super(result);
        this.saveNbt = saveNbt;
    }

    /**
     * Получить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @return Ингредиент шаблона.
     */
    public SiCraftIngredient getTemplate() {
        return template;
    }

    /**
     * Получить ингредиент основы (броня/оружие, второй слот).
     *
     * @return Ингредиент основы.
     */
    public SiCraftIngredient getBase() {
        return base;
    }

    /**
     * Получить ингредиент дополнения (ресурс, третий слот).
     *
     * @return Ингредиент дополнения.
     */
    public SiCraftIngredient getAddition() {
        return addition;
    }

    @Override
    public SiCraft addIngredient(SiCraftIngredient ingredient) {
        if (template == null) {
            template = ingredient;
        } else if (base == null) {
            base = ingredient;
        } else if (addition == null) {
            addition = ingredient;
        }
        return this;
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param ingredient Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(SiCraftIngredient ingredient) {
        template = ingredient;
        return this;
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(ItemStack itemStack) {
        return setTemplate(new SiCraftIngredient(itemStack));
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param material Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(Material material, int amount) {
        return setTemplate(new SiCraftIngredient(new ItemStack(material, amount)));
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param material Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(Material material) {
        return setTemplate(new SiCraftIngredient(new ItemStack(material)));
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(SiItemStack itemStack) {
        return setTemplate(new SiCraftIngredient(itemStack.toItemStack()));
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param itemType Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(SiItemType itemType, int amount) {
        return setTemplate(new SiCraftIngredient(itemType.buildItemStack(amount).toItemStack()));
    }

    /**
     * Установить ингредиент шаблона (шаблон брони/шаблон улучшения, первый слот).
     *
     * @param itemType Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(SiItemType itemType) {
        return setTemplate(new SiCraftIngredient(itemType.buildItemStack().toItemStack()));
    }

    /**
     * Установить ингредиент шаблона как RecipeChoice (шаблон брони/шаблон улучшения, первый слот).
     * <p>
     * ВАЖНО! Крайне не рекомендуется пользоваться данным методом,
     * если вы не добавляете RecipeChoice из ванильных рецептов.
     *
     * @param recipeChoice RecipeChoice
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setTemplate(RecipeChoice recipeChoice) {
        return setTemplate(SiCraftIngredient.buildFromRecipeChoice(recipeChoice));
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param ingredient Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(SiCraftIngredient ingredient) {
        base = ingredient;
        return this;
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(ItemStack itemStack) {
        return setBase(new SiCraftIngredient(itemStack));
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param material Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(Material material, int amount) {
        return setBase(new SiCraftIngredient(new ItemStack(material, amount)));
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param material Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(Material material) {
        return setBase(new SiCraftIngredient(new ItemStack(material)));
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(SiItemStack itemStack) {
        return setBase(new SiCraftIngredient(itemStack.toItemStack()));
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param itemType Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(SiItemType itemType, int amount) {
        return setBase(new SiCraftIngredient(itemType.buildItemStack(amount).toItemStack()));
    }

    /**
     * Установить ингредиент основы (броня/оружие, второй слот).
     *
     * @param itemType Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(SiItemType itemType) {
        return setBase(new SiCraftIngredient(itemType.buildItemStack().toItemStack()));
    }

    /**
     * Установить ингредиент основы как RecipeChoice (броня/оружие, второй слот).
     * <p>
     * ВАЖНО! Крайне не рекомендуется пользоваться данным методом,
     * если вы не добавляете RecipeChoice из ванильных рецептов.
     *
     * @param recipeChoice RecipeChoice
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setBase(RecipeChoice recipeChoice) {
        return setBase(SiCraftIngredient.buildFromRecipeChoice(recipeChoice));
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param ingredient Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(SiCraftIngredient ingredient) {
        addition = ingredient;
        return this;
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(ItemStack itemStack) {
        return setAddition(new SiCraftIngredient(itemStack));
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param material Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(Material material, int amount) {
        return setAddition(new SiCraftIngredient(new ItemStack(material, amount)));
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param material Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(Material material) {
        return setAddition(new SiCraftIngredient(new ItemStack(material)));
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param itemStack Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(SiItemStack itemStack) {
        return setAddition(new SiCraftIngredient(itemStack.toItemStack()));
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param itemType Ингредиент
     * @param amount   Количество
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(SiItemType itemType, int amount) {
        return setAddition(new SiCraftIngredient(itemType.buildItemStack(amount).toItemStack()));
    }

    /**
     * Установить ингредиент дополнения (ресурс, третий слот).
     *
     * @param itemType Ингредиент
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(SiItemType itemType) {
        return setAddition(new SiCraftIngredient(itemType.buildItemStack().toItemStack()));
    }

    /**
     * Установить ингредиент дополнения как RecipeChoice (ресурс, третий слот).
     * <p>
     * ВАЖНО! Крайне не рекомендуется пользоваться данным методом,
     * если вы не добавляете RecipeChoice из ванильных рецептов.
     *
     * @param recipeChoice RecipeChoice
     * @return Этот же крафт.
     */
    public SiSmithingTransformCraft setAddition(RecipeChoice recipeChoice) {
        return setAddition(SiCraftIngredient.buildFromRecipeChoice(recipeChoice));
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new SmithingTransformRecipe(SiNamespace.getKey(name), result, template.getRecipeChoice(), base.getRecipeChoice(), addition.getRecipeChoice(), saveNbt);
    }

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.SMITHING_TABLE){{
            setDescription(new SiLinedText("Предмет получается путём создания на столе кузнеца").getCompleteTextParts());
        }};
    }

    @Override
    public SiMenu buildMenu(String name) {
        return new SiMenu(name, SiMenu.row6size, new SiTextComponent(result.displayName())) {{
            setElement(19, SiCraftMenus.buildIngredientElement(template));
            setElement(20, SiCraftMenus.buildIngredientElement(base));
            setElement(21, SiCraftMenus.buildIngredientElement(addition));
            setElement(22, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(23, new SiMenuElement().setItemStack(buildIcon()));
            setElement(24, SiCrafts.rightArrowIcon.buildMenuElement().setDisplayName(SiText.string("")));
            setElement(25, SiCraftMenus.buildIngredientElement(new SiItemStack(result)));

            buildStandardButtons(this, new SiItemStack(result));
        }};
    }
}
