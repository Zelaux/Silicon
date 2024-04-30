package ru.vladislav117.silicon.weapon.item;

import org.bukkit.Material;
import ru.vladislav117.silicon.item.SiItemType;

/**
 * Магазин боеприпасов.
 */
public class SiAmmoMagazineItemType extends SiItemType {
    protected int defaultMaxAmmo;
    protected int defaultAmmo;

    /**
     * Создание нового магазина боеприпасов.
     *
     * @param name           Имя магазина боеприпасов
     * @param material       Материал
     * @param defaultMaxAmmo Максимальный размер магазина по умолчанию
     * @param defaultAmmo    Количество боеприпасов по умолчанию
     */
    public SiAmmoMagazineItemType(String name, Material material, int defaultMaxAmmo, int defaultAmmo) {
        super(name, material);
        usesUuid = true;
        this.defaultMaxAmmo = defaultMaxAmmo;
        this.defaultAmmo = defaultAmmo;
        defaultTags.put("max_ammo", defaultMaxAmmo);
        defaultTags.put("ammo", defaultAmmo);
    }
}
