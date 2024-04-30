package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import ru.vladislav117.silicon.namespace.SiNamespace;

/**
 * Крафт в плавильной печи.
 */
public class SiBlastFurnaceCraft extends SiFurnaceCraft {
    /**
     * Создание нового крафта в плавильной печи.
     *
     * @param result     Результат крафта
     * @param time       Время крафта в тиках
     * @param experience Получаемый опыт
     */
    public SiBlastFurnaceCraft(ItemStack result, int time, double experience) {
        super(result, time, experience);
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new BlastingRecipe(SiNamespace.getKey(name), result, ingredient.getRecipeChoice(), (float) experience, time);
    }
}
