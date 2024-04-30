package ru.vladislav117.silicon.world;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * Генератор мира.
 */
public class SiWorldGenerator {
    protected ChunkGenerator chunkGenerator;

    /**
     * Создание нового генератора мира.
     *
     * @param chunkGenerator Генератор чанков
     */
    public SiWorldGenerator(ChunkGenerator chunkGenerator) {
        this.chunkGenerator = chunkGenerator;
    }

    /**
     * Получение среды мира.
     *
     * @return Среда мира.
     */
    public World.Environment getEnvironment() {
        return World.Environment.NORMAL;
    }

    /**
     * Получение генератора чанков.
     *
     * @return Генератор чанков.
     */
    public ChunkGenerator getChunkGenerator() {
        return chunkGenerator;
    }
}
