package ru.vladislav117.silicon.region.item;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.cooldown.SiTickCooldown;
import ru.vladislav117.silicon.item.SiItemInteractions;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.region.SiRegion;
import ru.vladislav117.silicon.region.SiRegions;
import ru.vladislav117.silicon.text.SiText;

import java.util.Locale;

/**
 * Взаимодействия предмета с регионом.
 */
public class SiRegionItemInteractions extends SiItemInteractions.StaticInteractions {
    @Override
    public void rightClickBlock(Player player, boolean shifted, SiItemStack itemStack, Block block, PlayerInteractEvent event) {
        if (SiTickCooldown.hasCooldown(player.getName() + "_create_region")) return;
        SiTickCooldown.setCooldown(player.getName() + "_create_region", 10);
        int radius = itemStack.getTagsManager().getInteger("region_size", 1);
        BoundingBox boundingBox = new BoundingBox(block.getX() - radius, block.getWorld().getMinHeight(), block.getZ() - radius, block.getX() + radius, block.getWorld().getMaxHeight(), block.getZ() + radius);
        for (SiRegion region : SiRegions.getRegionsAt(block.getWorld()).getRegions()) {
            if (region.getBoundingBox().overlaps(boundingBox) && !region.isOwner(player)) {
                SiText.string("Регион соприкасается с чужим регионом!", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return;
            }
        }
        Location location = block.getLocation().clone();
        location.add(-radius, 0, -radius);
        location.setY(block.getWorld().getMinHeight());
        SiRegion region = new SiRegion(SiRegions.giveName(player.getName().toLowerCase(Locale.ROOT) + "_"), location, new Vector(radius * 2, Math.abs(block.getWorld().getMinHeight()) + block.getWorld().getMaxHeight(), radius * 2));
        region.getOwners().add(player.getName());
        region.getMembers().add(player.getName());
        region.setItemTypeName(itemStack.getItemType().getName());
        region.save();
        SiText.string("Создан новый регион " + region.getName() + ". Вы можете изменить его через команду /rg", SiPalette.Interface.green).toMessageTask().addPlayer(player).send();
        player.getInventory().getItem(event.getHand()).add(-1);
    }
}
