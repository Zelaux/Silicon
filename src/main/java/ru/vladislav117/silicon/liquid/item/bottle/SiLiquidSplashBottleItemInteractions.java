package ru.vladislav117.silicon.liquid.item.bottle;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import ru.vladislav117.silicon.item.SiItemInteractions;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.liquid.SiLiquidStack;
import ru.vladislav117.silicon.liquid.SiLiquidType;
import ru.vladislav117.silicon.liquid.SiLiquids;
import ru.vladislav117.silicon.liquid.item.SiLiquidContainer;

import java.util.Collection;

/**
 * Взаимодействия для бросаемой бутылки.
 */
public class SiLiquidSplashBottleItemInteractions extends SiItemInteractions.StaticInteractions {
    @Override
    public void potionSplash(SiItemStack itemStack, Collection<LivingEntity> entities, ThrownPotion potion, PotionSplashEvent event) {
        SiLiquidContainer liquidContainer = SiLiquidContainer.parseItemStack(itemStack);
        if (liquidContainer == null) return;
        SiLiquidStack liquidStack = liquidContainer.getLiquidStack();
        SiLiquidType liquidType = liquidStack.getLiquidType();
        if (liquidType.getInteractions() == null) return;
        boolean highTemperature = liquidStack.getTemperature() > SiLiquids.averageSafeTemperature.getRightBorder();
        boolean lowTemperature = liquidStack.getTemperature() < SiLiquids.averageSafeTemperature.getLeftBorder();
        LivingEntity initiator = null;
        if (event.getPotion().getShooter() instanceof LivingEntity livingEntity) {
            initiator = livingEntity;
        }
        liquidType.getInteractions().liquidSpilled(liquidStack, event.getPotion().getLocation(), null, itemStack, event.getPotion(), event);
        for (LivingEntity entity : entities) {
            if (highTemperature && SiLiquids.takesDamageFromHighTemperature(entity.getType())) {
                entity.damage(2, initiator);
            }
            if (lowTemperature && SiLiquids.takesDamageFromLowTemperature(entity.getType())) {
                entity.damage(2, initiator);
            }
            liquidType.getInteractions().pourLivingEntity(liquidStack, entity, initiator);
            if (entity instanceof Player player) {
                liquidType.getInteractions().pourPlayer(liquidStack, player, initiator);
            }
        }
    }
}
