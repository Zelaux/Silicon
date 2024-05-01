package ru.vladislav117.silicon.menu;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.icon.SiIcon;
import ru.vladislav117.silicon.icon.SiIcons;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс-контейнер для меню.
 */
public final class SiMenus {
    public static SiIcon rightArrow, leftArrow;
    static HashMap<String, SiMenu> menus = new HashMap<>();

    /**
     * Получение меню по имени.
     *
     * @param name Имя
     * @return Меню или null, если меню с таким именем нет.
     */
    @Nullable
    public static SiMenu get(String name) {
        return menus.get(name);
    }

    /**
     * Добавление меню.
     *
     * @param menu Меню.
     */
    public static void add(SiMenu menu) {
        menus.put(menu.getName(), menu);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiIcons.loaders.addPrimaryLoader(() -> {
            rightArrow = new SiIcon("right_arrow");
            leftArrow = new SiIcon("left_arrow");
        });
    }

    /**
     * Создание многостраничного меню.
     *
     * @param name        Имя меню
     * @param displayName Отображаемое имя меню
     * @param elements    Элементы меню
     * @return Первая страница меню.
     */
    public static SiMenu buildMenuCluster(String name, SiTextLike displayName, ArrayList<SiMenuElement> elements) {
        ArrayList<SiMenu> menus = new ArrayList<>();
        menus.add(new SiMenu(name + "_0", SiMenu.row6size, displayName));
        int elementIndex = 0;
        for (SiMenuElement element : elements) {
            if (elementIndex == 5 * 9) {
                menus.get(menus.size() - 1).setElement(SiMenu.row6size - 2, rightArrow.buildMenuElement(name + (menus.size() - 1) + "_transfer_right").setMenuTransfer(name + "_" + menus.size()).setDisplayName(SiText.string(">>>")));
                menus.add(new SiMenu(name + "_" + menus.size(), SiMenu.row6size, displayName));
                menus.get(menus.size() - 1).setElement(SiMenu.row6size - 2, leftArrow.buildMenuElement(name + (menus.size() - 1) + "_transfer_left").setMenuTransfer(name + "_" + (menus.size() - 2)).setDisplayName(SiText.string(">>>")));
                elementIndex = 0;
            }
            menus.get(menus.size() - 1).setElement(elementIndex, element);
            elementIndex++;
        }
        return menus.get(0);
    }

    /**
     * Создание многостраничного меню.
     *
     * @param name     Имя меню
     * @param elements Элементы меню
     * @return Первая страница меню.
     */
    public static SiMenu buildMenuCluster(String name, ArrayList<SiMenuElement> elements) {
        return buildMenuCluster(name, SiText.string(""), elements);
    }

    public static SiMenu buildFancyMenu(String name, ArrayList<SiMenuElement> elements) {
        return buildMenuCluster(name, elements);
    }
}
