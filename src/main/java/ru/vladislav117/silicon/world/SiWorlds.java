package ru.vladislav117.silicon.world;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import ru.vladislav117.silicon.content.SiContentList;
import ru.vladislav117.silicon.content.SiContentLoaders;
import ru.vladislav117.silicon.liquid.*;
import ru.vladislav117.silicon.ticker.SiServerLoadTicker;

/**
 * Класс-контейнер для миров.
 */
public class SiWorlds {
    public static SiContentList<SiWorld> all = new SiContentList<>();

    public static SiWorld world;

    public static SiContentLoaders loaders = new SiContentLoaders();

    /**
     * Инициализация.
     */
    public static void init() {
        loaders.addPrimaryLoader(() -> {
            world = new SiWorld("world");
        });

        loaders.addSecondaryLoader(() -> {
            for (SiWorld world1 : all.getAll()) world1.load();
        });

        new SiServerLoadTicker(ticker -> {
            for (SiWorld w : all.getAll()) {
                World world = w.getWorld();
                if (world == null) continue;
                SiLiquidStack atmosphere = w.getAtmosphere();
                if (atmosphere == null) continue;
                boolean damageByHighTemperature = atmosphere.getTemperature() > SiLiquids.averageSafeTemperature.getRightBorder();
                boolean damageByLowTemperature = atmosphere.getTemperature() < SiLiquids.averageSafeTemperature.getLeftBorder();
                for (Player player : world.getPlayers()) {
                    if (!atmosphere.getLiquidType().equals(SiLiquidTypes.air)) {
                        player.setRemainingAir(Math.max(0, player.getRemainingAir() - 6));
                        if (player.getRemainingAir() <= 0) player.damage(1);
                    }
                    if (damageByHighTemperature) {
                        if (!player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) player.damage(2);
                    }
                    if (damageByLowTemperature) {
                        player.setFreezeTicks(player.getFreezeTicks() + 4);
                        player.damage(2);
                    }
                    SiLiquidInteractions interactions = atmosphere.getLiquidType().getInteractions();
                    if (interactions == null) continue;
                    interactions.pourLivingEntity(atmosphere, player, null);
                    interactions.pourPlayer(atmosphere, player, null);
                    if (!atmosphere.getLiquidType().equals(SiLiquidTypes.air))
                        interactions.drink(atmosphere, player, null);
                }
            }
        });
    }
}
