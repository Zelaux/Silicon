package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingTrimRecipe;
import ru.vladislav117.silicon.namespace.SiNamespace;

/**
 * Крафт наложения шаблона на кузнечном столе.
 */
public class SiSmithingTrimCraft extends SiSmithingTransformCraft {
    /**
     * Создание нового крафта наложения шаблона на кузнечном столе. NBT теги по умолчанию сохраняются.
     *
     * @param result Результат крафта
     */
    public SiSmithingTrimCraft(ItemStack result) {
        super(result);
    }

    /**
     * Создание нового крафта наложения шаблона на кузнечном столе.
     *
     * @param result  Результат крафта
     * @param saveNbt Сохранять ли NBT теги
     */
    public SiSmithingTrimCraft(ItemStack result, boolean saveNbt) {
        super(result, saveNbt);
    }

    @Override
    public Recipe buildRecipe(String name) {
        return new SmithingTrimRecipe(SiNamespace.getKey(name), template.getRecipeChoice(), base.getRecipeChoice(), addition.getRecipeChoice(), saveNbt);
    }
}
