package ru.vladislav117.silicon.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.liquid.SiLiquidStack;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;

/**
 * Мир.
 */
public class SiWorld extends SiContent {
    protected SiTextLike displayName;
    protected World world = null;
    protected SiWorldGenerator worldGenerator = null;
    protected SiLiquidStack atmosphere = null;

    /**
     * Создание нового мира.
     *
     * @param name        Имя мира
     * @param displayName Отображаемое имя
     */
    public SiWorld(String name, SiTextLike displayName) {
        super(name);
        this.displayName = displayName;
        SiWorlds.all.add(this);
    }

    /**
     * Создание нового мира.
     *
     * @param name Имя мира
     */
    public SiWorld(String name) {
        super(name);
        this.displayName = SiText.string(name);
        SiWorlds.all.add(this);
    }

    /**
     * Получение отображаемого имени мира.
     *
     * @return Отображаемое имя мира.
     */
    public SiTextLike getDisplayName() {
        return displayName;
    }

    /**
     * Установка отображаемого имени мира.
     *
     * @param displayName Отображаемое имя мира
     * @return Этот же мир.
     */
    public SiWorld setDisplayName(SiTextLike displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Получение мира.
     *
     * @return Мир или null, если мир не инициализирован.
     */
    @Nullable
    public World getWorld() {
        return world;
    }

    /**
     * Получение генератора мира.
     *
     * @return Генератор мира или null, если не задан.
     */
    @Nullable
    public SiWorldGenerator getWorldGenerator() {
        return worldGenerator;
    }

    /**
     * Получение атмосферы мира.
     *
     * @return Атмосфера мира или null, если не задана.
     */
    @Nullable
    public SiLiquidStack getAtmosphere() {
        return atmosphere;
    }

    /**
     * Загрузка мира.
     *
     * @return Этот же мир.
     */
    public SiWorld load() {
        if (worldGenerator != null) {
            SiFile configurationFile = Silicon.getBukkitRoot().getChild("bukkit.yml");
            YamlConfiguration configuration = configurationFile.readYaml();
            if (!configuration.contains("worlds")) {
                configuration.createSection("worlds");
            }
            if (!configuration.getConfigurationSection("worlds").contains(name)) {
                configuration.getConfigurationSection("worlds").createSection(name);
            }
            configuration.getConfigurationSection("worlds").getConfigurationSection(name).set("plugin", Silicon.getPlugin().getName());
            configurationFile.writeYaml(configuration);
        }

        world = Bukkit.getWorld(name);
        if (world == null) {
            WorldCreator worldCreator = new WorldCreator(name);
            worldCreator.generator(worldGenerator.getChunkGenerator());
            worldCreator.environment(worldGenerator.getEnvironment());
            worldCreator.biomeProvider(worldGenerator.getChunkGenerator().getDefaultBiomeProvider(world));
            worldCreator.generateStructures(worldGenerator.getChunkGenerator().shouldGenerateStructures());
            worldCreator.createWorld();
            world = Bukkit.getWorld(name);
        }
        return this;
    }
}
