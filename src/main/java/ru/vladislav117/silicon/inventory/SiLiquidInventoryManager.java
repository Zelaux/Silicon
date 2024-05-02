package ru.vladislav117.silicon.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.filter.liquid.SiLiquidFilter;
import ru.vladislav117.silicon.liquid.SiLiquidStack;
import ru.vladislav117.silicon.liquid.SiLiquidType;
import ru.vladislav117.silicon.liquid.item.SiLiquidContainer;

import java.util.ArrayList;

/**
 * Менеджер жидкостного инвентаря.
 */
public class SiLiquidInventoryManager {
    protected Inventory inventory;
    protected int size;

    /**
     * Создание нового менеджера жидкостного инвентаря.
     *
     * @param inventory Инвентарь
     */
    public SiLiquidInventoryManager(Inventory inventory) {
        this.inventory = inventory;
        size = inventory.getSize();
    }

    /**
     * Получить инвентарь менеджера.
     *
     * @return Инвентарь менеджера.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Получить размер инвентаря.
     *
     * @return Размер инвентаря.
     */
    public int getSize() {
        return size;
    }

    /**
     * Подсчитать объём жидкости.
     *
     * @param filter Фильтр жидкости
     * @return Объём жидкости, подходящей фильтру.
     */
    public int countLiquidVolume(SiLiquidFilter filter) {
        int volume = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (!filter.isSuitable(liquidContainer.getLiquidStack())) continue;
            volume += liquidContainer.getLiquidStack().getVolume();
        }
        return volume;
    }

    /**
     * Проверка, может ли быть добавлена жидкость.
     *
     * @param liquidType Жидкость
     * @param volume     Объём
     * @return Может ли быть добавлена жидкость.
     */
    public boolean canBeAdded(SiLiquidType liquidType, int volume) {
        SiLiquidStack liquidStack = new SiLiquidStack(liquidType);
        int freeVolume = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (!liquidContainer.canBeAdded(liquidStack)) continue;
            freeVolume += liquidContainer.getFreeVolume();
            if (freeVolume >= volume) return true;
        }
        return false;
    }

    /**
     * Добавить жидкостный стак.
     *
     * @param liquidStack Жидкостный стак
     * @return Стак, который не поместился или null, если поместилось всё.
     */
    @Nullable
    public SiLiquidStack addLiquidStack(SiLiquidStack liquidStack) {
        liquidStack = liquidStack.clone();
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (!liquidContainer.canBeAdded(liquidStack)) continue;
            liquidStack = liquidContainer.add(liquidStack);
            inventory.setItem(slotIndex, liquidContainer.toItemStack().toItemStack());
            if (liquidStack == null) return null;
        }
        return liquidStack;
    }

    /**
     * Добавить жидкостный стак "по-умному". Принцип: сначала алгоритм пытается добавить жидкость в контейнеры,
     * где уже есть такая жидкость, а уже потом в пустые контейнеры.
     *
     * @param liquidStack Жидкостный стак
     * @return Стак, который не поместился или null, если поместилось всё.
     */
    @Nullable
    public SiLiquidStack smartlyAddLiquidStack(SiLiquidStack liquidStack) {
        // smart adding with !smart code :)
        liquidStack = liquidStack.clone();
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (!liquidContainer.canBeAdded(liquidStack) || !liquidContainer.getLiquidStack().getLiquidType().equals(liquidStack.getLiquidType()))
                continue;
            liquidStack = liquidContainer.add(liquidStack);
            inventory.setItem(slotIndex, liquidContainer.toItemStack().toItemStack());
            if (liquidStack == null) return null;
        }
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (!liquidContainer.canBeAdded(liquidStack)) continue;
            liquidStack = liquidContainer.add(liquidStack);
            inventory.setItem(slotIndex, liquidContainer.toItemStack().toItemStack());
            if (liquidStack == null) return null;
        }
        return liquidStack;
    }

    /**
     * Может ли быть удалён объём жидкости по фильтру.
     *
     * @param filter       Фильтр жидкости
     * @param targetVolume Объём
     * @return Может ли быть удалён объём жидкости по фильтру.
     */
    public boolean canBeRemoved(SiLiquidFilter filter, int targetVolume) {
        int volume = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (!filter.isSuitable(liquidContainer.getLiquidStack())) continue;
            volume += liquidContainer.getLiquidStack().getVolume();
            if (volume >= targetVolume) return true;
        }
        return false;
    }

    /**
     * Удаление объёма жидкости по фильтру.
     *
     * @param filter Фильтр жидкости
     * @param volume Объём
     * @return Удалённые жидкостные стаки.
     */
    public ArrayList<SiLiquidStack> removeSuitable(SiLiquidFilter filter, int volume) {
        ArrayList<SiLiquidStack> removed = new ArrayList<>();
        int removedVolume = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(slotItem);
            if (liquidContainer == null) continue;
            if (liquidContainer.getLiquidStack().getLiquidType().isVacuum()) continue;
            if (!filter.isSuitable(liquidContainer.getLiquidStack())) continue;
            int remainingVolume = Math.max(0, liquidContainer.getLiquidStack().getVolume() - (volume - removedVolume));
            if (remainingVolume == 0) {
                removedVolume += liquidContainer.getLiquidStack().getVolume();
                removed.add(liquidContainer.getLiquidStack().clone());
                liquidContainer.setEmptyLiquidStack();
                inventory.setItem(slotIndex, liquidContainer.toItemStack().toItemStack());
                continue;
            }
            removedVolume += volume - removedVolume;
            SiLiquidStack removedStack = liquidContainer.getLiquidStack().clone();
            removedStack.setVolume(volume - removedVolume);
            removed.add(removedStack);
            liquidContainer.getLiquidStack().setVolume(remainingVolume);
            inventory.setItem(slotIndex, liquidContainer.toItemStack().toItemStack());
        }
        return removed;
    }
}
