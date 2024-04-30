package ru.vladislav117.silicon.cusomModelData;

import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.node.SiNode;
import ru.vladislav117.silicon.resourcepack.SiResourcepack;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Реестр записей кастомных моделей.
 */
public final class SiCustomModelDataRegistry {
    static HashSet<Integer> registeredIds = new HashSet<>();
    static ArrayList<SiCustomModelDataRecord> records = new ArrayList<>();

    /**
     * Подобрать незанятый id со смещением.
     *
     * @param id Id
     * @return Id или id со смещением 1.
     */
    static int findId(int id) {
        if (registeredIds.contains(id)) return findId(id + 1);
        return id;
    }

    /**
     * Зарегистрировать запись.
     *
     * @param record Запись
     * @return Id, который выдан записи.
     */
    public static int register(SiCustomModelDataRecord record) {
        int id = findId(Math.abs((record.getCategory() + "." + record.getName()).hashCode() % 10_000_000));
        registeredIds.add(id);
        records.add(record);
        return id;
    }

    /**
     * Инициализация реестра.
     */
    public static void init() {
        SiEvents.addHandler(SiBuiltinEvents.ResourcepackWriteStartEvent.class, event -> {
            SiNode registry = SiNode.emptyList();
            for (SiCustomModelDataRecord record : records) {
                registry.add(record.toNode());
            }
            SiResourcepack.addGroup("custom_model_data", registry);
        });
    }
}
