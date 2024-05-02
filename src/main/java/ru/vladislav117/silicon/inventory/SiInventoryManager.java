package ru.vladislav117.silicon.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.comparator.SiComparator;
import ru.vladislav117.silicon.filter.item.SiItemFilter;
import ru.vladislav117.silicon.function.SiConverterFunction;
import ru.vladislav117.silicon.item.SiItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Менеджер инвентаря.
 */
public class SiInventoryManager {
    protected Inventory inventory;
    protected int size;

    /**
     * Создание нового менеджера инвентаря.
     *
     * @param inventory Инвентарь
     */
    public SiInventoryManager(Inventory inventory) {
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
     * Получить предмет по индексу слота.
     *
     * @param index Индекс слота
     * @return Предмет по индексу слота
     */
    public ItemStack getItem(int index) {
        ItemStack itemStack = inventory.getItem(index);
        if (itemStack == null) {
            return new ItemStack(Material.AIR);
        }
        return itemStack;
    }

    /**
     * Установить предмет в слот по индексу.
     *
     * @param index     Индекс слота
     * @param itemStack Предмет
     * @return Этот же менеджер инвентаря.
     */
    public SiInventoryManager setItem(int index, ItemStack itemStack) {
        inventory.setItem(index, itemStack);
        return this;
    }

    /**
     * Добавить предмет в инвентарь.
     *
     * @param itemStack Предмет
     * @return То, что не поместилось или null, если были положены все предметы.
     */
    @Nullable
    public ItemStack addItem(ItemStack itemStack) {
        HashMap<Integer, ItemStack> leftItemStacks = inventory.addItem(itemStack);
        for (Integer index : leftItemStacks.keySet()) return leftItemStacks.get(index);
        return null;
    }

    /**
     * Проверка, содержит ли инвентарь хотя бы X подходящих фильтру предметов.
     *
     * @param filter       Фильтр
     * @param targetAmount Количество (X)
     * @return Содержит ли инвентарь хотя бы X подходящих фильтру предметов.
     */
    public boolean containsAtLeast(SiItemFilter filter, int targetAmount) {
        int amount = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            if (SiComparator.isAir(slotItem)) continue;
            if (filter.isSuitable(slotItem)) amount += slotItem.getAmount();
            if (amount >= targetAmount) return true;
        }
        return false;
    }

    /**
     * Подсчитать количество предметов.
     * <p>
     * ВАЖНО! Возможно, будет менее затратно использовать метод containsAtLeast()?
     *
     * @param filter Фильтр
     * @return Количество предметов, подходящих фильтру.
     */
    public int countSuitable(SiItemFilter filter) {
        int amount = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            if (SiComparator.isAir(slotItem)) continue;
            if (filter.isSuitable(slotItem)) amount += slotItem.getAmount();
        }
        return amount;
    }

    /**
     * Удалить определённое количество предметов, подходящих фильтру.
     * <p>
     * ВАЖНО! Вам следует самим проверять наличие количества предметов.
     *
     * @param filter Фильтр
     * @param amount Количество
     * @return Список удалённых предметов.
     */
    public ArrayList<ItemStack> removeSuitable(SiItemFilter filter, int amount) {
        ArrayList<ItemStack> removed = new ArrayList<>();
        int removedAmount = 0;
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            if (SiComparator.isAir(slotItem)) continue;
            if (!filter.isSuitable(slotItem)) continue;
            int leftToRemove = amount - removedAmount;
            int remainingAmount = Math.max(0, slotItem.getAmount() - leftToRemove);
            if (remainingAmount == 0) {
                removedAmount += slotItem.getAmount();
                removed.add(slotItem.clone());
                inventory.setItem(slotIndex, new ItemStack(Material.AIR));
                continue;
            }
            removedAmount += leftToRemove;
            ItemStack removedItem = slotItem.clone();
            removedItem.setAmount(leftToRemove);
            removed.add(removedItem);
            slotItem.setAmount(slotItem.getAmount() - leftToRemove);
            inventory.setItem(slotIndex, slotItem);
        }
        return removed;
    }

    /**
     * Изменить первый найденный по фильтру предмет.
     *
     * @param filter   Фильтр
     * @param function Функция, изменяющая предмет
     * @return Было ли произведено изменение.
     */
    public boolean modifyFirstSuitable(SiItemFilter filter, SiConverterFunction<SiItemStack, SiItemStack> function) {
        for (int slotIndex = 0; slotIndex < size; slotIndex++) {
            ItemStack slotItem = inventory.getItem(slotIndex);
            if (SiComparator.isAir(slotItem)) continue;
            if (!filter.isSuitable(slotItem)) continue;
            inventory.setItem(slotIndex, function.convert(new SiItemStack(slotItem)).toItemStack());
            return true;
        }
        return false;
    }
}
