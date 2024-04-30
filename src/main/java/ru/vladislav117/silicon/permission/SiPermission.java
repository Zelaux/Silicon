package ru.vladislav117.silicon.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.external.vault.SiVaultAPI;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

public class SiPermission {
    protected String permission;
    protected boolean enabledByDefault = false;
    protected SiTextLike doesNotHavePermissionForCommand = SiText.string("У вас недостаточно прав на использование этой команды.", SiPalette.Interface.red);

    public SiPermission(String permission, boolean enabledByDefault) {
        this.permission = permission;
        this.enabledByDefault = enabledByDefault;
    }

    public SiPermission(String permission) {
        this.permission = permission;
    }

    public boolean has(Player player) {
        return SiVaultAPI.hasPermission(player, permission, enabledByDefault);
    }

    public boolean has(CommandSender sender) {
        return SiVaultAPI.hasPermission(sender, permission, enabledByDefault);
    }

    public boolean checkForCommand(Player player) {
        if (has(player)) return true;
        doesNotHavePermissionForCommand.toMessageTask().addPlayer(player).send();
        return false;
    }

    public boolean checkForCommand(CommandSender sender) {
        if (has(sender)) return true;
        doesNotHavePermissionForCommand.toMessageTask().addOtherReceiver(sender).send();
        return false;
    }
}
