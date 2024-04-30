package ru.vladislav117.silicon.weapon.bullet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.displayEntity.*;

import java.util.Collection;
import java.util.List;

/**
 * Класс-контейнер для типов снарядов.
 */
public final class SiBulletTypes {
    public static final SiContentList<SiBulletType> all = new SiContentList<>();

    public static SiDisplayEntityType bulletDisplayEntity;

    public static SiBulletType unknown;

    public static SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            unknown = new SiBulletType("unknown");
        });

        loaders.addSecondaryLoader(() -> {
        });

        SiDisplayEntityTypes.loaders.addPrimaryLoader(() -> {
            bulletDisplayEntity = new SiDisplayEntityType("bullet", Material.KNOWLEDGE_BOOK) {{
                texture = new SiDisplayEntityTexture.FeatureTexture(this, "bullet_type", unknown.getName(), List.of(all.getAllNames()));
                actions = new SiDisplayEntityActions() {
                    @Override
                    public void spawn(SiDisplayEntity displayEntity) {
                        displayEntity.getItemDisplay().setTeleportDuration((int) displayEntity.getTags().getDouble("speed", 1));
                    }

                    @Override
                    public void update(SiDisplayEntity displayEntity) {
                        ItemDisplay itemDisplay = displayEntity.getItemDisplay();
                        int lifetime = displayEntity.getTags().getInteger("lifetime", 0) - 1;
                        if (lifetime <= 0) {
                            SiBulletType bulletType = SiBulletTypes.all.get(displayEntity.getTags().getString("bullet_type"));
                            if (bulletType.getActions() != null) {
                                bulletType.getActions().destroyAny(displayEntity);
                            }
                            itemDisplay.remove();
                        }
                        displayEntity.getTags().setInteger("lifetime", lifetime);
                        double speed = displayEntity.getTags().getDouble("speed", 1);
                        Collection<Entity> nearbyEntities = itemDisplay.getLocation().getNearbyEntities(3, 3, 3);
                        int splits = 16;
                        for (int i = 0; i < splits; i++) {
                            itemDisplay.teleport(itemDisplay.getLocation().clone().add(itemDisplay.getLocation().getDirection().clone().multiply(speed / splits)));
                            if (itemDisplay.getLocation().getBlock().isSolid()) {
                                Block block = itemDisplay.getLocation().getBlock();
                                if (block.getBoundingBox().contains(itemDisplay.getLocation().toVector())) {
                                    SiBulletType bulletType = SiBulletTypes.all.get(displayEntity.getTags().getString("bullet_type"));
                                    if (bulletType.getActions() != null) {
                                        bulletType.getActions().destroyByBlock(displayEntity, block);
                                        bulletType.getActions().destroyAny(displayEntity);
                                    }
                                    itemDisplay.remove();
                                }
                                return;
                            }
                            for (Entity entity : nearbyEntities) {
                                if (!entity.getBoundingBox().contains(itemDisplay.getLocation().toVector())) continue;
                                if (entity.getUniqueId().toString().equals(displayEntity.getTags().getString("owner")))
                                    continue;
                                SiBulletType bulletType = SiBulletTypes.all.get(displayEntity.getTags().getString("bullet_type"));
                                SiBulletTypeActions bulletTypeActions = bulletType.getActions();
                                if (bulletTypeActions != null) {
                                    bulletTypeActions.destroyByEntity(displayEntity, entity);
                                    if (entity instanceof LivingEntity livingEntity)
                                        bulletTypeActions.destroyByLivingEntity(displayEntity, livingEntity);
                                    if (entity instanceof Player player)
                                        bulletTypeActions.destroyByPlayer(displayEntity, player);
                                    bulletTypeActions.destroyAny(displayEntity);
                                }
                                itemDisplay.remove();
                                return;
                            }
                        }
                    }
                };
            }};
        });
    }
}
