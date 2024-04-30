package ru.vladislav117.silicon.displayEntity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.tags.SiTagsManager;
import ru.vladislav117.silicon.ticker.SiServerLoadTicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Класс-контейнер для обработки DisplayEntities.
 */
public class SiDisplayEntities {
    static HashMap<UUID, SiDisplayEntity> displayEntitiesRegistry = new HashMap<>();
    static HashSet<SiDisplayEntity> displayEntities = new HashSet<>();
    static ArrayList<SiDisplayEntity> displayEntitiesToAdd = new ArrayList<>();

    /**
     * Зарегистрировать новый DisplayEntity. Он будет добавлен в очередь на добавление.
     *
     * @param displayEntity DisplayEntity
     */
    public static void registerDisplayEntity(SiDisplayEntity displayEntity) {
        displayEntitiesToAdd.add(displayEntity);
    }

    /**
     * Удалить DisplayEntity из реестра.
     *
     * @param displayEntity DisplayEntity.
     */
    public static void removeDisplayEntity(SiDisplayEntity displayEntity) {
        displayEntitiesRegistry.remove(displayEntity.getUuid());
        displayEntities.remove(displayEntity);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onEntitySpawnEvent(EntitySpawnEvent event) {
                if (event.isCancelled()) return;
                if (!(event.getEntity() instanceof ItemDisplay itemDisplay)) return;
                SiTagsManager tags = new SiTagsManager(itemDisplay.getPersistentDataContainer());
                if (!tags.hasTag("display_entity_type")) return;
                new SiDisplayEntity(itemDisplay);
            }

            @EventHandler
            public void onEntitiesLoadEvent(EntitiesLoadEvent event) {
                for (Entity entity : event.getEntities()) {
                    if (!(entity instanceof ItemDisplay itemDisplay)) continue;
                    SiTagsManager tags = new SiTagsManager(itemDisplay.getPersistentDataContainer());
                    if (!tags.hasTag("display_entity_type")) continue;
                    new SiDisplayEntity(itemDisplay);
                }
            }
        });

        new SiServerLoadTicker(ticker -> {
            for (SiDisplayEntity displayEntity : displayEntitiesToAdd) {
                displayEntitiesRegistry.put(displayEntity.getUuid(), displayEntity);
                displayEntities.add(displayEntity);
            }
            displayEntitiesToAdd.clear();

            ArrayList<SiDisplayEntity> displayEntitiesToRemove = new ArrayList<>();
            for (SiDisplayEntity displayEntity : displayEntities) {
                if (displayEntity.getItemDisplay() == null) continue;
                if (displayEntity.getType().getActions() == null) continue;
                displayEntity.getType().getActions().update(displayEntity);
                if (displayEntity.getItemDisplay().isDead()) {
                    displayEntitiesToRemove.add(displayEntity);
                }
            }
            for (SiDisplayEntity displayEntity : displayEntitiesToRemove) removeDisplayEntity(displayEntity);
        });
    }
}
