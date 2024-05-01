package ru.vladislav117.silicon.craft.minecraft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingTrimRecipe;
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

    @Override
    public SiItemStack buildIcon() {
        return new SiItemStack(Material.SMITHING_TABLE){{
            setDescription(new SiLinedText("Предмет получается путём применения шаблона на столе кузнеца").getCompleteTextParts());
        }};
    }
}
