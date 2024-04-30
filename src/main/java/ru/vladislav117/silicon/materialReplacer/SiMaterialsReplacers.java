package ru.vladislav117.silicon.materialReplacer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.item.SiItemStack;

import java.util.HashMap;

/**
 * Заменители материалов.
 */
public final class SiMaterialsReplacers {
    static HashMap<Material, SiMaterialReplacerItemType> materials = new HashMap<>();

    /**
     * Добавление заменителя материалов.
     *
     * @param itemType Заменитель материала.
     */
    public static void add(SiMaterialReplacerItemType itemType) {
        materials.put(itemType.getMaterial(), itemType);
    }

    /**
     * Получение заменителя материала.
     *
     * @param material Материал
     * @return Заменитель материала или null, если заменителя нет.
     */
    @Nullable
    public static SiMaterialReplacerItemType get(Material material) {
        return materials.get(material);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onItemSpawnEvent(ItemSpawnEvent event) {
                if (event.isCancelled()) return;
                ItemStack itemStack = event.getEntity().getItemStack();
                if (!materials.containsKey(itemStack.getType())) return;
                SiItemStack stack = new SiItemStack(itemStack);
                if (!stack.getItemType().isUnknown()) return;
                stack.getTagsManager().setString("item_type", materials.get(stack.getMaterial()).getName());
                stack.updateDisplay();
                event.getEntity().setItemStack(stack.toItemStack());
            }
        });
    }
}
