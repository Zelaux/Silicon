package ru.vladislav117.silicon.liquid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.color.SiColor;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.random.SiRandom;

/**
 * Класс-контейнер для жидкостей.
 */
public final class SiLiquidTypes {
    public static final SiContentList<SiLiquidType> all = new SiContentList<>();

    public static SiLiquidType vacuum, air, water, steam, lava, milk, experience, honey;

    public static SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация жидкостей.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            vacuum = new SiLiquidType("vacuum", SiLiquids.absoluteZeroTemperature) {{
                vacuum = true;
                display = new SiLiquidDisplay("Вакуум", new SiColor(0.4, 0.4, 0.6), new SiColor(0.1, 0.1, 0.2));
            }};

            air = new SiLiquidType("air", SiLiquids.averageTemperature) {{
                display = new SiLiquidDisplay("Воздух", new SiColor(0.88, 0.99, 0.99));
                interactions = new SiLiquidInteractions.StaticInteractions() {
                    @Override
                    public void drink(SiLiquidStack liquidStack, Player player, PlayerItemConsumeEvent event) {
                        player.setRemainingAir(Math.min(player.getRemainingAir() + player.getMaximumAir() * liquidStack.getVolume() / SiLiquids.bucketVolume, player.getMaximumAir()));
                    }
                };
            }};

            water = new SiLiquidType("water", 20) {{
                display = new SiLiquidDisplay("Вода", new SiColor(0.22, 0.22, 0.99));
                interactions = new SiLiquidInteractions.StaticInteractions() {
                    @Override
                    public void pourLivingEntity(SiLiquidStack liquidStack, LivingEntity entity, @Nullable Entity initiator) {
                        entity.setFireTicks((int) (entity.getFireTicks() - entity.getFireTicks() * (liquidStack.getVolume() / (double) SiLiquids.bottleVolume)));
                    }
                };
            }};

            steam = new SiLiquidType("steam", 100) {{
                display = new SiLiquidDisplay("Пар", new SiColor(0.75, 0.75, 0.75));
            }};

            milk = new SiLiquidType("milk", 20) {{
                display = new SiLiquidDisplay("Молоко", new SiColor(0.9, 0.9, 0.9));
                interactions = new SiLiquidInteractions.StaticInteractions() {
                    @Override
                    public void pourLivingEntity(SiLiquidStack liquidStack, LivingEntity entity, @Nullable Entity initiator) {
                        if (SiRandom.chance(liquidStack.getVolume() / (double) SiLiquids.bucketVolume)) {
                            entity.clearActivePotionEffects();
                        }
                    }
                };
            }};

            lava = new SiLiquidType("lava", 1500) {{
                display = new SiLiquidDisplay("Лава", new SiColor(0.99, 0.5, 0.25));
                interactions = new SiLiquidInteractions.StaticInteractions() {
                    @Override
                    public void pourLivingEntity(SiLiquidStack liquidStack, LivingEntity entity, @Nullable Entity initiator) {
                        entity.setFireTicks(entity.getFireTicks() + 6 * 20 * liquidStack.getVolume() / SiLiquids.bucketVolume);
                    }
                };
            }};

            experience = new SiLiquidType("experience", SiLiquids.averageTemperature) {{
                display = new SiLiquidDisplay("Опыт", new SiColor(0.65, 1, 0.25));
                interactions = new SiLiquidInteractions.StaticInteractions() {
                    @Override
                    public void drink(SiLiquidStack liquidStack, Player player, PlayerItemConsumeEvent event) {
                        player.giveExp((int) (SiRandom.getInteger(2, 11) * (double) liquidStack.getVolume() / SiLiquids.bottleVolume));
                    }

                    @Override
                    public void liquidSpilled(SiLiquidStack liquidStack, Location location, @Nullable Entity initiator, @Nullable SiItemStack itemStack, @Nullable ThrownPotion potion, @Nullable PotionSplashEvent event) {
                        ExperienceOrb experienceOrb = (ExperienceOrb) location.getWorld().spawnEntity(location, EntityType.EXPERIENCE_ORB);
                        experienceOrb.setExperience(SiRandom.getInteger(2, 11) * liquidStack.getVolume() / SiLiquids.bottleVolume);
                    }
                };
            }};

            honey = new SiLiquidType("honey", SiLiquids.averageTemperature) {{
                display = new SiLiquidDisplay("Мёд", new SiColor(0.99, 0.8, 0.33));
                interactions = new SiLiquidInteractions.StaticInteractions() {
                    @Override
                    public void drink(SiLiquidStack liquidStack, Player player, PlayerItemConsumeEvent event) {
                        player.setFoodLevel(player.getFoodLevel() + 6 * liquidStack.getVolume() / SiLiquids.bottleVolume);
                    }
                };
            }};
        });

        loaders.addSecondaryLoader(() -> {
            water.setLowTemperatureTransformation(new SiLiquidTransformation(0, null, new ItemStack(Material.ICE)));
            water.setHighTemperatureTransformation(new SiLiquidTransformation(100, steam));

            steam.setLowTemperatureTransformation(new SiLiquidTransformation(100, water));

            milk.setLowTemperatureTransformation(new SiLiquidTransformation(0, null, new ItemStack(Material.ICE)));
            milk.setHighTemperatureTransformation(new SiLiquidTransformation(100, steam));

            lava.setLowTemperatureTransformation(new SiLiquidTransformation(1100, null, new ItemStack(Material.OBSIDIAN)));

            honey.setLowTemperatureTransformation(new SiLiquidTransformation(0, null).addItem(new ItemStack(Material.ICE)).addItem(new ItemStack(Material.SUGAR)));
            honey.setHighTemperatureTransformation(new SiLiquidTransformation(100, steam, new ItemStack(Material.SUGAR)));
        });
    }
}
