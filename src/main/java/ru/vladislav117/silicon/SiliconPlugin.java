package ru.vladislav117.silicon;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.world.SiWorld;
import ru.vladislav117.silicon.world.SiWorlds;
import ru.vladislav117.tellurium.TeSilicon;

public final class SiliconPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Silicon.init(this);
        if (true) TeSilicon.init();
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        SiWorld world = SiWorlds.all.get(worldName);
        if (world == null) return null;
        if (world.getWorldGenerator() == null) return null;
        return world.getWorldGenerator().getChunkGenerator();
    }
}
