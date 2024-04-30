package ru.vladislav117.silicon.liquid.item.bottle;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import ru.vladislav117.silicon.item.SiItemInteractions;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.liquid.SiLiquidType;
import ru.vladislav117.silicon.liquid.SiLiquids;
import ru.vladislav117.silicon.liquid.item.SiLiquidContainer;

/**
 * Взаимодействия для бутылки.
 */
public class SiLiquidBottleItemInteractions extends SiItemInteractions.StaticInteractions {
    @Override
    public void consume(Player player, SiItemStack itemStack, PlayerItemConsumeEvent event) {
        SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(itemStack);
        if (liquidContainer == null) return;
        SiLiquidType liquidType = liquidContainer.getLiquidStack().getLiquidType();
        if (liquidType.isVacuum()) return;
        if (!SiLiquids.averageSafeTemperature.isInRange(liquidContainer.getLiquidStack().getTemperature())) {
            player.damage(4, player);
        }
        if (liquidType.getInteractions() == null) return;
        liquidType.getInteractions().pourLivingEntity(liquidContainer.getLiquidStack(), player, player);
        liquidType.getInteractions().pourPlayer(liquidContainer.getLiquidStack(), player, player);
    }
}
