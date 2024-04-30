package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import ru.vladislav117.silicon.namespace.SiNamespace;

/**
 * Крафт на костре.
 */
public class SiCampfireCraft extends SiFurnaceCraft {
    /**
     * Создание нового крафта на костре.
     *
     * @param result     Результат крафта
     * @param time       Время крафта в тиках
     * @param experience Получаемый опыт
     */
    public SiCampfireCraft(ItemStack result, int time, double experience) {
        super(result, time, experience);
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new CampfireRecipe(SiNamespace.getKey(name), result, ingredient.getRecipeChoice(), (float) experience, time);
    }
}
