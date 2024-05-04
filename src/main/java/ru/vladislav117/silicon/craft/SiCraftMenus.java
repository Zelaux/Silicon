package ru.vladislav117.silicon.craft;

import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.item.SiItemType;
import ru.vladislav117.silicon.item.category.SiItemCategories;
import ru.vladislav117.silicon.item.category.SiItemCategory;
import ru.vladislav117.silicon.log.SiLog;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.menu.SiMenus;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.ArrayList;
import java.util.HashMap;

public final class SiCraftMenus {
    static SiMenu categoriesMenu;

    public static SiMenu getCategoriesMenu() {
        return categoriesMenu;
    }

    public static String getItemName(SiItemStack itemStack) {
        if (itemStack.getItemType().isUnknown()) return itemStack.getMaterial().name().toLowerCase();
        return itemStack.getItemType().getName();
    }
//
//    public static ArrayList<SiItemCategory> getCategories(SiItemStack itemStack) {
//        if (itemStack.getItemType().isUnknown()) return new ArrayList<>();
//        return itemStack.getItemType().getCategories();
//    }

    public static SiMenuElement buildIngredientElement(SiCraftIngredient ingredient) {
        return buildIngredientElement(new SiItemStack(ingredient.getDisplayItemStack()));
    }

    public static SiMenuElement buildIngredientElement(SiItemStack item, ArrayList<SiTextLike> description) {
        return new SiMenuElement(getItemName(item) + "_ingredient") {{
            setItemStack(item);
            if (description != null) setDescription(description);
            setClickHandler((player, item, event) -> {
                ArrayList<SiCraft> crafts = SiCrafts.getCraftsFor(item);
                if (crafts.size() == 0) return;
                if (crafts.size() == 1) {
                    crafts.get(0).buildMenu(crafts.get(0).getName()).open(player);
                    return;
                }
                SiMenus.buildMenuCluster(getItemName(item) + "_crafts", new ArrayList<>() {{
                    int index = 0;
                    for (SiCraft craft : crafts) {
                        add(new SiMenuElement(getItemName(item) + "_craft_" + index) {{
                            setItemStack(craft.buildIcon());
                            setClickHandler((p, i, e) -> {
                                craft.buildMenu(crafts.get(0).getName()).open(p);
                            });
                        }});
                        index++;
                    }
                }}).open(player);
            });
        }};
    }
    public static SiMenuElement buildIngredientElement(SiItemStack item) {
        return buildIngredientElement(item, null);
    }

    public static SiMenu buildMenuForCategory(SiItemCategory category) {
        return SiMenus.buildMenuCluster(category.getName() + "_category_crafts", category.getDisplayName(), new ArrayList<>() {{
            for (SiItemType itemType : category.getItemTypes()){
                add(buildIngredientElement(itemType.buildItemStack()));
            }
        }});
    }

    public static SiMenuElement buildElementForCategory(SiItemCategory category){
        return new SiMenuElement(category.getName() + "_category_crafts") {{
            setItemStack(category.toItemStack());
            setClickHandler((player, item, ev) -> buildMenuForCategory(category).open(player));
        }};
    }

    public static void init() {
        SiEvents.addHandler(SiBuiltinEvents.SecondaryLoadEndEvent.class, event -> {
            categoriesMenu = SiMenus.buildFancyMenu("categories_crafts", new ArrayList<>() {{
                for (SiItemCategory category : SiItemCategories.all.getAll()) {
                    if (category.getItemTypes().size() == 0) continue;
                    add(buildElementForCategory(category));
                }
            }});
        });
    }
}
