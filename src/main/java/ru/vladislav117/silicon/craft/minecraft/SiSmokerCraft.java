package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmokingRecipe;
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
 * Крафт в коптильне.
 */
public class SiSmokerCraft extends SiFurnaceCraft {
    /**
     * Создание нового крафта в коптильне.
     *
     * @param result     Результат крафта
     * @param time       Время крафта в тиках
     * @param experience Получаемый опыт
     */
    public SiSmokerCraft(ItemStack result, int time, double experience) {
        super(result, time, experience);
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new SmokingRecipe(SiNamespace.getKey(name), result, ingredient.getRecipeChoice(), (float) experience, time);
    }

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.SMOKER){{
            setDescription(new SiLinedText("Предмет получается путём копчения в коптильне").getCompleteTextParts());
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
