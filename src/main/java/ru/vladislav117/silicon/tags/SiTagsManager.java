package ru.vladislav117.silicon.tags;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.namespace.SiNamespace;

import java.util.HashMap;

/**
 * Менеджер тегов. Является обёрткой для PersistentDataContainer.
 */
public class SiTagsManager {
    protected PersistentDataContainer container;

    /**
     * Создание нового менеджера тегов.
     *
     * @param container Контейнер тегов
     */
    public SiTagsManager(PersistentDataContainer container) {
        this.container = container;
    }

    /**
     * Получение контейнера тегов.
     *
     * @return Контейнер тегов.
     */
    public PersistentDataContainer getContainer() {
        return container;
    }

    /**
     * Установка другого контейнера тегов.
     *
     * @param container Контейнер тегов
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setContainer(PersistentDataContainer container) {
        this.container = container;
        return this;
    }

    /**
     * Проверка наличия тега в контейнере.
     *
     * @param tag Тег
     * @return Наличие тега в контейнере.
     */
    public boolean hasTag(String tag) {
        return container.has(SiNamespace.getKey(tag));
    }

    /**
     * Получение контейнера данных по тегу.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Контейнер данных или null, если тега не существует.
     */
    @Nullable
    public PersistentDataContainer getDataContainer(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.TAG_CONTAINER);
    }

    /**
     * Установка контейнера данных в тег.
     *
     * @param tag           Имя тега
     * @param dataContainer Контейнер данных
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setDataContainer(String tag, PersistentDataContainer dataContainer) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.TAG_CONTAINER, dataContainer);
        return this;
    }

    /**
     * Получение менеджера тегов по тегу.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Менеджер тегов или null, если тега не существует.
     */
    @Nullable
    public SiTagsManager getTagsManager(String tag) {
        PersistentDataContainer dataContainer = container.get(SiNamespace.getKey(tag), PersistentDataType.TAG_CONTAINER);
        return dataContainer == null ? null : new SiTagsManager(dataContainer);
    }

    /**
     * Установка менеджера тегов в тег.
     *
     * @param tag         Имя тега
     * @param tagsManager Менеджер тегов
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setTagsManager(String tag, SiTagsManager tagsManager) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.TAG_CONTAINER, tagsManager.getContainer());
        return this;
    }

    /**
     * Удаление тега.
     *
     * @param tag Тег, который будет удалён
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager removeTag(String tag) {
        container.remove(SiNamespace.getKey(tag));
        return this;
    }

    /**
     * Установка неизвестного значения тега.
     * <p>
     * ВАЖНО! Поддерживаются только примитивы и строки.
     *
     * @param tag   Имя тега
     * @param value Значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setTag(String tag, Object value) {
        if (value instanceof Boolean b) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.BOOLEAN, b);
        } else if (value instanceof Byte b) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.BYTE, b);
        } else if (value instanceof Short s) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.SHORT, s);
        } else if (value instanceof Integer i) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.INTEGER, i);
        } else if (value instanceof Long l) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.LONG, l);
        } else if (value instanceof Float f) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.FLOAT, f);
        } else if (value instanceof Double d) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.DOUBLE, d);
        } else if (value instanceof Character c) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.STRING, String.valueOf(c));
        } else if (value instanceof String s) {
            container.set(SiNamespace.getKey(tag), PersistentDataType.STRING, s);
        }
        return this;
    }

    /**
     * Установка неизвестных значений тегов.
     * <p>
     * ВАЖНО! Поддерживаются только примитивы и строки.
     *
     * @param tags Теги
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setTags(HashMap<String, Object> tags) {
        for (String tag : tags.keySet()) setTag(tag, tags.get(tag));
        return this;
    }
    
    /**
     * Получение Boolean значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public Boolean getBoolean(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.BOOLEAN);
    }
    
    /**
     * Получение boolean значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public boolean getBoolean(String tag, boolean defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.BOOLEAN, defaultValue);
    }
    
    /**
     * Установка boolean значения тега.
     *
     * @param tag   Имя тега
     * @param value boolean значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setBoolean(String tag, boolean value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.BOOLEAN, value);
        return this;
    }
    
    /**
     * Получение Short значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public Short getShort(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.SHORT);
    }
    
    /**
     * Получение short значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public short getShort(String tag, short defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.SHORT, defaultValue);
    }
    
    /**
     * Установка short значения тега.
     *
     * @param tag   Имя тега
     * @param value short значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setShort(String tag, short value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.SHORT, value);
        return this;
    }
    
    /**
     * Получение Integer значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public Integer getInteger(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.INTEGER);
    }
    
    /**
     * Получение int значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public int getInteger(String tag, int defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.INTEGER, defaultValue);
    }
    
    /**
     * Установка int значения тега.
     *
     * @param tag   Имя тега
     * @param value int значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setInteger(String tag, int value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.INTEGER, value);
        return this;
    }
    
    /**
     * Получение Long значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public Long getLong(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.LONG);
    }
    
    /**
     * Получение long значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public long getLong(String tag, long defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.LONG, defaultValue);
    }
    
    /**
     * Установка long значения тега.
     *
     * @param tag   Имя тега
     * @param value long значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setLong(String tag, long value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.LONG, value);
        return this;
    }
    
    /**
     * Получение Float значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public Float getFloat(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.FLOAT);
    }
    
    /**
     * Получение float значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public float getFloat(String tag, float defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.FLOAT, defaultValue);
    }
    
    /**
     * Установка float значения тега.
     *
     * @param tag   Имя тега
     * @param value float значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setFloat(String tag, float value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.FLOAT, value);
        return this;
    }
    
    /**
     * Получение Double значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public Double getDouble(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.DOUBLE);
    }
    
    /**
     * Получение double значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public double getDouble(String tag, double defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.DOUBLE, defaultValue);
    }
    
    /**
     * Установка double значения тега.
     *
     * @param tag   Имя тега
     * @param value double значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setDouble(String tag, double value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.DOUBLE, value);
        return this;
    }
    
    /**
     * Получение String значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public String getString(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.STRING);
    }
    
    /**
     * Получение String значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public String getString(String tag, String defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.STRING, defaultValue);
    }
    
    /**
     * Установка String значения тега.
     *
     * @param tag   Имя тега
     * @param value String значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setString(String tag, String value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.STRING, value);
        return this;
    }
    
    /**
     * Получение byte @Nullable [] значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public byte @Nullable [] getByteArray(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.BYTE_ARRAY);
    }
    
    /**
     * Получение byte[] значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public byte[] getByteArray(String tag, byte[] defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.BYTE_ARRAY, defaultValue);
    }
    
    /**
     * Установка byte[] значения тега.
     *
     * @param tag   Имя тега
     * @param value byte[] значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setByteArray(String tag, byte[] value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.BYTE_ARRAY, value);
        return this;
    }
    
    /**
     * Получение int @Nullable [] значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public int @Nullable [] getIntegerArray(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.INTEGER_ARRAY);
    }
    
    /**
     * Получение int[] значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public int[] getIntegerArray(String tag, int[] defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.INTEGER_ARRAY, defaultValue);
    }
    
    /**
     * Установка int[] значения тега.
     *
     * @param tag   Имя тега
     * @param value int[] значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setIntegerArray(String tag, int[] value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.INTEGER_ARRAY, value);
        return this;
    }
    
    /**
     * Получение long @Nullable [] значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag Имя тега
     * @return Значение тега или null, если тега не существует.
     */
    @Nullable
    public long @Nullable [] getLongArray(String tag) {
        return container.get(SiNamespace.getKey(tag), PersistentDataType.LONG_ARRAY);
    }
    
    /**
     * Получение long[] значения тега.
     * <p>
     * ВАЖНО! Если тег существует, но он другого типа, то будет вызвано исключение.
     *
     * @param tag          Имя тега
     * @param defaultValue Значение по умолчанию
     * @return Значение тега или значение по умолчанию, если тега не существует.
     */
    public long[] getLongArray(String tag, long[] defaultValue) {
        return container.getOrDefault(SiNamespace.getKey(tag), PersistentDataType.LONG_ARRAY, defaultValue);
    }
    
    /**
     * Установка long[] значения тега.
     *
     * @param tag   Имя тега
     * @param value long[] значение тега
     * @return Этот же менеджер тегов.
     */
    public SiTagsManager setLongArray(String tag, long[] value) {
        container.set(SiNamespace.getKey(tag), PersistentDataType.LONG_ARRAY, value);
        return this;
    }
    
}
