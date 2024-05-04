package ru.vladislav117.silicon.craft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import ru.vladislav117.silicon.craft.minecraft.*;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.function.SiConverterFunction;
import ru.vladislav117.silicon.icon.SiIcon;
import ru.vladislav117.silicon.icon.SiIcons;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.menu.SiMenus;
import ru.vladislav117.silicon.utils.ClassUtils;

import java.util.*;

/**
 * Класс-контейнер для крафтов.
 */
public class SiCrafts {
    static ArrayList<Recipe> complexRecipes = new ArrayList<>();
    static ArrayList<SiCraft> crafts = new ArrayList<>();
    static HashMap<Material, ArrayList<SiCraft>> craftsByMaterials = new HashMap<>();
    static HashMap<SiItemType, ArrayList<SiCraft>> craftsByItemTypes = new HashMap<>();

    public static SiIcon rightArrowIcon, allCategories, infoCrafts;

    /**
     * Добавить крафт в список по материалам.
     *
     * @param material Материал
     * @param craft    Крафт
     */
    static void addCraftByMaterial(Material material, SiCraft craft) {
        if (!craftsByMaterials.containsKey(material)) craftsByMaterials.put(material, new ArrayList<>());
        craftsByMaterials.get(material).add(craft);
    }

    /**
     * Добавить крафт в список по кастомным типам.
     *
     * @param itemType Кастомный тип предмета
     * @param craft    Крафт
     */
    static void addCraftByItemType(SiItemType itemType, SiCraft craft) {
        if (!craftsByItemTypes.containsKey(itemType)) craftsByItemTypes.put(itemType, new ArrayList<>());
        craftsByItemTypes.get(itemType).add(craft);
    }

    /**
     * Получение всех крафтов материала материала.
     *
     * @param material Материал
     * @return Список крафтов
     */
    public static ArrayList<SiCraft> getCraftsFor(Material material) {
        return craftsByMaterials.getOrDefault(material, new ArrayList<>(0));
    }

    /**
     * Получение всех крафтов кастомного типа предмета.
     *
     * @param itemType Тип предмета
     * @return Список крафтов
     */
    public static ArrayList<SiCraft> getCraftsFor(SiItemType itemType) {
        return craftsByItemTypes.getOrDefault(itemType, new ArrayList<>(0));
    }

    /**
     * Получение всех крафтов предмета.
     *
     * @param itemStack Предмет
     * @return Список крафтов
     */
    public static ArrayList<SiCraft> getCraftsFor(SiItemStack itemStack) {
        if (itemStack.getItemType().isUnknown()) return  craftsByMaterials.getOrDefault(itemStack.getMaterial(), new ArrayList<>(0));
        return craftsByItemTypes.getOrDefault(itemStack.getItemType(), new ArrayList<>(0));
    }

    /**
     * Добавить крафт.
     *
     * @param craft Крафт
     */
    public static void add(SiCraft craft) {
        crafts.add(craft);
        for (ItemStack itemStack : craft.getResults()) {
            SiItemStack result = new SiItemStack(itemStack);
            if (result.getItemType().isUnknown()) {
                addCraftByMaterial(result.getMaterial(), craft);
            } else {
                addCraftByItemType(result.getItemType(), craft);
            }
        }
    }

    /**
     * Обновить рецепты. Будут удалены все рецепты и пересозданы новые.
     */
    public static void updateRecipes() {
        CraftNameSystem.updateCrafts();
        Bukkit.clearRecipes();
        complexRecipes.forEach(Bukkit::addRecipe);
        for (SiCraft craft : crafts) {
            craft.setName(CraftNameSystem.calculateName(craft));
            if (craft instanceof SiRecipeCraft recipeCraft) {
                Bukkit.addRecipe(recipeCraft.buildRecipe(craft.getName()));
            }
        }
        Bukkit.updateRecipes();
    }

    /**
     * Инициализация крафтов.
     */
    public static void init() {
        CraftNameSystem.init();

        SiIcons.loaders.addPrimaryLoader(() -> {
            rightArrowIcon = new SiIcon("crafts_right_arrow");
            allCategories = new SiIcon("all_categories");
            infoCrafts = new SiIcon("info_crafts");
        });

        SiEvents.addHandler(SiBuiltinEvents.SecondaryLoadEndEvent.class, event -> {
            Iterator<Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                Recipe recipe = iterator.next();
                if (recipe instanceof BlastingRecipe blastingRecipe) {
                    new SiBlastFurnaceCraft(blastingRecipe.getResult(), blastingRecipe.getCookingTime(), blastingRecipe.getExperience()).addIngredient(blastingRecipe.getInputChoice());
                    continue;
                }
                if (recipe instanceof CampfireRecipe campfireRecipe) {
                    new SiCampfireCraft(campfireRecipe.getResult(), campfireRecipe.getCookingTime(), campfireRecipe.getExperience()).addIngredient(campfireRecipe.getInputChoice());
                    continue;
                }
                if (recipe instanceof SmokingRecipe smokingRecipe) {
                    new SiSmokerCraft(smokingRecipe.getResult(), smokingRecipe.getCookingTime(), smokingRecipe.getExperience()).addIngredient(smokingRecipe.getInputChoice());
                    continue;
                }
                if (recipe instanceof FurnaceRecipe furnaceRecipe) {
                    new SiFurnaceCraft(furnaceRecipe.getResult(), furnaceRecipe.getCookingTime(), furnaceRecipe.getExperience()).addIngredient(furnaceRecipe.getInputChoice());
                    continue;
                }
                if (recipe instanceof StonecuttingRecipe stonecuttingRecipe) {
                    new SiStonecutterCraft(stonecuttingRecipe.getResult()).addIngredient(stonecuttingRecipe.getInputChoice());
                    continue;
                }
                if (recipe instanceof ShapedRecipe shapedRecipe) {
                    new SiShapedCraft(shapedRecipe.getResult()) {{
                        for (String row : shapedRecipe.getShape()) setRow(row);
                        Map<Character, RecipeChoice> choiceMap = shapedRecipe.getChoiceMap();
                        for (Character character : choiceMap.keySet()) {
                            setIngredient(character, choiceMap.get(character));
                        }
                    }};
                    continue;
                }
                if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                    new SiShapelessCraft(shapelessRecipe.getResult()) {{
                        for (RecipeChoice recipeChoice : shapelessRecipe.getChoiceList()) addIngredient(recipeChoice);
                    }};
                    continue;
                }
                if (recipe instanceof SmithingTransformRecipe smithingTransformRecipe) {
                    new SiSmithingTransformCraft(smithingTransformRecipe.getResult(), smithingTransformRecipe.willCopyNbt()) {{
                        setTemplate(smithingTransformRecipe.getTemplate());
                        setBase(smithingTransformRecipe.getBase());
                        setAddition(smithingTransformRecipe.getAddition());
                    }};
                    continue;
                }
                if (recipe instanceof SmithingTrimRecipe smithingTrimRecipe) {
                    new SiSmithingTrimCraft(smithingTrimRecipe.getResult(), smithingTrimRecipe.willCopyNbt()) {{
                        setTemplate(smithingTrimRecipe.getTemplate());
                        setBase(smithingTrimRecipe.getBase());
                        setAddition(smithingTrimRecipe.getAddition());
                    }};
                    continue;
                }
                complexRecipes.add(recipe);
            }
            updateRecipes();
        });
    }

    /**
     * Система имён крафтов.
     */
    public static class CraftNameSystem {
        static HashSet<String> registeredNames = new HashSet<>();
        static HashMap<Class<? extends SiCraft>, SiConverterFunction<SiCraft, String>> converters = new HashMap<>();

        /**
         * Добавить конвертер имени крафта.
         *
         * @param klass     Класс крафта
         * @param converter Конвертер
         */
        public static void addConverter(Class<? extends SiCraft> klass, SiConverterFunction<SiCraft, String> converter) {
            converters.put(klass, converter);
        }

        /**
         * Вызывается при обновлении крафтов, очищает список зарегистрированных имён.
         */
        public static void updateCrafts() {
            registeredNames.clear();
        }

        /**
         * Поиск незанятого имени.
         *
         * @param name Оригинальное имя
         * @return То же имя или "имя.индекс".
         */
        static String findName(String name) {
            if (!registeredNames.contains(name)) return name;
            int index = 0;
            while (true) {
                if (!registeredNames.contains(name + "." + index)) return name + "." + index;
                index++;
            }
        }

        /**
         * Получить имя типа стака.
         *
         * @param itemStack Стак
         * @return Имя типа стака.
         */
        public static String getTypeName(ItemStack itemStack) {
            SiItemType itemType = new SiItemStack(itemStack).getItemType();
            if (itemType.isUnknown()) return itemStack.getType().name().toLowerCase(Locale.ROOT);
            return itemType.getName();
        }

        /**
         * Рассчитывает имя для крафта и добавляет его в список зарегистрированных имён.
         *
         * @param craft Крафт
         * @return Имя крафта.
         */
        public static String calculateName(SiCraft craft) {
            Class<?> craftClass = ClassUtils.getOriginalClass(craft);
            if (converters.containsKey(craftClass)) {
                String name = findName(converters.get(craftClass).convert(craft));
                registeredNames.add(name);
                return name;
            }
            String name = findName(getTypeName(craft.getResult()) + "_craft");
            registeredNames.add(name);
            return name;
        }

        /**
         * Инициализация системы имён крафтов.
         */
        public static void init() {
            addConverter(SiBlastFurnaceCraft.class, craft -> {
                SiBlastFurnaceCraft blastFurnaceCraft = (SiBlastFurnaceCraft) craft;
                return getTypeName(blastFurnaceCraft.getResult()) + "_by_blast_furnace_from_" + getTypeName(blastFurnaceCraft.getIngredient().getDisplayItemStack());
            });
            addConverter(SiCampfireCraft.class, craft -> {
                SiCampfireCraft campfireCraft = (SiCampfireCraft) craft;
                return getTypeName(campfireCraft.getResult()) + "_by_campfire_from_" + getTypeName(campfireCraft.getIngredient().getDisplayItemStack());
            });
            addConverter(SiFurnaceCraft.class, craft -> {
                SiFurnaceCraft furnaceCraft = (SiFurnaceCraft) craft;
                return getTypeName(furnaceCraft.getResult()) + "_by_furnace_from_" + getTypeName(furnaceCraft.getIngredient().getDisplayItemStack());
            });
            addConverter(SiSmokerCraft.class, craft -> {
                SiSmokerCraft smokerCraft = (SiSmokerCraft) craft;
                return getTypeName(smokerCraft.getResult()) + "_by_smoker_from_" + getTypeName(smokerCraft.getIngredient().getDisplayItemStack());
            });
            addConverter(SiStonecutterCraft.class, craft -> {
                SiStonecutterCraft stonecutterCraft = (SiStonecutterCraft) craft;
                return getTypeName(stonecutterCraft.getResult()) + "_by_stonecutter_from_" + getTypeName(stonecutterCraft.getIngredient().getDisplayItemStack());
            });
            addConverter(SiShapelessCraft.class, craft -> getTypeName(craft.getResult()) + "_by_crafting_table");
            addConverter(SiShapedCraft.class, craft -> getTypeName(craft.getResult()) + "_by_crafting_table");
            addConverter(SiSmithingTransformCraft.class, craft -> getTypeName(craft.getResult()) + "_by_smithing_table");
            addConverter(SiSmithingTrimCraft.class, craft -> {
                SiSmithingTrimCraft smithingTrimCraft = (SiSmithingTrimCraft) craft;
                return getTypeName(smithingTrimCraft.getBase().getDisplayItemStack()) + "_with_" + getTypeName(smithingTrimCraft.getTemplate().getDisplayItemStack()) + "_and_" + getTypeName(smithingTrimCraft.getAddition().getDisplayItemStack()) + "_by_smithing_table";
            });
        }
    }
}
