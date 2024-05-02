package ru.vladislav117.silicon.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.log.SiLog;
import ru.vladislav117.silicon.materialReplacer.SiMaterialReplacerItemType;
import ru.vladislav117.silicon.materialReplacer.SiMaterialsReplacers;
import ru.vladislav117.silicon.ticker.SiServerLoadTicker;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Класс-контейнер для работы с блоками.
 */
public final class SiBlocks {
    static ChunksRecords chunksRecords = new ChunksRecords();
    static ArrayList<Location> chunksQueue = new ArrayList<>();

    /**
     * Создание ключа чанка.
     *
     * @param worldName Имя мира
     * @param x         x
     * @param z         z
     * @return Ключ чанка.
     */
    public static String buildChunkKey(String worldName, int x, int z) {
        return worldName + "_" + x + "_" + z;
    }

    /**
     * Создание ключа чанка.
     *
     * @param chunk Чанк
     * @return Ключ чанка.
     */
    public static String buildChunkKey(Chunk chunk) {
        return buildChunkKey(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    /**
     * Создание ключа чанка.
     *
     * @param location Позиция внутри чанка
     * @return Ключ чанка.
     */
    public static String buildChunkKey(Location location) {
        return buildChunkKey(location.getChunk());
    }

    /**
     * Создание ключа блока.
     *
     * @param x x
     * @param y y
     * @param z z
     * @return Ключ блока.
     */
    public static String buildBlockKey(int x, int y, int z) {
        return x + "_" + y + "_" + z;
    }

    /**
     * Создание ключа блока.
     *
     * @param block Блок
     * @return Ключ блока.
     */
    public static String buildBlockKey(Block block) {
        return buildBlockKey(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Создание ключа блока.
     *
     * @param location Позиция блока
     * @return Ключ блока.
     */
    public static String buildBlockKey(Location location) {
        return buildBlockKey(location.getBlock());
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.addHandler(SiBuiltinEvents.ServerLoadEvent.class, event -> {
            for (Location location : chunksQueue) {
                chunksRecords.loadChunk(location.getChunk());
            }
        });

        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onBlockPlaceEvent(BlockPlaceEvent event) {
                if (event.isCancelled()) return;
                SiMaterialReplacerItemType materialReplacer = SiMaterialsReplacers.get(event.getBlock().getType());
                if (materialReplacer == null) return;
                if (!(materialReplacer instanceof SiBlockItemType blockItemType)) return;
                if (blockItemType.getBlockUpdater() == null) return;
                chunksRecords.addBlock(blockItemType, event.getBlock().getLocation());
            }

            @EventHandler
            public void onBlockBreakEvent(BlockBreakEvent event) {
                if (event.isCancelled()) return;
                SiMaterialReplacerItemType materialReplacer = SiMaterialsReplacers.get(event.getBlock().getType());
                if (materialReplacer == null) return;
                if (!(materialReplacer instanceof SiBlockItemType)) return;
                chunksRecords.removeBlock(event.getBlock().getLocation());
            }

            @EventHandler
            public void onChunkLoadEvent(ChunkLoadEvent event) {
                if (!Silicon.isServerLoaded()) {
                    Location location = event.getChunk().getBlock(0, 0, 0).getLocation();
                    if (!chunksQueue.contains(location)) chunksQueue.add(location);
                    return;
                }
                chunksRecords.loadChunk(event.getChunk());
            }
        });


        new SiServerLoadTicker(ticker -> chunksRecords.update());
    }

    /**
     * Запись о блоке.
     */
    public static class BlockRecord {
        protected SiBlockItemType type;
        protected Location location;
        protected int ticks = 0;

        /**
         * Создание новой записи о блоке.
         *
         * @param type     Тип блока
         * @param location Позиция
         */
        public BlockRecord(SiBlockItemType type, Location location) {
            this.type = type;
            this.location = location;
        }

        /**
         * Обновление блока.
         */
        public void update() {
            if (ticks <= 0) {
                ticks = type.getBlockUpdater().update(type, location.getBlock());
            }
            ticks--;
        }
    }

    /**
     * Запись о чанке.
     */
    public static class ChunkRecord {
        protected Chunk chunk;
        protected HashMap<String, BlockRecord> blocks = new HashMap<>();
        protected ArrayList<String> blockKeys = new ArrayList<>();

        /**
         * Создание новой записи о чанке.
         *
         * @param chunk Чанк
         */
        public ChunkRecord(Chunk chunk) {
            this.chunk = chunk;
            for (int y = chunk.getWorld().getMinHeight(); y < chunk.getWorld().getMaxHeight(); y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        Block block = chunk.getBlock(x, y, z);
                        SiMaterialReplacerItemType materialReplacer = SiMaterialsReplacers.get(block.getType());
                        if (materialReplacer == null) continue;
                        if (!(materialReplacer instanceof SiBlockItemType type)) return;
                        if (type.getBlockUpdater() == null) return;
                        addBlock(type, block.getLocation());
                    }
                }
            }
        }

        /**
         * Добавление блока в чанк.
         *
         * @param type     Тип блока
         * @param location Позиция блока
         */
        public void addBlock(SiBlockItemType type, Location location) {
            String blockKey = buildBlockKey(location);
            blocks.put(blockKey, new BlockRecord(type, location));
            blockKeys.add(blockKey);
        }

        /**
         * Удаление блока из чанка
         *
         * @param location Позиция блока
         */
        public void removeBlock(Location location) {
            String blockKey = buildBlockKey(location);
            blocks.remove(blockKey);
            blockKeys.remove(blockKey);
        }

        /**
         * Обновление чанка.
         */
        public void update() {
            if (!chunk.isLoaded()) return;
            ArrayList<Location> blockLocationsToRemove = new ArrayList<>();
            for (String blockKey : blockKeys) {
                BlockRecord blockRecord = blocks.get(blockKey);
                if (!blockRecord.location.getBlock().getType().equals(blockRecord.type.getMaterial())) {
                    blockLocationsToRemove.add(blockRecord.location);
                    continue;
                }
                blockRecord.update();
            }
            for (Location blockLocationToRemove : blockLocationsToRemove) removeBlock(blockLocationToRemove);
        }
    }

    /**
     * Записи о чанках.
     */
    public static class ChunksRecords {
        protected HashMap<String, ChunkRecord> chunks = new HashMap<>();
        protected ArrayList<String> chunkKeys = new ArrayList<>();

        /**
         * Создание новых записей о чанках.
         */
        public ChunksRecords() {
        }

        /**
         * Загрузить чанк.
         *
         * @param chunk Чанк
         */
        public void loadChunk(Chunk chunk) {
            String chunkKey = buildChunkKey(chunk);
            if (chunks.containsKey(chunkKey)) return;
            chunks.put(chunkKey, new ChunkRecord(chunk));
            chunkKeys.add(chunkKey);
        }

        /**
         * Добавить блок в чанк
         *
         * @param type     Тип блока
         * @param location Позиция блока
         */
        public void addBlock(SiBlockItemType type, Location location) {
            String chunkKey = buildChunkKey(location);
            // if (!chunks.containsKey(chunkKey)) loadChunk(location.getChunk());
            chunks.get(chunkKey).addBlock(type, location);
        }

        /**
         * Удалить блок из чанка.
         *
         * @param location Позиция блока
         */
        public void removeBlock(Location location) {
            String chunkKey = buildChunkKey(location);
            // if (!chunks.containsKey(chunkKey)) loadChunk(location.getChunk());
            chunks.get(chunkKey).removeBlock(location);
        }

        /**
         * Обновить чанки.
         */
        public void update() {
            for (String chunkKey : chunkKeys) chunks.get(chunkKey).update();
        }
    }
}
