package ru.vladislav117.silicon.displayEntity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.tags.SiTagsManager;

import java.util.UUID;

/**
 * DisplayEntity.
 */
public class SiDisplayEntity {
    @Nullable
    protected ItemDisplay itemDisplay;
    protected UUID uuid;
    protected SiDisplayEntityType type;
    protected SiTagsManager tags;

    /**
     * Создание нового DisplayEntity из ItemDisplay.
     *
     * @param itemDisplay ItemDisplay.
     */
    public SiDisplayEntity(ItemDisplay itemDisplay) {
        this.itemDisplay = itemDisplay;
        uuid = itemDisplay.getUniqueId();
        tags = new SiTagsManager(itemDisplay.getPersistentDataContainer());
        type = SiDisplayEntityTypes.all.get(tags.getString("display_entity_type"));
        SiDisplayEntities.registerDisplayEntity(this);
    }

    /**
     * Получить ItemDisplay.
     *
     * @return ItemDisplay или null, если сущности по какой-то причине нет.
     */
    @Nullable
    public ItemDisplay getItemDisplay() {
        if (itemDisplay == null) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) return null;
            itemDisplay = (ItemDisplay) entity;
        }
        return itemDisplay;
    }

    /**
     * Получить UUID.
     *
     * @return UUID.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Получить тип DisplayEntity.
     *
     * @return Тип DisplayEntity.
     */
    public SiDisplayEntityType getType() {
        return type;
    }

    /**
     * Получить теги DisplayEntity.
     *
     * @return Теги DisplayEntity.
     */
    public SiTagsManager getTags() {
        return tags;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
