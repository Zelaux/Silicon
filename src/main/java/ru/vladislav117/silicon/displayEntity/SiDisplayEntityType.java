package ru.vladislav117.silicon.displayEntity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.tags.SiTagsManager;

import java.util.HashMap;

/**
 * Тип DisplayEntity.
 */
public class SiDisplayEntityType extends SiContent {
    protected Material material;
    protected SiDisplayEntityTexture texture = null;
    protected SiDisplayEntityActions actions = null;

    /**
     * Создание нового типа DisplayEntity.
     *
     * @param name Имя контента
     */
    public SiDisplayEntityType(String name, Material material) {
        super(name);
        this.material = material;
        SiDisplayEntityTypes.all.add(this);
    }

    /**
     * Получение материала типа.
     *
     * @return Материал типа.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Получение текстуры.
     *
     * @return Текстура или null.
     */
    @Nullable
    public SiDisplayEntityTexture getTexture() {
        return texture;
    }

    /**
     * Получение взаимодействий.
     *
     * @return Взаимодействия.
     */
    @Nullable
    public SiDisplayEntityActions getActions() {
        return actions;
    }

    /**
     * Создать стак для DisplayEntity.
     *
     * @param displayEntity DisplayEntity
     * @param material      Материал
     * @return Стак для DisplayEntity.
     */
    public SiItemStack buildItemStack(SiDisplayEntity displayEntity, Material material) {
        return new SiItemStack(material);
    }

    /**
     * Создать DisplayEntity.
     *
     * @param location  Позиция
     * @param direction Направление
     * @param tags      Теги
     * @return Созданный DisplayEntity.
     */
    public SiDisplayEntity spawn(Location location, Vector direction, HashMap<String, Object> tags) {
        ItemDisplay itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
        itemDisplay.getLocation().setDirection(direction);
        SiTagsManager entityTags = new SiTagsManager(itemDisplay.getPersistentDataContainer());
        entityTags.setTags(tags);
        entityTags.setTag("display_entity_type", name);
        SiDisplayEntity displayEntity = new SiDisplayEntity(itemDisplay);
        itemDisplay.setItemStack(buildItemStack(displayEntity, material).toItemStack());
        if (texture != null) {
            SiItemStack itemStack = new SiItemStack(itemDisplay.getItemStack());
            itemStack.setCustomModelData(texture.getCustomModelData(displayEntity));
            displayEntity.getItemDisplay().setItemStack(itemStack.toItemStack());
        }
        return displayEntity;
    }
}
