package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmokingRecipe;
import ru.vladislav117.silicon.namespace.SiNamespace;

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
}
